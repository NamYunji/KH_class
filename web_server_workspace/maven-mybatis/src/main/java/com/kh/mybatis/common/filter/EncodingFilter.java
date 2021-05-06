package com.kh.mybatis.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 서블릿 가기 전 인코딩 처리
		// 필터는 GET,POST를 구분하지 못함 -> GET,POST 구분없이 무조건 실행됨
		// GET도 인코딩 처리해도 상관없음
		request.setCharacterEncoding("utf-8");
		System.out.println("[utf-8] encoding 처리함");
		chain.doFilter(request, response);
	}
}
