<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String life = "life is very short!";
	String movie = "노인을 위한 나라는 없다.";
	pageContext.setAttribute("life", life);
	pageContext.setAttribute("movie", movie);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>El Basics</title>
</head>
<body>
	<h1>El Basics</h1>
	<%-- scope 생략시, pageScope - requestScope - sessionScope - applicationScope --%>
	<!-- request.getAttribute하지 않아도 값을 가져오는 것 -->
	<p>${life}</p>
	<p>${coffee}</p>
	<p>${serverTime}</p>
	<p>${honngd}</p>
	<p>${honngd.id}</p>
	<p>${honngd.name}</p>
	<p>${honngd.gender}</p>
	<p>${honngd.age}</p>
	<p>${honngd.married}</p>
	<p>${book}</p>
	<p>${movie}</p>
	<p>${applicationScope.movie}</p>
	<!-- EL은 NullPointException을 유발하지 않는다.
		 null인 경우에는 ""출력 -->
	<p>[${cow.run}]</p>
	<h2>list</h2>
	<p>${list}</p>
	<!-- index로 접근도 가능 -->
	<p>${list[0]}</p>
	<p>${list[1]}</p>
	<p>${list[2]}</p>
	<!-- 존재하지 않는 인덱스여도 역시 오류나지 않음 -->
	<p>[${list[3]}]</p>
	<h2>map</h2>
	<p>${map}</p>
	<p>${map.language}</p>
	<!-- 난해한 속성 접근은 중괄호 이용, 문자열로 요청 -->
	<p>[${map.Dr.zang}]</p>
	<p>${map['Dr.zang']}</p>
	<p>${map['Dr.zang'].name}</p>
	<p>${map['Dr.zang']["name"]}</p>
	<h1>Param</h1>
	<!-- http://localhost:9090/action/el/elBasics.do?pname=%EC%95%84%EC%9D%B4%ED%8F%B0&pcount=10&option=black&option=128gb 
		 사용자 입력값 처리-->
	<!-- param.네임값 -->
	<p>${param.pname}</p>	<!-- 아이폰 -->
	<p>${param.pcount}</p>	<!-- 10-->
	<!-- 복수개 값은 paramValues로 처리 -->	
	<p>${paramValues.option[0]}</p> <!-- black -->	
	<p>${paramValues.option[1]}</p>		<!-- 239gb-->
	
	<h1>cookie</h1>
	<p>${cookie.JSESSIONID}</p>		<!-- 쿠키객체 자체 리턴   javax.servlet.http.Cookie@4b108782-->
	<p>${cookie.JSESSIONID.value}</p>	<!-- 쿠키 객체의 값  158CEBEF0F7F9F6DE811F99BA1105CD4 -->
	
	<h1>header</h1>
	<!-- 개발도구 - Request Headers에 있는 걸 jsp에서 꺼내쓸 수 있음 
	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
	Accept-Encoding: gzip, deflate, br
	User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36
	Referer: http://localhost:9090/action/
	... -->
	<p>${header.accept}</p>
	<p>${header["User-Agent"]}</p>
	
	<!-- 여기까지는 다 map임. 별도의 map을 구성해둔 것에서 가져온 것. -->
	
	<h1>pageContext</h1>
	<!-- 유일하게 map이 아님 
	bean은 was가 관리하는 객체 (그냥 자바객체라고 이해하면 됨). 참조형 객체
	getPage()
	getRequest()
		getMethod() : GET|POST
		getContextPath() : /action
	getResponse()
	getSession()
	getServletContext()
	getErrorData()
	이걸 통해서 위의 객체들에 실제로 접근 가능
	앞에서는 단순 속성만 가진 map객체.
	이건 실제 request, response객체에 접근 가능	 -->
	<!-- 무슨 전송방식으로 요청되었는지  알려면 getRequest()에 getMethod()
		get은 다 어디감? ogml get을 제외하고 나머지 이름을 property로 사용한다.-->
	<p>${pageContext.request.method}</p>	<!-- GET -->
	<p>${pageContext.request.contextPath}</p>	<!-- /action -->
	<br /><br /><br /><br /><br />
</body>
</html>