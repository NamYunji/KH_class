package com.kh.spring.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kh.spring.user.service.UserService;

@Component // bean으로 등록
@Scope("prototype") // scope속성 적용
@Lazy // lazy-init속성 적용, lazy-init="true"와 동일
public class UserController {
	/**
	 * @Autowired
	 * 1. field의 의존객체
	 */
	// @Autowired // 의존주입
	private UserService userService;

	/**
	 * @Autowired
	 * 2. setter
	 */
	// @Autowired // 의존주입
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	/**
	 * @Autowired
	 * 3. 생성자
	 */
	@Autowired // 의존주입
	public UserController(UserService userService) {
		this.userService = userService;
	} 
	// -> autowired 어노테이션은 필드, 세터, 생성자 영역 중 선택해서 하나만 작성해도 작동함
	
	public void getUserDetail(String id) {
		// 필드에 있는 의존객체의 메소드 호출
		userService.getUserDetail(id);
	}
}
