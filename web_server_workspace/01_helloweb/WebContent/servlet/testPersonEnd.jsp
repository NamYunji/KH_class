<%-- jsp주석1 --%>
<%-- jsp
	 java + html (자바코드, html코드를 모두 작성 가능)
	 정확히 말하면, html도 다 자바코드
	 jsp의 모든 자바코드 <%...%>는 모두 서버단에서 처리되고, 그 결과만 html에 반영된다. 
	 --%>
	 
<%-- < %@는 jsp의 설정 부분 --%>
<%-- import문 사용법 --%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- html 작성부 이전에 java코드를 씀 --%>
<%-- java코드로 사용자 입력값부분 처리 --%>
<%
	// jsp scriptlet - 자바공간
	// 콘솔에 출력해봄 -> 자바 공간이 맞음을 확인
	System.out.println(123);
	// 사용자 입력값 가져오기
	// servlet에서 jsp에 request, response를 전달해뒀음
	// -> jsp에서 선언없이 request, response에 접근 가능!
	// 똑같은 자바 공간이라는 것을 상기하기! servlet의 method처럼 여기도 그런 공간!
	String name = request.getParameter("name");
	String color = request.getParameter("color");
	String animal = request.getParameter("animal");
	String[] foodArr = request.getParameterValues("food");
	System.out.println("name@jsp = " + name);
	System.out.println("color@jsp = " + color);
	System.out.println("animal@jsp = " + animal);
	// java.lang 패키지 이외의 패키지는 모두 import해줘야 함 
	System.out.println("foodArr@jsp = " + Arrays.toString(foodArr));
	// 저장된 속성 가져오기
	// Object에 담아뒀기 때문에, 꺼내면 바로 object임 -> string변수에 바로 담을 수 없음
	String recommendation = (String)request.getAttribute("recommendation");
	// 잘 넘겨받았는지 확인
	System.out.println("recommendation@jsp = " + recommendation);
%>

<%-- Ctrl+U - 페이지 소스보기 결과를 복붙 --%>
<%-- 이건 단순 html문서가 아닌, 이렇게 html을 만들겠다! 하는 것 --%>
<!DOCTYPE html>
<html>
<head>
<title>개취 검사 결과</title>
<style>
.recommendation { font-size: 2em; color: lime; text-decoration: underline; }
</style>
<body>
<h1>개인 취향 검사 결과 jsp</h1>
<%-- name, color, animal, foodArr - 사용자가 입력한 값 --%>
<%-- <%= 출력식 %> 위의 변수를 그대로 사용 가능
출력식 내에는 세미콜론 찍지 않음  --%>
<p><%= name %>님의 개인 취향 검사 결과는 </p>
<p><%= color %>색을 좋아합니다.</p>
<p>좋아하는 동물은 <%= animal %>입니다.</p>
<%-- 아무것도 선택하지 않았을 경우 (null) - 분기처리 --%>
<p><% if(foodArr != null) { %>좋아하는 음식은 <%= Arrays.toString(foodArr) %>입니다.
<% } else { %>
	좋아하는 음식이 없습니다.
<% } %>
</p>
<%-- server program이 생산한 데이터
	- request.getParameter로 처리 불가
	- forwarding시 jsp에 이 값을 파라미터로 전달할 방법이 없음
 --%>
<hr/>
<%-- 여기도 위에 선언한 자바 변수를 가져다 쓰기 --%>
<p class='recommendation'>오늘은 <%= recommendation %>어떠세요?</p>
</body>
</head>
</html>
