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
			// Dao최종 코드에서 쿼리에 대한 아이디값을 찾고 전달할 값을 던지는데, 전달할 값은 하나로 전달해야 함
			// vo가 됐든 map이 됐든, 전달할 값을 하나로 만드는 게 중요함
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
