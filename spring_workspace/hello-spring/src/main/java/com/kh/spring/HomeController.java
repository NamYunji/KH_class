package com.kh.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// / (root)로 요청이 들어왔고, get방식이라면 이 메소드가 이번 요청을 처리한다!
	// welcome file이 /보다 우선순위가 높음
	// -> welcome file이 준비되어 있으면 controller를 거치지 않음
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		log.info("index 페이지 요청!");
		// forward 키워드 -> index.jsp로 바로 넘김
		// viewResolver를 사용하지 않음 
		// 그렇지 않으면 WEB-INF/views에 가서 index.jsp를 찾음
		return "forward:/index.jsp";
	}
}
