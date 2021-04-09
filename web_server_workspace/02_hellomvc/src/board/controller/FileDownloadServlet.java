package board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;

@WebServlet("/board/fileDownload")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 사용자 입력값 : no 게시글번호
		int no = Integer.parseInt(request.getParameter("no"));
		
		// 2. 업무로직 : 게시글번호로 첨부파일 조회
		// originalFileName, renamedFileName 필요
		Attachment attach = boardService.selectOneAttachment(no); 
		System.out.println("attach@servlet = " + attach);
		// attach@servlet = Attachment [no=28, boardNo=111, originalFileName=yunji.jpg, renamedFileName=20210408_183112732_435.jpg, status=true]
	
		// 3. 파일 다운로드
		// a. 입출력스트림 생성 (서버컴퓨터의 파일을 읽어서 응답메시지에 쓰기작업)
		// [입력 스트림]
		// 입력스트림 BufferedInputStream
		// 파일을 읽어들이는 기본스트림 FileInputStream(다운받을 파일의 경로(문자열 또는 파일객체로 받음))
		String saveDirectory = getServletContext().getRealPath("/upload/board"); // 파일이 저장된 경로
		File f = new File(saveDirectory, attach.getRenamedFileName());// 다운받을 파일 객체 File(파일의 경로, 파일 이름)
		BufferedInputStream bis = 
				new BufferedInputStream(new FileInputStream(f));
		// [출력 스트림]
		// 대상이 응답메시지인 출력스트림 가져오기
		// bufferedoutputstream의 대상이 응답메시지이므로
		// BufferedOutputStream 보조스트림만 만들고
		// 대상이 되는 주스트림은 새로 만들 필요가 없고 response에게 달라고 하면 됨
		BufferedOutputStream bos = 
				new BufferedOutputStream(response.getOutputStream());
		// b. 응답헤더작성 (이거 파일이야! 다운로드 받을 준비해!)
		// 인코딩을 위한 스트링 객체 새로 만들기
		// 바이트배열로 변환했다가 다시 스트링배열로 조합할 때 인코딩을 톰캣의 기본 인코딩값(ISO-8859-1)으로 바꿔서 문자열 만듦
		String responseFileName = new String(attach.getOriginalFileName().getBytes("utf-8"), "ISO-8859-1");
		System.out.println(responseFileName); // 여기서는 깨지지만 거꾸로 뒤집어 나올때는 한글이 잘 출력될 것
		// html의 경우 응답헤더를 jsp에서 대신 써주고 있었음 (jsp의 contentType="text/html; charset=UTF-8" - 전송하는 응답메시지의 타입은 text html이야!)
		// 이제는 text/html이 아니라 이진 data라는 걸 알려줘야 함
		// 이진데이터의 content type - application/octet-stream
		response.setContentType("application/octet-stream; charset=utf-8");
		// 첨부파일로 저장해야 하므로 파일명 지정 
		// 이거 첨부파일로 다운받아라 - setHeader("Content-Disposition", "attachment;filename=파일명"); 이거 첨부파일이야, 파일이름은 이거야!
		response.setHeader("Content-Disposition",
//							"attachment;filename=" + attach.getOriginalFileName()
							"attachment;filename=" + responseFileName
							);
		// c. 파일출력 (쓰기작업)
		int read = -1;
		// bufferedInputStream으로 읽어와서 read에 담고, 그게 -1이 아니라면 계속 돌아라
		while((read = bis.read()) != -1) {
			// 읽어온 내용을 bufferedOutputStream에 쓰기작업함
			bos.write(read);
		}
		// d. 자원반납
		bos.close();
		bis.close();
	}
}