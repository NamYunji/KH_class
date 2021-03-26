<%-- page directive(지시어) - jsp 문서 설정에 대한 것 
+ import구문도 쓸 수 있음 (또는 import속성만 따로 빼서 쓸 수도 있음)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.io.*"%>
    
<%
	// jsp 스크립팅 요소 : scriptlet
	// 자바공간, 메소드 몸통 안
	// 위치 : 자유로움, 출력식 앞에만 있으면 됨
	
	// 1부터 10까지의 합 구하기
	int sum = 0;
	for(int i = 1; i <= 10; i++) {
		sum += i;
	}
	
	// 현재시각
	Date now = new Date();
	
	
	
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp - basic</title>
<script>
window.onload = function() {
	var sum = 0;
	for(var i = 1; i <= 10; i++)
		sum += i;
	document.querySelector("#sum").innerText = sum;
}
</script>
</head>
<body>
	<h1>Basic</h1>
	<%-- hsp 주석은 java파일 변환시 제거된다. --%>
	<!-- html 주석은 client까지 전달된다.-->
	<%-- jsp 스크립팅 요소 : 출력식
	출력식은 세미콜론을 찍지 않음
	why? 이건 <% out.print(sum); %>과 동일하기 때문
	-> <% %> 이 안에 작성된 내용이 out.print(인자);로 전달됨
	 --%>
	<p>server-side : java로 계산된 결과 : <%= sum %></p>
	<p>server-side : java로 계산된 결과 : <% out.print(sum); %></p>
	<%-- 페이지 소스보기 - 자바코드는 없고 html코드만 있음
		 => 자바로 계산된 결과 = server측에서 처리되었다 --%>
		 
	<p>client-side : javascript로 계산된 결과 : <span id="sum"></span></p>

	<p>server-side : 현재시각 <%= now %>(<%= now.getTime() %>)</p>
	<p>clident-side : 현재시각 <span id="now"></span></p>
	
<script>
	var date = new Date();
	now.innerText = date + "(" + date.getTime() + ")";
</script>
</body>
</html>