package com.kh.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.person.model.vo.Person;

//index.jsp
@WebServlet("/standard/useBean.do")
public class UseBeanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 업무로직에 의해 생성된 데이터
		// request속성에 여러 데이터를 담아두고, jsp에서 가져다 쓸것
		Person person = new Person(); // Person객체
		// person객체에 데이터 담기
		person.setId("honggd");
		person.setName("홍길동");
		person.setAge(35);
		person.setGender('남');
		person.setMarried(true);
		// 이 데이터를 jsp에서 이용하도록 request에 속성으로 담아둠
		request.setAttribute("honggd", person);
		// 2. jsp위임
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/standard/useBean.jsp");
		reqDispatcher.forward(request, response); // request가 아닌 reqDispatcher가 forward하는 것
	}
}
