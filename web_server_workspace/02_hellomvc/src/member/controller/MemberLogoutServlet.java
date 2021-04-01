package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutServlet
 */
@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그아웃 -> 세션 무효화 : 세션에 저장된 속성값을 모두 폐기함
		// 세션 가져오기
		// false - 만약 세션이 존재하지 않으면, 새로 만들지 않고 null을 리턴
		// session객체가 유지되는게 30분 정도라고 가정한다면,
		// 로그인하고 1시간 지나서 로그아웃 요청을 한다면 이미 서버의 세션객체는 이미 폐기된 상태
		// 세션이 이미 유효하지 않으니, 그때는 새로 session객체를 만들지 말고 null을 리턴해라
		HttpSession session = request.getSession(false);
		
		// 세션 무효화코드
		// null일수도 있기 때문에, 세션이 null이 아닐때만 (session이 이미 폐기되지 않았을 경우) invalidate처리
		if(session != null)
			session.invalidate();
		// 사실 session객체는 폐기되지 않고, 재사용되는데
		// 그 재사용하기 위한 과정임
		// 현재 있는 session만 무효화해서 속성들만 제거하고 다음 사용자가 오면 재사용하는 것
		// (물론 session id는 새로 발급됨)ㄴ
		
		// 세션처리가 잘 되었으면, 다시 sendRedirect처리 -> 인덱스페이지로 보냄
		response.sendRedirect(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
