package com.kh.spring.user.service;

import org.springframework.stereotype.Component;

// 주의 : 인터페이스가 아닌 실제 구현클래스에 component 어노테이션 등록
@Component // bean으로 등록
public class UserServiceImpl implements UserService {

	@Override
	public void getUserDetail(String id) {
		System.out.println("사용자 " + id + "의 이름은 홍길동입니다.");
	}
}

