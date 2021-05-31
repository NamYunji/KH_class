package com.kh.spring.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Slf4j
 * 
 * spring의 특징 (DI, IoC, POJO, PSA, AOP)
 * 중에서 PSA (Portable Service Abstraction)의 대표적 예시
 * 
 * [추상체]
 * Slf4j가 바로 스프링이 제공하는 logging 추상체
 * -> 실체 구현체는 이 Slf4j를 통해 제어한다
 * [구현체]
 * 1. log4j
 * 2. java.util.logging
 * 3. apache logging
 * 4. logback
 * 
 * spring app이 있고, 실제 구현체로 log4j를 사용한다하면
 * 직접적으로 log4j를 사용하지 않고, 추상체 slf4j를 사이에 두어서
 * 추상체를 통해 log4j를 제어함
 * 
 * 구현체들은 각각 다름
 * 직접적으로 log4j 구현체를 사용하게 되면 log4j만 사용할 수 있는 코드들을 사용함
 * 이때, logback으로 바꾸고 싶다면 log4j코드들을 다 변경해야 함
 * 추상체를 통해서 동일한 logging 코드를 작성하면
 * pom.xml의 의존만 변경하면 됨
 * -> 구현체가 뭐든간에 일관되게 코드 제어 가능
 */
@Slf4j // 어노테이션 사용 - 필드 선언 자동으로 해줌
public class Slf4jTest {
	// org.slf4j.Logger
	// private static final Logger log = LoggerFactory.getLogger(Slf4jTest.class);
	
	public static void main(String[] args) {
		// 공용이므로 구현체의 세세한 내용을 다 반영하지는 못함
		// log.fatal("fatal"); // slf4j에는 fatal레벨이 없음
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");
		
		// 실행결과는 동일함
		// ERROR: com.kh.spring.log.Slf4jTest - error
		// WARN : com.kh.spring.log.Slf4jTest - warn
		// INFO : com.kh.spring.log.Slf4jTest - info
		// DEBUG: com.kh.spring.log.Slf4jTest - debug
		
		// slf4j는 아래와 같은 문법도 지원
		log.error("error = {}", "메시지");
		// ERROR: com.kh.spring.log.Slf4jTest - error = 메시지
	}
}