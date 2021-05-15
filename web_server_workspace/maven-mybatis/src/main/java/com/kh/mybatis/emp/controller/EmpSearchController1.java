package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;

public class EmpSearchController1 extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. 사용자 입력값
			// 검색어 처리
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			// 전달할 값이 searchType, searchKeyword 두개이므로 하나로 만들기 위해 map에 담아둠
			Map<String, Object> param = new HashMap<>();
			param.put("searchType", searchType);
			param.put("searchKeyword", searchKeyword);
			
			System.out.println("param@controller = " + param);
			
			// 2. 업무로직
			// kh.employee테이블의 모든 행 조회
			List<Map<String, Object>> list = null;
			if(searchType == null || searchKeyword == null)
				// searchType이나 searchKeyword가 null일 경우에만 전체직원 조회
				list = empService.selectAllEmp();
			else
				// 검색조건이 있다면 search1호출
				list = empService.search1(param);
			
			System.out.println("list@controller = " + list);
			
			// 3. jsp위임
			request.setAttribute("list", list);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "emp/search1";
	}
}
