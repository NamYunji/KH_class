package com.kh.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	// getMapping - / -> 인덱스 요청시 이 메소드 응답
	// responseBody -> 리턴된 것을 응답메시지 body에 출력
	@GetMapping("/")
	public String home() {
		log.debug("home요청!");
		return "forward:index.jsp";
	}
}
