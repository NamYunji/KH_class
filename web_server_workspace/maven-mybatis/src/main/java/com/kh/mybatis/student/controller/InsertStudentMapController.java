package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;

public class InsertStudentMapController extends AbstractController {

	private StudentService studentService = new StudentServiceImpl();

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 사용자 입력값
			String name = request.getParameter("name");
			String tel = request.getParameter("tel");
			// Map에 담으면 vo클래스는 만들지 않아도 됨
			// vo객체로 모아담든지, map으로 모아담든지, 전달할 값을 하나로 만드는 게 중요함
			// Dao최종 코드에서 쿼리에 대한 아이디값을 찾고 전달할 값을 던지는데, 전달할 값은 딱 하나밖에 못 전달함
			// ex. session.insert("student.insertStudent", student); - student 대신 name, tel 이런식으로 불가
			Map<String, Object> student = new HashMap<>();
			student.put("name", name);
			student.put("tel", tel);
			System.out.println("studentMap@controller = " + student);
			
			// 2. 업무로직
			int result = studentService.insertStudentMap(student);
			
			// 3. 사용자 피드백 & redirect
			request.getSession().setAttribute("msg", "학생 정보 등록 성공");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/student/insertStudent.do";
	}
}
