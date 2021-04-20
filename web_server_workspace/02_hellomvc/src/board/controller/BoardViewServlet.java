package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.exception.BoardException;
import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.BoardComment;
import common.MvcUtils;

@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();		
    
	/**
	 * 게시글 상세보기
	 * - 게시글 하나 + 첨부파일 까지 조회 (board + attachment 조회)
	 * - 조인없이 두번 쿼리요청할 것
	 * 
	 * 게시글 등록 성공시 바로 상세보기 페이지로 이동할 것
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자 입력값 : no
		try {
		int no  = 0; 
		try {
			no = Integer.parseInt(request.getParameter("no"));
		} catch(NumberFormatException e) {
			throw new BoardException("유효한 게시글 번호가 아닙니다.", e);
		}
		
		//2. 업무로직 : board객체 조회 (+첨부파일 조회)
		// 1. 게시글 자체 가져오기
		Board board = boardService.selectOne(no);
		if(board == null) {
			throw new BoardException("해당 게시글이 존재하지 않습니다.");
		}
		// xss공격 방지
		// 사용자가 입력할 수 있는 부분에는 다 처리해줘야 함
		board.setTitle(MvcUtils.escapeHtml(board.getTitle()));
		board.setContent(MvcUtils.escapeHtml(board.getContent()));
		
		// \n개행문자를 <br/>태그로 변경
		// 여러 곳에서 쓰일 예정이므로 MvcUtils에 메소드 만듦
		// board의 content를 가져와서 바꾸고 리턴된 값을 다시 content로 세팅
		board.setContent(MvcUtils.convertLineFeedToBr(board.getContent()));
		
		// 2. 해당 게시글의 댓글 가져오기
		// 여러개의 댓글이 달릴 수 있어서 list로 가져오기
		// no를 parameter로 전달해서 해당 게시글을 가져오는 것
		List<BoardComment> commentList = BoardService.selectBoardCommentList(no);
		System.out.println("commentList@servlet = " + commentList);
		//3. jsp forwarding
		request.setAttribute("board", board);
		request.setAttribute("commentList", commentList);
		request.getRequestDispatcher("/WEB-INF/views/board/boardView.jsp")
			   .forward(request, response);
		} catch(Exception e) {
			// 로깅
			e.printStackTrace();
			// 관리자 이메일 알림
			// 다시 예외를 던져서 WAS가 정한 에러페이지에서 응답메시지 작성
			throw e;
		}
	}
}
