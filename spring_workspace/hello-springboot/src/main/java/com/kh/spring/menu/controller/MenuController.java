package com.kh.spring.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.menu.model.service.MenuService;
import com.kh.spring.menu.model.vo.Menu;

import lombok.extern.slf4j.Slf4j;

// html이 아니라 data만 리턴하는 것을 유의하기!!
/**
 * @RestController
 * RestAPI는 데이터만 주고받기 때문에 HTML이 필요치 않음
 * RestAPI의 컨트롤러는 @RestController 어노테이션 사용
 * @RestController의 기능 
 * : @Controller + @ResponseBody
 *   -> 모든 handler는 @ResponseBody로 처리된다
 *   리턴하면 모두 json으로 변환해서 처리해줌
 */
@RestController
@Slf4j
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping("menus")
	public List<Menu> menus(){
		// 1. 업무로직
		List<Menu> list = menuService.selectMenulList();
		log.debug("list = {}", list);
		
		// 2. 리턴하면 자동으로 @ResponseBody에 의해서 json변환 후, 응답출력 처리
		return list;
	}
}
