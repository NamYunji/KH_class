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

/**
 * Servlet의 LifeCycle
 * 객체생성 - init - service - doGet/doPost - destroy
 */
@WebServlet("*.do") // 슬래시 사용하지 말기
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// prop을 읽어와서는 
	// url-pattern:String - Controller객체를 key, value형식으로 담기
	// -> AbstractController가 된다.
	// doGet, doPost에도 써야하기 때문에 필드로 선언
	private Map<String, AbstractController> urlControllerMap = new HashMap<>();

	/**
	 * init()
	 * 서블릿 생성시 최초 1회 실행
	 * 사용자 요청주소를 컨트롤러 객체와 binding할 수 있는 정보를 가진 map객체 생성
	 * ex)
	 * /student/selectOneStudent.do -> SelectOneStudentController
	 * /student/insertStudent.do -> InsertStudentController
	 * ...
	 */
	public void init(ServletConfig config) throws ServletException {
		Properties prop = new Properties();
		// url-command-properties의 내용을 읽어서 prop객체에 담아두기
		String fileName = DispatcherServlet.class
											.getResource("/url-command.properties") // 절대경로로 읽어옴
											.getPath(); // 문자열로 리턴받음
		// java reflection 
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// prop의 내용을 urlControllerMap에 key,value로 넣어줄건데 객체를 만들어서 넣어줄 것
		// 1) prop의 stringPropertyNames() - key값만 모아서 리턴
		Set<String> keys = prop.stringPropertyNames();
		// for each문으로 하나씩 열람
		for(String key : keys) {
			// 2) value값 가져오기
			// 2.1) prop의 getProperty() - value값을 가져옴
			String value = prop.getProperty(key); // 풀네임의 클래스명 
			// -> 2.2) Controller 객체화
			// new SomeController(); - 이 부분이 여러 클래스가 오기 때문에 매번 달라짐
			try {
				// 2.3) 클래스 객체 -> value에 넣을 controller객체 만들기
				// class.forName(클래스 파일명) - 이에 해당하는 클래스 객체를 리턴
				// 클래스 객체 : 해당 클래스의 구성정보를 가지고 있음 (필드, 메소드, 접근제한자 ...)
				Class clazz = Class.forName(value); 
				System.out.println("clazz = " + clazz);
				// class com.kh.mybatis.student.controller.InsertStudentController
				// 이 클래스 객체를 가지고 객체 만들기
				AbstractController controller 
						= (AbstractController) clazz.newInstance();
				System.out.println("controller = " + controller);
				// com.kh.mybatis.student.controller.InsertStudentController@fa2cad1
				// 4) urlControllerMap에 key, value 추가
				urlControllerMap.put(key, controller);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// forName(value) -> ClassNotFoundException
				// newInstance() -> InstantiationException, IllegalAccessException
				e.printStackTrace();
			}
		}
		System.out.println("urlControllerMap@init = " + urlControllerMap);
		// urlControllerMap@init = 
		// {/student/selectOneStudentMap.do=com.kh.mybatis.student.controller.SelectOneStudentMapController@37e0f278, 
		// /student/selectOne.do=com.kh.mybatis.student.controller.SelectOneController@72e27ae0,
		// /student/insertStudentMap.do=com.kh.mybatis.student.controller.InsertStudentMapController@b4b7d6d, 
		// /student/insertStudent.do=com.kh.mybatis.student.controller.InsertStudentController@43da8d77}
	}

	// 요청별로 짝지어진 controller객체를 찾아서 controller객체의 doGet이나 doPost를 호출
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 사용자 요청주소 가져오기
		// /maven-mybatis/student/insertStudent.do 리턴
		String uri = request.getRequestURI(); 
		// contextPath를 제외한 나머지 주소 구하기
		// /maven-mybatis/ - /의 인덱스 번호 = contextPath의 길이값
		int beginIndex = request.getContextPath().length();
		// subString(beginIndex) - 해당 인덱스부터 끝까지의 string을 리턴
		// /student/insertStudent.do 리턴
		String url = uri.substring(beginIndex); 
		
		// 2. controller객체를 찾아서 호출
		/**
		 	url이 key값으로 담겨있음
		 	get(Object key) 해당 key와 mapping된 value를 리턴
		 	ex.
			urlController.get(/student/insertStudent.do)
			-> com.kh.mybatis.student.controller.InsertStudentController 리턴
		 */
		AbstractController controller = urlControllerMap.get(url);
		// 해당 key와 mapping된 value가 없다면 null 리턴 
		if(controller == null)
			// 예외를 던져서 더 진행할 수 없도록
			throw new ControllerNotFoundException(url + "에 해당하는 controller가 없습니다.");
		
		// controller객체가 존재한다면 실행
		// 1. method 판단
		// getMethod() - 해당 요청이 만들어질 때의 method를 string으로 리턴
		String method = request.getMethod();
		String viewName = null;
		switch(method) {
		// 2. method에 따른 doGet, doPost실행
		// 2.1. get -> abstract controller의 doGet호출
		// viewName에 jsp주소에 대한 리턴값을 담아둠
		// 2.2. post -> abstract controller의 doPost호출
		// viewName에 redirect의 location값에 대한 리턴값을 담아둠
		// 2.3. 둘다 해당하지 않을 시 -> methodNotAllowedException
		case "GET" : viewName = controller.doGet(request, response); break;
		case "POST" : viewName = controller.doPost(request, response); break;
		default : throw new MethodNotAllowedException(method);
		}
		
		// 3. jsp forwarding 또는 redirect 처리
		if(viewName != null) {
			// 문자열.startsWith(String prefix) - 해당 문자열이 해당 접두사로 시작하는지 확인해서 boolean형 리턴
			if(viewName.startsWith("redirect:")) {
				// 3.1. post -> redirect
				// location값 구해오기 - contextPath + redirect: 를 제외한 나머지
				// -> redirect: -> ""로 바꾸기 -> redirect: 제거
				// 문자열.replace(CharSequence target, CharSequence replacement)
				// - 해당 문자열에서 첫번째 인자부분을 두번째 인자로 바꿈
				/**
				 * viewName = redirect:/student/insertStudent.do
				 * -> WEB-INF/views/student/insertStudent.do
				 */
				String location = request.getContextPath() + viewName.replace("redirect:", "");
				response.sendRedirect(location);
			} else {
				// 3.1. get -> jsp forwarding
				// 접두어 : /WEB-INF/views/
				// 접미어 : .jsp
				/**
				 * viewName = student/insertStudent
				 * -> /WEB-INF/views/student/insertStudent.jsp
				 */
				final String prefix = "/WEB-INF/views/";
				final String suffix = ".jsp";
				request.getRequestDispatcher(prefix + viewName + suffix)
					   .forward(request, response);
			}
		}
	}

	// doPost로 와도 doGet호출하도록
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
