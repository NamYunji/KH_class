package com.kh.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet
 * webService를 위한 java class
 * 일반 자바클래스이나 webService용도로 쓰는 것 뿐
 * 
 * 그러려면 반드시 HttpServlet을 상속해야 함
 * (extends HttpServlet)
 * import javax.servlet.http.HttpServlet;
 * 
 * -> 사용자가 /web/testPerson1.do를 요청했을 때 처리할 수 있는 자바클래스가 된 것!
 *
 * 자바 클래스 자체로는 웹요청을 받아들일 수 없으니,
 * 그것에 필요한 사항들을 HttpServlet에 미리 구현해두고
 * 그걸 상속해서 custom servlet class를 만들어 쓰는 것!
 * 
 * 
 * Servlet 생명주기
 * 일반 클래스에 HttpServlet을 상속하고, doGet()을 오버라이드 하면,
 * 어떻게 일반 클래스가 브라우져에서 보낸 요청을 처리할 수 있는 Servlet 클래스가 될까?
 * 
 * *** Servlet의 특징 : singletone 방식 ***
 * -> Servlet은 WAS구동 내내 딱 하나의 객체만 만들어져서 처리된다.
 * (요청이 들어올 때마다 객체를 만들고, 요청이 끝나면 객체를 반납하게 된다면, 비효율적)
 * 그래서 한번만 servlet을 만들고, 그것을 재사용함
 * 
 * - 최초 client 호출시 1회
 * 1. 객체 생성 (by Servlet 기본 생성자 호출)  
 * 	  but 우리가 하는 게 아닌, tomcat이 해준 것!
 * 2. init() 메소드 호출
 * 
 * - client 매 요청마다 처리
 * 3. HttpServlet(부모클래스)의 service() 메소드 호출
 * 4. 전송방식에 따라 doGet() | doPost() 호출
 * 
 * - 마지막 1회 (tomcat 종료시)
 * 5. destroy 호출 (-> tomcat종료시 객체 반환)
 *	    하나의 요청이 끝나면 반환되는 것이 아닌, tomcat 종료시에 반환되는 것
 *
 *
 * 실제 웹어플리케이션에서는 쓰레딩 처리가 잘 되어있음
 * 여러 건을 동시에 처리하도록 하나의 요청은 하나의 쓰레드로 배정받아서 처리
 * 
 * 하나의 서버에 여러 클라이언트가 동시적으로 요청을 보낸다면
 * 서버가 싱글쓰레드라면 1번 다 처리하고, 2번 시작, 2번 처리하고, 3번 시작...
 * 멀티쓰레드라면 클라이언트 요청마다 응대하는 쓰레드가 하나씩 배정됨
 * -> 기다리지 않고, 요청 보낸 즉시 응답을 받음
 * like 3대의 전화, 3명의 전화상담원
 * 
 * 
 * GET 방식에도 인코딩 선언을 해줘도 상관없음
 * 인코딩 선언을 하든 말든 모두 인코딩이 잘 되어 나옴
 * POST방식에서는 인코딩 선언 필수
 * servlet 안의 코드는 인코딩 처리 빼고는 다 똑같은데,
 * 그렇다면 GET, POST를 섞어 써도 되는가?
 * NO!
 * 
 * 멱등
 * - 서비스 전후로 Database의 상태가 바뀌지 않는 경우
 * - ex. 테이블 query
 * 	 select - 멱등 O, 데이터가 바뀌지 않음 (단순 조회) => GET
 *   insert, update - 멱등 X, 실행(서비스) 전후로 데이터가 바뀜 => POST
 * + 회원가입 - POST
 * - 어떤 요청을 보내느냐에 따라 GET|POST 방식 결정
 * + 로그인 - POST
 *   why? 아이디, 패스워드가 url에 노출되는 것을 방지
 */
public class TestPerson1Servlet extends HttpServlet{
	
	// Servlet 생명주기 알아보기
	// 1. 기본생성자 생성
	public TestPerson1Servlet() {
		super();
		System.out.println("기본생성자 TestPerson1Servlet() 호출!");
	}
	
	// 2. init() 호출
	@Override
	public void init(ServletConfig config) {
		System.out.println("init(ServletConfig) 호출!");
	}
	
	// 3. service() 호출
	// 오버라이드 하지 않고 그대로 쓸 것!
	
	// 5. destroy() 호출
	@Override
	public void destroy() {
		System.out.println("destroy() 호출!");
	}
	
	// [ G E T 방 식 ]
	// 1. doGet() 메소드 오버라이드
	// 1-1. 파라미터 작성
	// cf. 이 파라미터들도 javax.servlet 패키지에 있는 인터페이스들
	// 1-2. 예외던지기 (IOException, ServletException)
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		// 4. 매요청시 사용되는 servlet객체는 동일하다.
		// 매 요청시 servlet객체가 동일한 객체인지 확인
		System.out.println(this.hashCode());
		
		// [http://localhost:9090/web/testPerson1.do?name=홍길동&color=파랑&animal=고양이&food=짜장면&food=짬봉]
		
