package board.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.BoardComment;

/**
 * 댓글, 대댓글 서블릿
 */
@WebServlet("/board/boardCommentInsert")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {
		// 1. 사용자 입력값 처리
		// hidden태그 
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int commentLevel = Integer.parseInt(request.getParameter("commentLevel"));
		int commentRef = Integer.parseInt(request.getParameter("commentRef"));
		String writer = request.getParameter("writer");
		// textArea태그
		String content = request.getParameter("content");
		BoardComment bc = new BoardComment(0, commentLevel, writer, content, boardNo, commentRef, null);
		System.out.println("boardComment@servlet = " + bc);
		// [no=0, commnetLevel=1, writer=admin, content=c, boardNo=113, commentRef=0, regDate=null]
		// 2. 업무로직
		int result = boardService.insertBoardComment(bc);
		// 3. 사용자 피드백 & 리다이렉트
		// cf. catch절로 떨어지지 않으면 무조건 댓글등록 성공, result가 0인지 1인지 여부 불필요
		request.getSession().setAttribute("msg", "댓글등록성공!");
		// 현재페이지로 이동, 그러려면 게시글 번호 필요
		response.sendRedirect(request.getContextPath() + "board/boardView?no=" + boardNo);
	  } catch(Exception e) {
		 e.printStackTrace();
		 throw e;
	  }
	}
}
