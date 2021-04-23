package com.kh.ajax.text;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/text")
public class AjaxTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// servlet에서 하는 역할은 이전과 동일함
		// but 이때는 jsp로 넘기지 않음
		// 1. 사용자 입력값 처리
		String name = request.getParameter("name");
		System.out.println("name@servlet.doPost = "+ name);
		// 2. 업무로직
		// 3. 응답처리  : jsp로 넘기지 않고 출력스트림을 이용해서 응답메시지 직접 작성
		// response.setContentType - 응답메시지가 어떤 타입인지, 인코딩은 어떻게 할지
		// text/plain 그냥 텍스트니까 text/html이 아닌 text/plain
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("안녕");
		out.println("12345");
		out.println("<h1>" + name + "</h1>");
		out.println(new Date());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자 입력값 처리
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		System.out.println("name@servlet.doPost = "+ name);
		// 2. 업무로직
		// 3. 응답처리  : jsp로 넘기지 않고 출력스트림을 이용해서 응답메시지 직접 작성
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("POST로 요청 하셨습니다.");
		out.println("이름은 [" + name + "]입니다.");
		out.println("요청처리 시각은  "+ new Date() + "입니다.");
	}
}
