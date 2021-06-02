package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 빈등록
@Aspect // aspect 클래스로 등록 -> 하위에 pointcut, advice를 가지고 있음
public class LoggerAspect {
	
	// pointcut 선언
	@Pointcut("execution(* com.kh.spring.memo..selectMemoList(..))")
	public void loggerPointcut() {}
	
	/**
	 * Around Advice
	 * : joinpoint 실행전, 실행후에 삽입되어 처리되는 advice (보조업무)
	 * 
	 * 인자로 전달된 ProceedingJoinPoint가 보조업무의 실제 메소드를 가리킴
	 * @throws Throwable 
	 */
	@Around("loggerPointcut()") // advice를 pointcut과 연결
	public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
		
		// joinPoint는 메소드의 시그니쳐를 가져와보기
		Signature signature = joinPoint.getSignature();
		
		// before
		// 필터랑 비슷, 주업무 joinpoint 실행전
		log.debug("----- {} start -----", signature);
		
		// 주업무 joinPoint 실행
		Object retrunObj = joinPoint.proceed();
		
		// after
		// 주업무 joinpoint 실행후
		log.debug("----- {} end -----", signature);
		
		return retrunObj;
	}
}
