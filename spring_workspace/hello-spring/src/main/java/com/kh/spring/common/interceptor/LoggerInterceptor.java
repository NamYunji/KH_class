package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * interceptor로서 사용하려면 HandlerInterceptorAdapter 상속해야 함
 * handler 앞뒤에서 가로채므로 HandlerInterceptor라고 부름
 */
@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter{

	/**
	 * Handler 호출전 실행
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("=============== start ===============");
		// request객체를 인자로 받기 때문에 이번 요청주소를 알 수 있음
		log.debug(request.getRequestURI());
		log.debug("-------------------------------------");
		// 리턴절은 반드시 호출해줘야 그 다음 핸들러가 호출됨
		return super.preHandle(request, response, handler); // 무조건 true를 리턴
		// 만약 return false라면 이후 코드는 실행되지 앟음
	}

	/**
	 * Handler 리턴 이후 실행
	 * handler가 작업한 ModelAndView객체 확인 가능
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		// 호출 이후에야 modelAndView객체 확인 가능
		log.debug("-------------------------------------");
		// ModelAndView는 model객체와 view에 대한 정보를 가지고 있음
		log.debug("modelAndView = {}", modelAndView);
	}

	/**
	 * view단(jsp) 작업 이후 실행
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.debug("-------------- view --------------");
		super.afterCompletion(request, response, handler, ex);
		log.debug("______________ end ______________\n");
	}

	
}
