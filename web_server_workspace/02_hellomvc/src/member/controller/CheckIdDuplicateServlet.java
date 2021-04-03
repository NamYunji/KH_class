package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckIdDuplicateServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// cf. 영문자, 숫자로만 쓸 경우에는 인코딩 필요없음
		// 1. 사용자입력값 처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@servlet = " + memberId);
		// 2. 업무로직 : 해당 id를 db에서 조회
		// member객체를 가져옴
		Member member = new MemberService().selectOne(memberId);
		// 조회시 없는 아이디면 member에 null이 담김, null이 넘어왔을 때 해당 아이디를 사용할 수 있음
		boolean available = member == null;
		// jsp에서 사용할 수 있도록 속성으로 담아둠
		request.setAttribute("available", available);
		// 3. 응답처리 : 사용가능여부를 jsp에서 html로 작성
		RequestDispatcher reqDispatcher = 
				request.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp");
		reqDispatcher.forward(request, response);
	}
}
