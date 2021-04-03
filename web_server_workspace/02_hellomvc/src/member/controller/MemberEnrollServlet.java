package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollServlet
 */
// 어차피 mvc라는 공간에 있으므로 /mvc(context path)를 쓰면 안됨
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	
	/**
	 * GET방식 - 회원가입 페이지
	 * 회원가입 페이지를 요청하는 건 DB의 상태가 변하지 않는 멱등요청
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 여기서는 forwarding처리만 해줌
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp")
				.forward(request, response);
	}

	/**
	 * POST방식 - 회원가입 처리
	 * -> DB에 저장
	 * 회원가입 처리를 요청하는 건 DB의 상태가 변함
	 * (DML요청은 다 POST로 처리하기)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. encoding
		request.setCharacterEncoding("utf-8"); //대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		// 2. 사용자 입력값 처리
		// name값으로 입력값을 가져와서 자바변수에 옮겨담기
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String _birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		//체크박스같은 경우 선택된 복수의 값이 배열로 전달된다.
		String[] hobbyArr = request.getParameterValues("hobby");

		// 파라미터로 전달한 문자열배열이 null이면, NullPointerException유발.
		String hobby = "";
		// String배열을 ,콤마를 이용해서 하나로 합치기
		if(hobbyArr != null) hobby = String.join(",", hobbyArr);
		// 아무것도 선택하지 않으면  -> "" -> db에는 빈문자열도 null로 처리됨
		
		// 날짜타입으로 변경 : 1990-09-09
		// 문자열로 값이 넘어옴 -> sql Date타입으로 변환
		Date birthday = null;
		if(_birthday != null && !"".equals(_birthday))
			birthday = Date.valueOf(_birthday);
		
		// member객체
		Member member = new Member(memberId, password, memberName, 
				MemberService.MEMBER_ROLE, gender, birthday, email, phone, address, hobby, null);
		
		System.out.println("입력한 회원정보 : " + member);
		
		// 3. 서비스로직 호출 (업무로직)
		int result = memberService.insertMember(member);

		// 4. 사용자 피드백 및 페이지 리다이렉트
		// 세션에 피드백 msg를 담음 
		String msg = "";
		if (result > 0) {
			msg = "회원가입에 성공했습니다.";
		} else {
			msg = "회원가입에 실패했습니다.";
		}
		// msg를 속성으로 저장
		request.getSession().setAttribute("msg", msg);
		
		// sendRedirect를 통해 url 변경
		// dml후에는 반드시 url 변경 !!!
		response.sendRedirect(request.getContextPath());
	}
}
