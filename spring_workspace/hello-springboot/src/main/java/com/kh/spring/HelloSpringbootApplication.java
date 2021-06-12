package com.kh.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @SpringBootApplication 
 * cf. 어노테이션도 하나의 클래스이다
 * 주요 기능 3가지를 이 어노테이션 하나로 묶음
 * 1. @SpringBootConfiguration
 * 		springboot관련 설정
 * 2. @EnableAutoConfiguration
 * 		application-context를 관리
 * 		(context는 하나. 
 * 		 application-context, servlet-context로 나뉘지 않음)
 * 3. @ComponentScan
 * 		현재 실행클래스가 속한 base-package 기준으로 annotation 활성화
 */
@SpringBootApplication
public class HelloSpringbootApplication {

	// main 메소드가 있음
	// legacy프로젝트는 프로젝트를 지역서버와 연결해서 지역서버 안에 프로젝트가 배포되어 실행되는 구조
	// springboot는 프로젝트 하나에 이미 tomcat(내장서버)가 들어있음
	// 지역서버 연결이 없음!
	// main메소드 -> 프로젝트를 실행한다는 것 = 내장서버를 실행하는 것
	public static void main(String[] args) {
		// SpringApplication의 run이라는 static메소드를 호출하면서
		// 인자로 현재 어플리케이션의 클래스 전달
		SpringApplication.run(HelloSpringbootApplication.class, args);
	}
}
