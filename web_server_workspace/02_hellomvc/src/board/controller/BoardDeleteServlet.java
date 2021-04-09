package board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;

/**
 * 게시글 삭제 서블릿
 */
@WebServlet("/board/boardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. 사용자 입력값
			int no = Integer.parseInt(request.getParameter("no"));
			
			// 2. 업무로직
			int result = boardService.deleteBoard(no);
			String msg = result > 0 ? "게시글 삭제 성공!" : "게시글 삭제 실패!";
			
			// 3. 리다이렉트 & 사용자 피드백 (dml - 리다이렉트 필수)
			HttpSession session = request.getSession();
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/board/boardList");		
		} catch(Exception e) {
			// 예외로깅 작업 - controller에서 한번만 하면 됨
			e.printStackTrace();
			// 예외페이지로 넘어가기 위해 was에 예외 다시 던지기
			throw e;
			// 예외처리를 안해주면 tomcat한테 예외가 던져져서 톰캣 예외페이지가 나오는 것
		}
	}
}
