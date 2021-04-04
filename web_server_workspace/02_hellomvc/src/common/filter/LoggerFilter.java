package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoggerFilter implements Filter{
/**
 * 인터페이스의 추상메소드는 반드시 구현해야 함
 * but, 인터페이스 중 default 메소드는 구현하지 않아도 됨 (init(), destroy())
 * why? 
 * public default void init(FilterConfig filterConfig) throws ServletException {}
 * {} 몸통이 있어서 추상메소드가 아님
 */
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("LoggerFilter의 init 메소드 호출!");
	}
	
	@Override
	public void destroy() {
		System.out.println("LoggerFilter의 destory 메소드 호출!");
	}
	
	/**
	 * 일련의 처리를 doFilter안에 작성한다
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1. 서블릿 호출전
		// chain.doFilter를 기준으로 이전에 작성하면 서블릿 가기 전에 실행됨
		System.out.println("\n================================");
		// ServletRequest를 HttpServletRequest로 형변환
		HttpServletRequest httpReq = (HttpServletRequest)request;
		String uri = httpReq.getRequestURI();
		System.out.println(uri);
		System.out.println("--------------------------------");
		// 필수 : chain의 doFilter를 다시 호출
		// 다음 filterChain객체를 호출하는 것
		// 다음 filterChain객체가 존재하지 않으면, servlet 호출
		chain.doFilter(request, response);
		
		// 2. servlet/jsp 처리 이후
		// chain.doFilter를 기준으로 이후에 작성하면 서블릿 처리 후에 실행됨
		System.out.println("________________________________");
	}

}
