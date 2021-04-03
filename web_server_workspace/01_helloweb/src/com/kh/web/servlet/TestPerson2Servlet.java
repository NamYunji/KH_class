package com.kh.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestPerson2Servlet extends HttpServlet{

	// doPost() 오버라이드
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		// 0. encoding 선언
		request.setCharacterEncoding("utf-8");
		
		// 1. 사용자 입력값 변수에 담기
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		String[] foodArr = request.getParameterValues("food");
		
		System.out.println(name);
		System.out.println(color);
		System.out.println(animal);
		System.out.println(Arrays.toString(foodArr));
		
		// 2. 업무로직
		// 색깔로 메뉴 추천
		String recommendation = "";
		switch(color) {
		case "빨강" : recommendation = "빨간 짬뽕"; break;
		case "노랑" : recommendation = "노란 참외"; break;
		case "초록" : recommendation = "초록색 시금치"; break;
		case "파랑" : recommendation = "파란 파워에이드"; break;
		}
		
		// jsp에 recommendation(새로 생성된 data)을 전달하기 위해 
		// request의 속성으로 저장한다.
		request.setAttribute("recommendation", recommendation);
		// void javax.servlet.ServletRequest.setAttribute(String name, Object o)
		// 속성은 map과 비슷해서 key:String, value:Object 로 저장함
		// object - 모든 자바 타입을 다 담을 수 있다는 뜻!
		// 직접 jsp에 담아두지 못하니까, request에 담아두는 것
		
		// 3. 응답메시지 처리 html 
		// html코드는 jsp 파일에서 작업 -> html작성을 jsp에 위임
		// RequestDispatcher 객체 : 보내는 역할 (dispatching = send)
		// request.getRequestDispatcher("jsp의 위치")
		// 경로지정시, 슬래시로 시작한다면 /WebContent에서 바로 조회함
		
		// 객체를 가져옴
		RequestDispatcher reqDispatcher
			= request.getRequestDispatcher("/servlet/testPersonEnd.jsp");
		
		// 객체로 부터 forward()메소드 호출
		// request객체와 response객체를 같이 넘겨줘야 함
		reqDispatcher.forward(request, response);
		
		
		
		
		// -> 바톤터치 완료! forward이후로 코드 작성하면 오류남
		// jsp에게 응답할 html은 너가 작성해! 하고 넘겨버리는 것
		// WAS는 testPersonEnd.jsp에 가서 작업함
		
//		response.setContentType("text/html; charset=utf-8");
//		PrintWriter out = response.getWriter();
//		
//		out.println("<!DOCTYPE html>");
//		out.println("<html>");
//		out.println("<head>");
//		out.println("<title>개취 검사 결과</title>");
//		// css 효과주기
//		out.println("<style>");		
//		out.println(".recommendation { font-size: 2em; color: lime; text-decoration: underline; }");		
//		out.println("</style>");		
//		out.println("</head>");
//
//		out.println("<body>");
//		out.println("<h1>개인 취향 검사 결과 jsp</h1>");
//		out.println("<p>" + name + "님의 개인 취향 검사 결과는 </p>");
//		out.println("<p>" + color + "색을 좋아합니다.</p>");
//		out.println("<p>좋아하는 동물은" + animal + "입니다.</p>");
//		out.println("<p>좋아하는 음식은" + Arrays.toString(foodArr) + "입니다.</p>");
//		out.print("</hr>");
//		// 색상별 메뉴 추천
//		out.print("<p class='recommendation'>오늘은 "+ recommendation + "어떠세요?</p>");
//		out.println("</body>");
//		
//		out.println("</html>");
			
	}

}