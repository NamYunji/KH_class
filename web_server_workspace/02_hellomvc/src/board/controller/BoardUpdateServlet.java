package board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * 게시글 수정 서블릿
 */
@WebServlet("/board/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * jsp 연결
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자 입력값
		int no = Integer.parseInt(request.getParameter("no"));
		// 2. 업무로직
		Board board = boardService.selectOne(no);
		// 3. jsp포워딩
		request.setAttribute("board", board); // board객체와 첨부파일까지
		request.getRequestDispatcher("/WEB-INF/views/board/boardUpdateForm.jsp")
				.forward(request, response);
	}

	/**
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// [파일 업로드]
		// 1. MultipartRequest객체 생성
		
		// < 파일을 저장할 경로 > 
		// /WebContent/upload/board/업로드파일명.jsp
		// 절대경로 - getServletContext()
		try {
		String saveDirectory = getServletContext().getRealPath("/upload/board");
		// 저장기능이 있는 객체들 (setAttribute, getAttribute) 
		// pageContext - request - session - application 객체
		// 단일 jsp - 이번 요청 - browser - was사용 내내 (서버 끄기 전까지)
		// applicaton객체에 접근할 수 있는 게 getServletContext()
		// application은 별칭이고, 실제 타입은 ServletContextType
		// 그것에 getRealPath하면 web root directory를 절대경로로 반환
		// -> WebContent + /upload/board
		System.out.println("saveDirectory@servlet = " + saveDirectory);
		
		// < 파일 최대 허용크기 >
		// -> 바이트 단위로 전달해야 함
		// 10mb = 10byte * 1kb * 1kb (1kb = 1024 byte)
		int maxPostSize = 10 * 1024 * 1024;
		
		// < 인코딩 >
		String encoding = "utf-8";
		
		// < 파일명 변경 정책 객체 >
		// 중복 파일인 경우, numbering 처리 -> 덮어쓰기 방지
		// 제공되는 인터페이스 사용
		// DefaultFileRenamePolicy() - 사용자가 입력한 파일명을 그대로 유지, 겹치면 넘버링
		// but 한글의 경우, encoding문제를 유발할 수 있음
		// 실행가능한 파일형태로 업로드하는 경우, 서버컴퓨터에 해를 끼칠 수 있음 -> 보안적으로 취약한 상태
		// fileRename 처리
		// 년월일시분초,난수까지 해서 절대 중복이 없을법한 파일구조로 만들기 (ex. 20210406191919_123.jpg)
		// -> 한글이 깨질 일도 없고, 보안측면에서도 좋음
		// FileRenamePolicy policy = new DefaultFileRenamePolicy();
		FileRenamePolicy policy = new MvcFileRenamePolicy();
		
		MultipartRequest multipartRequest =
				new MultipartRequest(
								request, // 기존 request
								saveDirectory, // 저장 경로, 어디에 파일 저장할지
								maxPostSize, // 파일 최대 허용크기
								encoding, // 인코딩
								policy // 파일명 변경 정책 객체
								);
		// -> 파일 저장이 완료됨
		
		// 2. db에 게시글/첨부파일 정보 저장
		// board table과 짝꿍으로 attachment table이 있는데,
		// board에 attachment 하나 이렇게 처리할 것!
		
		// 2-1. 사용자 입력값처리
		// cf. request가 아닌, multipartRequest에서 꺼낼 것
		int no = Integer.parseInt(multipartRequest.getParameter("no"));
		String title = multipartRequest.getParameter("title");
		String writer = multipartRequest.getParameter("writer");
		String content = multipartRequest.getParameter("content");	
		
		// 업로드한 파일명 가져오기
		String originalFileName = multipartRequest.getOriginalFileName("upFile"); // 사용자가 업로드한 파일명
		String renamedFileName = multipartRequest.getFilesystemName("upFile"); // 실제 저장된 파일명
		
		// 수정하기 - 기존 첨부파일이 삭제되는 경우
		// Board객체를 만들기 전 체크박스의 value값인 첨부파일 고유번호를 가져옴
		String attachNo = multipartRequest.getParameter("delFile"); // 삭제할 파일번호
		System.out.println("attachNo@servlet = " + attachNo);
		// query에서 자동형변환 처리되므로 그냥 String그대로 놔둠
		
		// Board board = new Board(0, title, writer, content, null, 0, null);
		Board board = new Board();
		board.setNo(no);
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		// 첨부파일이 있을 때만 attach추가
		// multipartRequest.getFile("upFile"):File -> 파일 객체 리턴 -> != null
		if(originalFileName != null) {
			// attachment 객체 생성
			Attachment attach = new Attachment();
			// 첨부파일을 새로 추가했다면 no가 이미 있으니까 바로 세팅 가능 (insert와의 차이점)
			attach.setBoardNo(no);
			attach.setOriginalFileName(originalFileName);
			attach.setRenamedFileName(renamedFileName);
			// board에 attach를 세팅
			board.setAttach(attach);
		}
		
		// 2 - 2. 업무로직
		// 첨부파일 삭제
		// delete이긴 하나, 실제 쿼리는 update로 진행
		int result = 0;
		// 체크박스가 선택될 경우에만 value값이 들어옴 -> attachNo가 null이 아닌 경우 -> 삭제하기가 체크된 경우
		if(attachNo != null)
			// 파일 고유번호를 가지고 service단의 메소드 실행
			result = boardService.deleteAttachment(attachNo);

		// db에 update처리
		result = boardService.updateBoard(board);
		String msg = (result > 0) ? 
			"게시물 수정 성공!" : "게시물 수정 실패!";
		String location = request.getContextPath()
					+ "/board/boardView?no=" + board.getNo();
		// 성공이든 아니든 게시물 상세보기페이지로 이동	
		
		// 3. DML요청 : 리다이렉트 & 사용자피드백 (alert)
		// /mvc/board/boardList
		HttpSession session = request.getSession();
		session.setAttribute("msg", msg);
		response.sendRedirect(location);
	} catch(Exception e) {
		e.printStackTrace();
		throw e;
	  }
	}
}
