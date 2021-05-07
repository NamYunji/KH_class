package com.kh.mybatis.common;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.exception.ControllerNotFoundException;
import com.kh.mybatis.common.exception.MethodNotAllowedException;

import sun.rmi.server.Dispatcher;


@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, AbstractController> urlControllerMap = new HashMap<>();

	/**
	 * 서블릿 생성시 최초 1회 실행
	 * 사용자 요청주소를 컨트롤러 객체와 binding할 수 있는 정보를 가진 map객체 생성
	 * 
	 * /student/selectOneStudent.do -> SelectOneStudentController가 연결되도록
	 * /student/insertStudent.do -> InsertStudentController
	 * ...
	 * 
	 */
	public void init(ServletConfig config) throws ServletException {
		Properties prop = new Properties();
		// url-command-properties의 내용을 읽어서 담아두기
		String fileName = DispatcherServlet.class
											.getResource("/url-command.properties") // 절대경로로 읽어옴
											.getPath(); // 문자열을 읽어옴
		// java reflection 
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<String> keys = prop.stringPropertyNames();
		for(String key : keys) {
			String value = prop.getProperty(key); // 풀네임의 클래스명 
			// Controller 객체화
			// new SomeController(); - 여러 클래스가 오기 때문에 매번 달라짐
			try {
				// 클래스 객체 : 해당 클래스의 구성정보를 가지고 있음 (필드, 메소드, 접근제한자 ...)
				Class clazz = Class.forName(value);
				// 이 클래스 객체를 가지고 객체 만들기
				AbstractController controller 
						= (AbstractController) clazz.newInstance();
				// urlControllerMap에 추가
				urlControllerMap.put(key, controller);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println("urlControllerMap@init = " + urlControllerMap);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 사용자 요청주소 가져오기
		String uri = request.getRequestURI(); // /maven-mybatis/student/insertStudent.do 리턴
		int beginIndex = request.getContextPath().length();
		String url = uri.substring(beginIndex); // /student/insertStudent.do 리턴
		
		// 2. controller객체 호출
		AbstractController controller = urlControllerMap.get(url);
		if(controller == null)
			throw new ControllerNotFoundException(url + "에 해당하는 controller가 없습니다.");
		
		String method = request.getMethod();
		String viewName = null;
		switch(method) {
		case "GET" : viewName = controller.doGet(request, response); break;
		case "POST" : viewName = controller.doPost(request, response); break;
		default : throw new MethodNotAllowedException(method);
		}
		
		// 3. jsp forwarding 또는 redirect 처리
		if(viewName != null) {
			if(viewName.startsWith("redirect:")) {
				// redirect
				String location = request.getContextPath() + viewName.replace("redirect:", "");
				response.sendRedirect(location);
			} else {
				// jsp forwarding
				final String prefix = "/WEB-INF/views/";
				final String suffix = ".jsp";
				request.getRequestDispatcher(prefix + viewName + suffix)
					   .forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
