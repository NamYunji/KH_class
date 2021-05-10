package com.kh.mybatis.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.exception.MethodNotAllowedException;

public abstract class AbstractController {
	
	// 목적 : Controller클래스에서 필요한 메소드를 override해서 사용하도록 함
	// return String - viewName : jsp 경로 또는 redirect의 location값 리턴
	// 추상메소드로 만들지는 않고, 미리 구현을 해둘 것
	public String doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {		
		// 메소드를 오버라이드하지 않고 호출시, 예외를 던짐
		throw new MethodNotAllowedException("GET");
	}
	
	public String doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			throw new MethodNotAllowedException("POST");
	}
}
