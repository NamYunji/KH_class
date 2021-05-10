package board.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * cos.jar 파일 업로드
	 * 0. form의 속성 enctype="multipart/form-data"추가
	 * 1. MultipartRequest객체 생성
	 * 		- request 객체
	 * 		- 파일을 저장할 경로
	 * 		- 인코딩
	 * 		- 최대허용크기
	 * 		- 파일명 변경정책 객체
	 * 		  (같은 이름의 파일이라도 덮어쓰지 않도록 변경해주는 룰을 정한 객체
	 * 			ex. 그림.png -> 그림(1).png )
	 * 		-> 서버컴퓨터에 파일 저장 완료
	 * 2. db에 파일정보를 저장
	 * 		- 사용자가 저장한 파일명 (original_filename)
	 * 		- 실제 저장된 파일명 (renamed_filename)
	 * 
	 * 주의
	 * MultipartRequest객체를 사용하면,
	 * 기존 HttpServletRequest에서는 사용자입력값에 접근 불가
	 * -> MultipartRequest를 사용해서 사용자 입력값까지 꺼내야 함
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
		String title = multipartRequest.getParameter("title");
		String writer = multipartRequest.getParameter("writer");
		String content = multipartRequest.getParameter("content");	
		
		// 업로드한 파일명 가져오기
		String originalFileName = multipartRequest.getOriginalFileName("upFile"); // 사용자가 업로드한 파일명
		String renamedFileName = multipartRequest.getFilesystemName("upFile"); // 실제 저장된 파일명
		
		// Board board = new Board(0, title, writer, content, null, 0, null);
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		// 첨부파일이 있을 때만 attach추가
		// multipartRequest.getFile("upFile"):File -> 파일 객체 리턴 -> != null
		if(originalFileName != null) {
			// attachment 객체 생성
			Attachment attach = new Attachment();
			attach.setOriginalFileName(originalFileName);
			attach.setRenamedFileName(renamedFileName);
			// board에 attach를 세팅
			board.setAttach(attach);
		}
		
		// 2 - 2. 업무로직 : db에 insert처리
		int result = boardService.insertBoard(board); // 이때의 board는 boardNo가 없음, insertBoard를 완료하고 나면 board에 boardNo가 세팅됨
		String msg = (result > 0) ? 
			"게시물을 등록하였습니다." : "게시물 등록에 실패했습니다.";
		String location = request.getContextPath();
		location += (result > 0) ?
						"/board/boardView?no=" + board.getNo() : // 이때의 board는 boardNo가 있음
							"/board/boardList";
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