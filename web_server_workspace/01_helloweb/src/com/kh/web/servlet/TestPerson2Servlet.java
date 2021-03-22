package com.kh.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestPerson2Servlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		// 0. 인코딩 선언
		// http message body부분 인코딩이 유효하도록 한다
		request.setCharacterEncoding("utf-8");
		
		
		//1. 사용자입력값 가져오기
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		String[] foodArr = request.getParameterValues("food");
		
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("foodArr = " + Arrays.toString(foodArr));
		
		
		// 2. 업무로직
		
		// 3. 응답메세지처리 html
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>개취 검사 결과</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>개인 취향 검사 결과 POST</h1>");
		out.println("<p>" + name + "님의 개인취향 검사 결과는 </p>");
		out.println("<p>" + color + "색을 좋아합니다. </p>");
		out.println("<p>좋아하는 동물은 " + animal + "입니다.</p>");
		out.println("<p>좋아하는 음식은 " + Arrays.toString(foodArr)+ "입니다. </p>");
		out.println("</body>");
		out.println("</html>");
	
	}

	
}
