package com.kh.spring.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// 전체메뉴 조회
	@GetMapping("menus")
	public List<Menu> menus(){
		try {
			// 1. 업무로직
			List<Menu> list = menuService.selectMenulList();
			log.debug("list = {}", list);
			
			// 2. 리턴하면 자동으로 @ResponseBody에 의해서 json변환 후, 응답출력 처리
			return list;
		} catch (Exception e) {
			log.error("/menus 오류!", e);
			throw e;
		}
	}
	
	// 추천메뉴 조회
	// @pathVariable 경로변수
	// {변수명} parameter부로 넘긴 변수를 가져다 쓸 수 있음
	@GetMapping("/menus/{type}/{taste}")
	public List<Menu> menuByType(@PathVariable String type, @PathVariable String taste){
		try {
			log.debug("type = {}, taste = {}", type, taste);
			Map<String, Object> param = new HashMap<>();
			param.put("type", type);
			param.put("taste", taste);
			
			List<Menu> list = menuService.selectMenuListByTypeAndTaste(param);
			log.debug("list = {}", list);
			
			return list;
		} catch (Exception e) {
			log.error("/menus/type 오류!", e);
			throw e;
		}
	}
	
	// 메뉴등록
	// Enum을 따로 빼서 처리하기보다는 @RequestBody 어노테이션 사용하기
	// @RequestBody 요청메시지의 body에 작성된 json문자열을 java객체로 변환
	// cf. @ResponseBody : 응답메시지에 직접 출력
	@PostMapping("/menu")
	public Map<String, Object> insertMenu(@RequestBody Menu menu){
		try {
			log.debug("menu = {}", menu);
			int result = menuService.insertMenu(menu);
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "메뉴등록 성공!");
			// 비동기요청이기 때문에 redirect할 필요없음
			return map;
		} catch (Exception e) {
			log.error("메뉴 등록 실패!");
			throw e;
		}
	}
}
