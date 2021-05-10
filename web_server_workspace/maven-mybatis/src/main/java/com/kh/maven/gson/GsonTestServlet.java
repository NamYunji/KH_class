package com.kh.maven.gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/gson.do")
public class GsonTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "gson maker");
		map.put("server-time", System.currentTimeMillis());
		map.put("num", 123456);
		// 응답헤더 작성
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		// 첫번째 인자인 map을 자바객체로 변환해서 응답객체로 써라
		gson.toJson(map, response.getWriter());
		// {num: 123456, name: "gson maker", server-time: 1620287932359}
	}
}
