package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;

// 여러개의 url이 문자열 배열로 전달됨
// urlPatterns = { "/member/memberView", "/member/memberUpdate", "/member/memberDelete" }
// 원래는 urlPatterns라는 속성이지만, 기본속성이라 생략가능
@WebFilter({ 
		"/member/memberView",
		"/member/memberUpdate", 
		"/member/memberDelete",
		"/member/updatePassword",
		"/board/boardForm"
		})
public class LoginFilter implements Filter {

	/**
	 * 로그인 여부 확인하기
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 형변환
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse)response;
		HttpSession session = httpReq.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		// cf. session이 유효하지 않으면 만들어서라도 가져오기 때문에 null일 수 없음 -> session이 null인지 여부 검사 불필요
		if(loginMember == null) {
			session.setAttribute("msg", "로그인 후 사용할 수 있습니다.");
			httpRes.sendRedirect(httpReq.getContextPath());
			// null이면 조기리턴 -> doFilter 실행x -> servlet가지도 못하고 빠꾸당함
			return;
		}
		chain.doFilter(request, response);
	}
}
