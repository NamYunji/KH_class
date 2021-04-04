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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. encoding
		request.setCharacterEncoding("UTF-8");
		// 2. 사용자 입력값 처리
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
		// update구문에서 사용하지 않는 것들은 자리만 null로 채워둠 (memberRole, enrollDate)
		Member member = new Member(memberId, password, memberName, 
				null, gender, birthday, email, phone, address, hobby, null);
		System.out.println("입력한 회원정보 : " + member);
		// 3. 업무로직
		int result = memberService.updateMember(member);
		
		// 4. 사용자 피드백 및 리다이렉트 처리
		// DML의 성격을 가진 서비스 뒤에는 반드시 리다이렉트 처리 !!
		HttpSession session = request.getSession();
		String msg = "";
		if(result > 0) {
			msg = "성공적으로 회원정보를 수정했습니다.";
			// 회원정보 수정 -> session의 정보도 갱신
			session.setAttribute("loginMember", memberService.selectOne(memberId));
		}
		else
			msg = "회원정보수정에 실패했습니다.";
		session.setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/member/memberView");
	}
}
