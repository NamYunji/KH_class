package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kh.spring.member.model.vo.Member;

/** 
 * 모든 Adapter의 목적 
 * : 중간 bridge 역할
 * interface가 있고 추상메소드가 있다면
 * 구현클래스가 그 메소드를 모두 구현해야 함
 * -> 직접 구현하지 말고, adapter class로 중간 단계를 놓음
 * 	  adaptor class는 추상 메소드를 모두 구현하되, 몸통은 비워둠
 * 	  이제는 추상메소드가 아닌 몸통이 빈 메소드
 * -> custom class에서는 이 adaptor class를 상속
 * 	  선택적으로 override 가능
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 로그인 여부를 체크해서 로그인하지 않은 경우, return false
		
		// session에 저장된 loginMember가져오기
		// 핸들러매핑 이전이기 때문에, 원래의 방식대로 사용해야함
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			// FlashMap을 통해서 redirect후 사용자피드백 전달하기
			// redirectAttribute의 addFlashAttribute는 handler에서만 사용가능함
			// addFlashAttribute는 내부적으로 flashMap 이용
			// flashMap을 이용하면 그와 같은 효과를 낼 수 있음
			FlashMap flashMap = new FlashMap();
			flashMap.put("msg", "로그인 후 사용하실 수 있습니다.");
			// flashMapManager를 통해 flashMap 등록
			FlashMapManager manager 
				= RequestContextUtils.getFlashMapManager(request);
			manager.saveOutputFlashMap(flashMap, request, response);
			
			// 로그인 후 최종이동할 url을 session속성 next로 저장
			String url = request.getRequestURL().toString();
			// getRequestURL() - http로 시작하는 값이 들어옴
			// //http://localhost:9090/spring/member/memberDetail.do
			session.setAttribute("next", url);
			
			// 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/member/memberLogin.do");
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}
