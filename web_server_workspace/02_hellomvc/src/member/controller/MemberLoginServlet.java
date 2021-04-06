package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberLoginServlet
 */
/**
 * @WebServlet
 * 자바 클래스를 효율적으로 이용할 수 있도록 기능 추가
 * -> servlet 등록을 자동으로 처리함
 * 
 * 가질 수 있는 속성
 * - urlPatterns : String[] -> 한 서블릿이 여러 url을 처리할 수 있도록 배열임
 * 	 @WebServlet("/member/login") = @WebServlet(urlPatterns = {"/member/login"})
 * - name
 * 
 * cf. 이제까지는 servlet클래스를 만들고 web.xml에 서블릿을 등록해줘야 했었음
 * 
 */
@WebServlet("/member/login")
public class MemberLoginServlet extends HttpServlet {
	// 직렬화 과정에서 클래스를 구분하기 위한 고유값
	private static final long serialVersionUID = 1L;
	
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// cf. 예외를 던지는 것 = 톰캣에서 예외처리를 하고 있다는 것
		 
		// 서블릿은 늘 사용자 요청정보를 잘 처리해줘야 함
		// 서블릿의 고정 순서
		// 1. encoding
		request.setCharacterEncoding("utf-8");
		// 2. 사용자 입력값 처리
		// name값으로 입력값을 가져와서 자바변수에 옮겨담기
		String memberId = request.getParameter("memberId");
		String password = MvcUtils.getSha512(request.getParameter("password"));
		String saveId = request.getParameter("saveId");
		System.out.println("memberId@servlet = " + memberId);
		System.out.println("password@servlet = " + password);
		// 체크하면 on, 아니면 null이 대입
		System.out.println("saveId@servlet = " + saveId); 
		
		// 3. 업무로직 : memberId로 회원객체를 조회
		// 사용자가 honggd, 1234라고 보냈을 때 로그인 성공, 실패 여부를 확인
		// db에서 사용자 정보를 가져와야 함 -> service에 위임
		Member member = memberService.selectOne(memberId);
		System.out.println("member@servlet = " + member);
		
		// memberId로 조회했을 때 아이디가 없다면 null
		// 로그인 성공/실패여부 판단
		// 1. 로그인 성공 : member != null && password.equals(member.getPassword())
		// 2. 로그인 실패 :
		// 		아이디 존재하지 않음 member == null
		// 		아이디가 존재하나 비번이 틀릴 경우 member != null && !password.equals(member.getPassword())
		HttpSession session = request.getSession();
		
		if(member != null && password.equals(member.getPassword())) {
			// 로그인 성공
			request.setAttribute("msg", "로그인에 성공했습니다.");
			// request.getSession() - session을 가져옴
			// request.getSession(create true(생략)/false) - create여부를 Boolean형으로 전달할 수 있음 : 새로 생성여부
			// -> 만약 존재하지 않는다면 새로 생성해서 가져와라 (기본값 : true)
			// true - 처음 접속 - session객체가 없음 -> 새로 만들어서라도 가져와라
			// HttpSession session = request.getSession(); (if절 위로 끌어올림)
			// session에 담기
			session.setAttribute("loginMember", member);
			// 필요한 정보를 가져올 수도 있음
			// session.getId() - JSESSIONID
			System.out.println("JSESSIONID = " + session.getId());
			
			// session timeout : web.xml보다 우선순위가 높음
			// session.setMaxInactiveInterval(sec); - 초단위
			// login성공후부터 30초 이후에 세션객체는 유효하지 않아짐 -> 로그인이 풀림
			// session.setMaxInactiveInterval(30);
			
			// saveId : cookie처리
			// 동일한 부분 refactoring
			// 1. Cookie객체 만들기 ("key", "value")
			Cookie c = new Cookie("saveId", memberId);
			// 2. 이 쿠키를 어디에서 사용할 지 경로 설정  - path : 쿠키를 전송할 url
			c.setPath(request.getContextPath());

			// saveId != null -> 체크한 경우
			if(saveId != null) {
				// 3. 기간을 정하지 않으면, Session Cookie (세션이 유지되는 기간만 보관)
				// Persistent Cookie : setMaxAge(sec)해주면 원하는만큼 보관 가능
				c.setMaxAge(60 * 60 * 24 * 7); // 7일짜리 영속쿠키
			}
			// 체크해제시
			else {
				// 브라우져의 쿠키를 제거
				// 0으로 지정해서 즉시 삭제, 음수로 지정하면 session종료시 제거됨
				c.setMaxAge(0); 
			}
			// 4. 쿠키를 응답에 같이 보내야 클라이언트에 저장
			response.addCookie(c);
			
			
			// 로그인한 사용자 정보 - 조회해온 member객체를 담아둠 - jsp에서도 사용가능
			// request.setAttribute("loginMember", member);
			
			// 리다이렉트 : url변경 목적
			// response.sendRedirect("이동주소")
			// response.sendRedirect(request.getContextPath());
		}
		else {
			// 로그인 실패
			// request.setAttribute("msg", "로그인에 실패했습니다.");
			// 다음요청에서도 읽어낼 수 있도록 session에 저장해줌
			session.setAttribute("msg", "로그인에 실패했습니다.");
			// 로그인 실패시 페이지 이동을 위함
			// request.getContextPath() -> /mvc를 문자열로 리턴 
			// request.setAttribute("loc", request.getContextPath());
			
			// 4. 응답메시지 처리 - html
//			RequestDispatcher reqDispatcher =
//					request.getRequestDispatcher("/index.jsp");
//			reqDispatcher.forward(request, response);
		} 
		response.sendRedirect(request.getContextPath());
	}
}