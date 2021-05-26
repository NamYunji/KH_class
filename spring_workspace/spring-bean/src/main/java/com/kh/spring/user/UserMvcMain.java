package com.kh.spring.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kh.spring.user.controller.UserController;

public class UserMvcMain {
	public static void main(String[] args) {
		// applicationContext 객체 생성
		String configLocation = "/application-context-with-annotation.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		// applicationContext 객체를 생성함과 동시에 spring-container는 이미 실행준비가 완료됨
		System.out.println("------ spring-contrainer bean 초기화 완료 ------");
		
		// 객체 가져오기
		UserController controller1 = context.getBean(UserController.class);
		controller1.getUserDetail("honggd");
		
		UserController controller2 = context.getBean(UserController.class);
		controller2.getUserDetail("sinsa");
		// 같은 객체 리턴
		
		System.out.println(controller1);
		System.out.println(controller2);
		System.out.println(controller1 == controller2); // true
	}
}
