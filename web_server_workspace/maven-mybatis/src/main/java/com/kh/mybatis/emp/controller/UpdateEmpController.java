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

public class UpdateEmpController extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	// updateFrm을 제공
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. 사용자 입력값
			// cf. empId는 숫자이지만, db에서 처리되는건 문자로 처리되기 때문에 별도의 형변환 x
			String empId = request.getParameter("empId");
			System.out.println("empId@controller = " + empId);
			
			// 2. 업무로직 : 사용자가 변경할 수 있는 직급목록, 부서목록, 사원1명 정보(부서명, 직급명)
			List<Map<String, String>> jobList = empService.selectJobList();
			List<Map<String, String>> deptList = empService.selectDeptList();
			Map<String, Object> emp = empService.selectOneEmp(empId);
			System.out.println("emp@controller = " + emp);
			
			// 넘겨받은 emp가 null인 상황을 대비
			if(emp == null) {
				// null인 이유는 empId가 문제가 있었기 때문
				// 전달받은 인자가 문제 -> IllegalArgumentException
				throw new IllegalArgumentException(empId);
			}
			
			// 3. jsp위임
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("emp", emp);
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return "emp/updateEmp";
	}
	
	// 실제 update 수정 요청 처리
	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String empId;
		try {
			// 1. 사용자 입력값 처리
			Map<String, String> param = new HashMap<>();
			// 사용자 입력값을 empId에 대입하고, 대입된 empId를 다시 value로 사용
			param.put("empId", empId = request.getParameter("empId"));
			param.put("jobCode", request.getParameter("jobCode"));
			param.put("deptCode", request.getParameter("deptCode"));
			System.out.println("param@controller = " + param);
			// param@controller = {empId=223, jobCode=J3, deptCode=D7} - 모두선택함
			// param@controller = {empId=223, jobCode=J4, deptCode=} - 직급만 선택함
			// -> 선택하지 않은 것은 빈문자열이 넘어감
			
			// 2. 업무로직
			int result = empService.updateEmp(param);
			if (result == 0)
				throw new  IllegalArgumentException(empId);
			// 3. 사용자 피드백 & 리다이렉트
			request.getSession().setAttribute("msg", "사원정보 수정 성공!");
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/emp/updateEmp.do?empId=" + empId;
	}
}
