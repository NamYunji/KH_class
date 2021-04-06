package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Paging Recipe
 * A. Contents Section : 쿼리
 * 		1. start rownum ~ end rownum 구하기
 * 		2. cPage (현재페이지), numPerPage (페이지당 표시할 컨텐츠 수)
 * B. Pagebar Section : 숫자에 맞게 html 작성
 * 		1. totalContents 총 컨텐츠
 * 		2. totalPage (전체 페이지 수)
 * 		-> totalContents와 numPerPage를 알면, tatalPage를 구할 수 있음
 * 		3. pageBarSize (페이지바에 표시할 페이지 개수) ex. < 1 2 3 4 5 >
 * 		4. pageNo (페이지 넘버를 출력할 증감변수)
 * 		5. pageStart ~ pageEnd (pageNo의 범위)
 */
@WebServlet("/admin/memberList")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩 처리 - filter에서 처리
		// 1. 사용자 입력값 처리 : 현재 페이지 cPage 변수 넘기기
		// numPerPage 고정시킴
		final int numPerPage = 10;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
			// ? cPage = 1, ? cPage = 2 이런식으로 넘어올 것			
		} catch (NumberFormatException e) {
			// request.getParameter("cPage")가 null인 경우, Number Format Exception 발생
			// Integer.parseInt(request.getParameter("cPage"))가 완료돼야 cPage에 담기는데 그렇지 않고, catch절로 떨어짐
			// -> cPage에는 1의 값이 그대로 유지 -> 처리코드 없음
		}
		// 105건이 있을 때, 1~10 11~20, 21~30 이렇게 될 것
		
		// 2. 업무로직 : DB에서 전체회원 조회
		// 회원가입일 내림차순으로 조회
		// cPageN : n1 ~ n2
		// cPage1 : 1 ~ 10
		// cPage2 : 11 ~ 20
		// cPage3 : 21 ~ 30
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
		// selectList에 start와 end를 함께 넘기기
		List<Member> list = memberService.selectList(start, end);
		System.out.println("list@servlet = " + list);
		// 3. jsp에 html응답메세지 작성 위임
		// 리다이렉트 안하고, 한 요청 안에서 일어나니까 session이 아닌 request에 담기
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp")
				.forward(request, response);
	}
}
