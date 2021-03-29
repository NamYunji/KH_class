package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// MemberService의 selectOne메소드 호출
		Member member = new memberService.selectOne(memberId);
		System.out.println("member@servlet = " + member);
		
		// 4. 응답메시지 처리 - jsp위임 또는 redirect처리
	}
}
