package member.controlloer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet("/member/memberView")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원정보를 가져와서 jsp를 통해 뿌리기
		// 회원정보를 가진 객체는 session의 loginMember에 있음
		// 1. 업무로직 : 회원객체 가져오기
		HttpSession session = request.getSession();
		Member member = null;
		if(session != null)
			member = (Member)session.getAttribute("loginMember");
		if(member == null) {
			session.setAttribute("msg", "로그인 후 사용할 수 있습니다.");
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		// 2. jsp 위임처리
		// jsp에서도 session에 접근할 수 있기 때문에 member객체를 따로 담지는 않음
		request.getRequestDispatcher("/WEB-INF/views/member/memberView.jsp")
				.forward(request, response);
		// cf. 변수에 담지 않고 바로 처리할 수도 있음
	}

}
