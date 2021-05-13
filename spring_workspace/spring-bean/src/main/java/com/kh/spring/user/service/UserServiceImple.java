package com.kh.spring.user.service;

import org.springframework.stereotype.Component;

@Component
public class UserServiceImple implements UserService {

	@Override
	public void getUserDetail(String id) {
		System.out.println("사용자 " + id + "의 이름은 홍길동입니다.");
	}
}

