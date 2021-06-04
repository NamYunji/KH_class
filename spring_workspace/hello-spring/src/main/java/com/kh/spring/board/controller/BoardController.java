package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;
import com.kh.spring.common.util.HelloSpringUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

	// 절대경로를 가져오기 위한 의존주입
	@Autowired
	private ServletContext application;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("boardList.do")
	public String boardList(
				@RequestParam(required = true, defaultValue = "1") int cPage,
				Model model,
				HttpServletRequest request
			) {
		try {
			log.debug("cPage = {}", cPage);
			final int limit = 10;
			final int offset = (cPage - 1) * limit;
			// service로 넘길 때 limit, offset을 둘 다 넘겨도 되지만
			// 여러 인자를 넘기는게 부담스러운 일
			// -> map으로 담아서 넘기기
			Map<String, Object> param = new HashMap<>();
			param.put("limit", limit);
			param.put("offset", offset);
			// 1. 업무로직 : content영역 - Rowbounds
			List<Board> list = boardService.selectBoardList(param);
			
			// pageBar 가져오기
			// 전체페이지수, 현재페이지, 한페이지당 게시글 수, 이동할 url
			int totalContents = boardService.selectBoardTotalContents();
			String url = request.getRequestURI(); // 현재 요청페이지
			log.debug("totalContents = {}, url = {}", totalContents, url);
			// totalContents = 60, url = /spring/board/boardList.do
			
			String pageBar = HelloSpringUtils.getPageBar(totalContents, cPage, limit, url);
			
			// 2. jsp에 위임
			model.addAttribute("list", list);
			model.addAttribute("pageBar", pageBar);
		} catch (Exception e) {
			log.error("게시글 조회 오류!", e);
			throw e;
		}
		return "board/boardList";
		// board테이블안에서 첨부파일이 있는지 확인하는 법
		// 이걸 상속하는 vo클래스를 만들어서 자식클래스에서 해결
	}
	
	@GetMapping("/boardForm.do")
	public void boardFrom() {}
	
	@PostMapping("/boardEnroll.do")
	public String boardEnroll(
						@ModelAttribute BoardExt board,
						// 업로드한 파일은 별도로 처리됨
						// 여러개의 첨부파일을 받으므로 배열로 받기
						// MultipartFile upFile
						@RequestParam(name = "upFile") MultipartFile[] upFiles,
						RedirectAttributes redirectAttr
						) throws Exception {
		log.debug("board = {}", board);
		
		try {
			// 1. 서버컴퓨터에 파일 저장 : 절대경로 /resources/upload/board
			
			// saveDirectory 객체 생성
			// 여기서 application은 servletContext (application의 타입)
			// cf. 생명주기와 타입
			// pageContext:PageContext - request:HttpServletRequest - session:HttpSession - application(ServletContext)
			String saveDirectory = application.getRealPath("resources/upload/board");
			log.debug("saveDirectory = {}", saveDirectory);
			
			// 디렉토리 생성
			// 디렉토리가 존재하지 않을 경우, 프로그래밍적으로 생성하기
			// upload폴더와 board폴더를 생성해줌
			File dir = new File(saveDirectory);
			if(!dir.exists())
				dir.mkdirs(); // 복수개의 디렉토리를 생성
			
			// 복수개의 attachment를 list로 관리
			List<Attachment> attachList = new ArrayList<>();
			
			// 파일을 경로에 저장
			for(MultipartFile upFile : upFiles) {
				// input[name=upFile]로부터 비어있는 upFile이 넘어온다. (파일 선택을 안해도 null이 아님)
				if(upFile.isEmpty()) continue; // continue를 통해 이하코드 진행되지 않도록
				
				// 파일명 변경
				String renamedFilename 
					= HelloSpringUtils.getRenamedFilename(upFile.getOriginalFilename());
				
				// a. 서버컴퓨터에 저장
				// file 객체 생성(부모디렉토리, 파일명)
				File dest = new File(saveDirectory, renamedFilename);
				upFile.transferTo(dest); // 파일 이동 // 예외 던짐
				
				// b. 저장된 데이터를 Attachment객체에 저장 및 list에 추가
				Attachment attach = new Attachment();
				attach.setOriginalFilename(upFile.getOriginalFilename());
				attach.setRenamedFilename(renamedFilename);
				// attachList에 차곡차곡 담기
				attachList.add(attach);
			}
			
			log.debug("attachList = {}", attachList);
			// attachList =
			// [Attachment(no=0, boardNo=0, originalFileName=남윤지님 반명함.jpg, renamedFileName=20210602_165147989_476.jpg, uploadDate=null, downloadCount=0, status=false), 
			// Attachment(no=0, boardNo=0, originalFileName=남윤지님 블루.jpg, renamedFileName=20210602_165147995_660.jpg, uploadDate=null, downloadCount=0, status=false)]
	
			// board객체에 설정
			// boardExt객체이기 때문에 setAttachList가 있음
			board.setAttachList(attachList);
			
			// 2. 업무로직 : db저장 (board, attachment테이블 모두 insert)
			// 서비스 요청은 단일요청으로 처리
			int result = boardService.insertBoard(board);
			
			// 3. 사용자피드백 & 리다이렉트
			redirectAttr.addFlashAttribute("msg", "게시글 등록 성공!");
		} catch (Exception e) {
			log.error("게시글 등록 오류!", e);
			throw e;
		}
		// 상세보기 페이지로 이동
		// 이동시 등록한 board의 고유번호를 함께 가지고 감
		return "redirect:/board/boardDetail.do?no=" + board.getNo();
		
//		for(MultipartFile upFile : upFiles) {
//			log.debug("upFile = {}", upFile); // upFile = org.springframework.web.multipart.commons.CommonsMultipartFile@29d679e6
//			log.debug("upFile.name = {}", upFile.getOriginalFilename()); // upFile.name = 남윤지님 블루.jpg | 파일을 업로드하지 않으면 빈문자열이 넘어옴
//			log.debug("upFile.size = {}", upFile.getSize()); // upFile.size = 106833
//			log.debug("-------------------------------------");
//		}
	}
	
	@GetMapping("/boardDetail.do")
	public void selectOneBoard(@RequestParam int no, Model model) {
	}
}
	