		// [ 1 . R E Q U E S T - 사용자 입력값 가져오기 ]
		
		// 입력값이 단수개의 경우
		// 1. 사용자 입력값 (form태그내 작성한 내용들) 가져오기
		// -> request.getParameter("html태그의 네임값")
		// 1-1. String 변수에 담기
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		
		// 입력값이 복수개의 경우
		// 1. 사용자 입력값 (form태그내 작성한 내용들) 가져오기
		// -> request.getParameterValues("html태그의 네임값")
		// cf. getParameter()를 쓰면 첫번째 값 하나밖에 못 가져옴
		// 1-1. String 배열에 담기
		// cf. 이 메소드는 String 배열을 리턴함
		String[] foodArr = request.getParameterValues("food");
		
		// 사용자 입력값을 잘 가져왔는지 확인
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("foodArr = " + Arrays.toString(foodArr));
		
		// 이 서블릿이 사용자가 요청한 testPerson1.do와 매칭하도록 등록해야 함!
		// .do라는 파일은 없음. 단지 확장자 형식을 빌려 쓴 것 뿐. 하나의 문자열로 보면 됨
		
		
		// [ 2. R E S P O N S E - 응답메시지 작성 ]
		// html로 응답메시지를 작성함
		
		// 1. html로 작성할 것이라고 응답에 미리 선언함
		// text/html - 나 지금 메시지 쓸건데 text파일이고 html 형식으로 쓸거야!
		// charset=utf-8 - character는 utf-8으로 인코딩해!
		response.setContentType("text/html; charset=utf-8");
		// 2. 출력스트림 PrintWriter 변수에 response.getWriter()담기
		// PrintWriter 타입으로 가져옴
		// -> response.getWriter()
		PrintWriter out = response.getWriter();
		// 3. out.println("html 작성")
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>개취 검사 결과</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>개인 취향 검사 결과 GET</h1>");
		out.println("<p>" + name + "님의 개인 취향 검사 결과는 </p>");
		out.println("<p>" + color + "색을 좋아합니다.</p>");
		out.println("<p>좋아하는 동물은" + animal + "입니다.</p>");
		out.println("<p>좋아하는 음식은" + Arrays.toString(foodArr) + "입니다.</p>");
		out.println("</body>");
		out.println("</html>");
	}
	
	// [ P O S T 방 식 ]
	// 메소드만 다르고, 구조는 get방식과 동일
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		
		// 0. 인코딩 선언
		// request로 꺼내기 전에, 사용자 요청메시지는 utf-8방식으로 인코딩 된거야! 하고 선언
		// http message body부분도 인코딩이 유효하도록 한다.
		request.setCharacterEncoding("utf-8");
		
		// [ 1 . R E Q U E S T - 사용자 입력값 가져오기 ]
		
		// 입력값이 단수개의 경우
		// 1. 사용자 입력값 (form태그내 작성한 내용들) 가져오기
		// -> request.getParameter("html태그의 네임값")
		// 1-1. String 변수에 담기
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		
		// 입력값이 단수개의 경우
		// 1. 사용자 입력값 (form태그내 작성한 내용들) 가져오기
		// -> request.getParameterValues("html태그의 네임값")
		// cf. getParameter()를 쓰면 첫번째 값 하나밖에 못 가져옴
		// 1-1. String 배열에 담기
		// cf. 이 메소드는 String 배열을 리턴함
		String[] foodArr = request.getParameterValues("food");
		
		// 사용자 입력값을 잘 가져왔는지 확인
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("foodArr = " + Arrays.toString(foodArr));
		
		// 이 서블릿이 사용자가 요청한 testPerson1.do와 매칭하도록 등록해야 함!
		// .do라는 파일은 없음. 단지 확장자 형식을 빌려 쓴 것 뿐. 하나의 문자열로 보면 됨
		
		
		// [ 2. R E S P O N S E - 응답메시지 작성 ]
		// html로 응답메시지를 작성함
		
		// 1. html로 작성할 것이라고 응답에 미리 선언함
		// text/html - 나 지금 메시지 쓸건데 text파일이고 html 형식으로 쓸거야!
		// charset=utf-8 - character는 utf-8으로 인코딩해!
		response.setContentType("text/html; charset=utf-8");
		// 2. 출력스트림 PrintWriter 변수에 response.getWriter()담기
		// PrintWriter 타입으로 가져옴
		// -> response.getWriter()
		PrintWriter out = response.getWriter();
		// 3. out.println("html 작성")
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>개취 검사 결과</title>");
		out.println("<body>");
		out.println("<h1>개인 취향 검사 결과 POST</h1>");
		out.println("<p>" + name + "님의 개인 취향 검사 결과는 </p>");
		out.println("<p>" + color + "색을 좋아합니다.</p>");
		out.println("<p>좋아하는 동물은" + animal + "입니다.</p>");
		out.println("<p>좋아하는 음식은" + Arrays.toString(foodArr) + "입니다.</p>");
		out.println("</body>");
		out.println("</head>");
		out.println("</html>");
	}
}
