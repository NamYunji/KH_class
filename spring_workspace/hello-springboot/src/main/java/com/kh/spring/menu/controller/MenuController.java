package com.kh.spring.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	// 메뉴수정 (조회)
//	@GetMapping("/menu/{id}")
//	public Menu selectOneMenu(@PathVariable String id) {
//		try {
//			log.debug("id = {}", id);
//			
//			Menu menu = menuService.selectOneMenu(id);
//			return menu;
//		} catch (Exception e) {
//			log.error("메뉴조회 오류 : " + id, e);
//			throw e;
//		}
//	}
	
	// 메뉴수정 (조회) - ResponseEntity 사용
	// ResponseEntity를 통해서 존재하지 않는 메뉴번호를 요청한 경우
	// 404 status code를 응답
	// ResponseEntity는 status code를 마음대로 설정 가능
	@GetMapping("/menu/{id}")
	public ResponseEntity<Menu> selectOneMenu(@PathVariable String id) {
		try {
			log.debug("id = {}", id);
			
			Menu menu = menuService.selectOneMenu(id);
			
			if(menu != null) {
				return ResponseEntity
						.ok()
						.body(menu);
			} else {
				return ResponseEntity
						.notFound()
						.build();
			}
		} catch (Exception e) {
			log.error("메뉴조회 오류 : " + id, e);
			throw e;
		}
	}
	
	// 메뉴수정 (수정)
	// pathVariable로 아이디 값을 받아서 처리해도 되지만
	// 대신 json으로 통으로 날라왔기 때문에 requestBody로 해도 됨
	@PutMapping("/menu/{id}")
	public Map<String, Object>  updateMenu(@RequestBody Menu menu) {
		try {
			log.debug("menu = {}", menu);
			int result = menuService.updateMenu(menu);
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "메뉴 수정 성공!");
			return map;
		} catch (Exception e) {
			log.error("메뉴수정 실패!", e); 
			throw e;
		}
	}
	
	/**
	 * ResponseEntity의 방식
	 * 1. builder패턴
	 * 		static메소드를 연이어 호출해가면서 객체 완성
	 * 2. 생성자 방식 : 이 방식 사용함
	 */
	// 메뉴 삭제
	// ResponseEntity를 이용해서 삭제 실패까지 처리
	// <?> : 리턴하고 있는 객체의 타입에 따라 그대로 적용됨
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<?> deleteMenu(@PathVariable String id){
		try {
			log.debug("id = {}", id);
			int result = menuService.deleteMenu(id);
			if(result > 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("msg", "메뉴 삭제 성공!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404를 넘김
			}
		} catch (Exception e) {
			log.error("메뉴 삭제 실패!", e);
			throw e;
		}
	}
}
