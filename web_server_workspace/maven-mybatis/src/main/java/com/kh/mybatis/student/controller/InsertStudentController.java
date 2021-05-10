package com.kh.mybatis.student.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;
import com.kh.mybatis.student.model.vo.Student;

public class InsertStudentController extends AbstractController {

	// controller가 의존하는 service단 객체는 interface를 통해 제어한다
	// interface : StudentService
	// 실제 구현클래스 : StudentServiceImpl
	private StudentService studentService = new StudentServiceImpl();
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 [원래의 방식]
		 requestDispatcher를 통해서 jsp로 포워딩했었음
		 RequestDispatcher reqDispatcher
			= request.getRequestDispatcher("/menu/menuOrder.jsp");
		 reqDispatcher.forward(request, response);
		 */
		// 이제는 jsp를 리턴해버림 -> 그 후로는 dispatcherServlet이 알아서 처리
		// return "/WEB-INF/views/student/insertStudent.jsp";
		// -> [/WEB-INF/views/] [.jsp] 중복되는 부분 제거
		return "student/insertStudent";
	}

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// 1. 사용자 입력값 처리
			String name = request.getParameter("name");
			String tel = request.getParameter("tel");
			Student student = new Student();
			student.setName(name);
			student.setTel(tel);
			
			// 2. 업무로직
			int result = studentService.insertStudent(student);
			
			// 3. 사용자 피드백 및 리다이렉트
			request.getSession().setAttribute("msg", "학생 등록 성공!");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// 리다이렉트 -> url바꾸기
		/**
		 [원래의 방식]
		 response.sendRedirect(request.getContextPath() + "/board/boardList");	
		 */
		// redirect:리다이렉트할 주소
		return "redirect:/student/insertStudent.do";
	}
}
