package com.kh.ajax.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.Member;

@WebServlet("/html")
public class AjaxHtmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 업무로직
		List<Member> list = new ArrayList<>();
		list.add(new Member("hwangj", "황제성", "hwang.jpg"));
		list.add(new Member("jjj", "쥴리아 로버츠", "juliaRoberts.jpg"));
		list.add(new Member("gone", "김고은", "김고은.jpg"));
		list.add(new Member("dfpk", "다프트펑크", "daftpunk.jpg"));
		
		// 2. jsp에 위임
		// why? html은 jsp가 잘만드니까!
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/html/member.jsp")
				.forward(request, response);
	}
}
