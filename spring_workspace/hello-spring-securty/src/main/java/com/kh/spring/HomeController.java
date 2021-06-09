package com.kh.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	// 인덱스 요청하면 forwarding처리해서 index페이지로 가도록 되어있음
	@GetMapping("/")
	public String home() {
		log.info("home 요청!");
		return "forward:/index.jsp";
	}
	
	@GetMapping("/board/boardList.do")
	public void boardList() {}

	@GetMapping("/admin/memberList.do")
	public void memberList() {}
	
	@GetMapping("/error/accessDenied.do")
	public void accesDenied() {}
	
}
