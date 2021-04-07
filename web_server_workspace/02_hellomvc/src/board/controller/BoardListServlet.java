package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.MvcUtils;

/**
 * 페이징
 * - 한 페이지당 게시글 수 5개 - numPerPage 5개
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 0. 인코딩처리는 EncodingFilter가 선처리
		
		// 1. 사용자 입력값
		final int numPerPage = 5;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			// 좌항에 대입하지 못했다면 cPage에 1이 그대로 담기니까 예외처리코드 불필요
		}
		// 2. 업무로직
		// A. Contents영역 : start컨텐츠 ~ end컨텐츠
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
		List<Board> list = boardService.selectList(start, end);
		System.out.println("list@servlet = " + list);
		int totalContents = boardService.selectBoardCount();		
		// B. pageBar영역
		String url = request.getRequestURI();
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);
		
		// 3. 응답 html처리 - jsp에 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp")
				.forward(request, response);
	}
}
