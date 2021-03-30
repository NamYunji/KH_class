package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String password = request.getParameter("password");
		System.out.println("memberId@servlet = " + memberId);
		System.out.println("password@servlet = " + password);
		
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
		if(member != null && password.equals(member.getPassword())) {
			// 로그인 성공
			request.setAttribute("msg", "로그인에 성공했습니다.");
			// session을 가져옴
			HttpSession session = request.getSession();
			// session에 담기
			session.setAttribute("loginMember", member);
			// 로그인한 사용자 정보 - 조회해온 member객체를 담아둠 - jsp에서도 사용가능
			// request.setAttribute("loginMember", member);
			
			// 리다이렉트 : url변경 목적
			// response.sendRedirect("이동주소")
			response.sendRedirect(request.getContextPath());
		}
		else {
			// 로그인 실패
			request.setAttribute("msg", "로그인에 실패했습니다.");
			// 로그인 실패시 페이지 이동을 위함
			// request.getContextPath() -> /mvc를 문자열로 리턴 
			request.setAttribute("loc", request.getContextPath());
			
			// 4. 응답메시지 처리 - html
			RequestDispatcher reqDispatcher =
					request.getRequestDispatcher("/index.jsp");
			reqDispatcher.forward(request, response);
		}
	}
}