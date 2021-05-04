<%@page import="java.util.Arrays"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- core를 쓰기 위함 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 다른 것들은 다 출력 용도였다면, jstl을 이용하면 변수 선언 가능. -->
<!-- scope를 이용해서 어느 범위까지만 쓸 수 있는 변수인지 지정 가능, page면 현재페이지에서만 가능 -->
<!-- /jstl/coreBasics.jsp?num1=234&num2=432 -->
<c:set var="no1" value="${param.num1}" scope="page"/>
<c:set var="no2" value="${param.num2}" scope="page"/>
<%-- <% Object no2 = 200;
	pageContext.setAttribute("no2", no2);
	// 이것과 동일함
%> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Core Basics</title>
<style>
table {
	border-collapse : collapse;
	border : 1px solid #000;
	margin : 10px;
}
th, td {
	border : 1px solid #000;
	padding : 5px;
}
</style>
</head>
<body>
	<h1>JSTL</h1>
	<p>Jsp Standard Tag Library</p>
	<%-- 표준이라고는 하지만 표준이 아님. 기본적으로 tomcat에 가지고 있지 않음. jar파일 필요
	진짜 표준인<jsp:include>와 구별하기
	<c:set>
	<c:if>
	<fmt:formatNumber>
	<sql:insert> --%>
	<h1>Core Basics</h1>
	<p><c:out value="${no1}"/> ${no1}</p>	<!-- 100 / c:out하지 않아도 동일한 결과-->
	<p><c:out value="${no2}"/>${no2}</p>	<!-- 200 -->
	<p><c:out value="${no1 + no2}"/>${no1 + no2}</p>	<!-- 300, 문자열 산술연산이 아닌, 숫자 산순연살로 처리됨. 산술연산은 다 숫자로 취급해서 처리하기 때문
	parameter가 없으면, 산술연산에서 null은 0으로 치환 null + null = 0 + 0 -> 0 -->
	
	<h2>조건식</h2>
	<!-- 이 조건식이 true면 태그 안의 내용이 출력됨 -->
	<c:if test="${Integer.parseInt(no1) > Integer.parseInt(no2)}"> 
	<!-- Missing required attribute "test" -->
	${no1} > ${no2}
	</c:if> 
	<!-- false -> 실행되지 않음 -->
	<c:if test="${Integer.parseInt(no1) < Integer.parseInt(no2)}">
	${no1} &lt; ${no2}
	</c:if>
	<c:if test="${no1 eq no2}">
	${no1} eq ${no2}
	</c:if>
	<!-- else if같은건 없음. 다 독립적 if로 처리해야 함 -->
<%-- 	<% if(bool) { %>
	bool이 true면 이걸 출력함
	<% } %> --%>
	<c:set var="rnd" value="<%= new Random().nextInt(100) %>"/>
	<p>
		<!-- 개수 제한없이 when태그 나열 -->
		<!-- 아무것도 해당되지 않을 때 -->
	<c:choose>
		<c:when test="${rnd % 5 == 0}">인형을 뽑았습니다.</c:when>
		<c:when test="${rnd % 5 == 1}">권총을 뽑았습니다.</c:when>
		<c:otherwise>꽝입니다. 다음 기회에...</c:otherwise>
	</c:choose>
	<script>
	console.log(${rnd});
	</script>
	<h2>반복문</h2>
	<!-- step속성의 기본값 : 1 , begin이 무조건 end보다 작아야 함, step은 마이너스일수 없음-->
	<c:forEach var="i" begin="1" end="6" step="1">
	<%-- <c:forEach var="i" begin="6" end="1" step="-1"> --%>
	<%-- javax.servlet.jsp.JspTagException: 'step' <= 0 --%>
	<h${7 - i}>Hello World${7 - i}</h${7 - i}>
	</c:forEach>
	
	<c:set 
		var="list"
		value='<%= Arrays.asList("홍길동", "신사임당", "이순신") %>'/>
	<!-- items : 반복할 객체
		 index는 0부터 시작, count는 1부터 시작-->
	<c:forEach items="${list}" var="name" varStatus="vs">
		<p>${vs.index} ${vs.count} - ${name}</p>
	</c:forEach>
	
	<table>
	<tr>
		<th>No</th>
		<th>아이디</th>
		<th>이름</th>
		<th>성별</th>
		<th>나이</th>
		<th>결혼여부</th>
	</tr>
	<c:forEach items="${personList}" var="person" varStatus="vs">
		<tr>
		<td>${vs.count}</td>
		<td>${person.id}</td>
		<td>${person.name}</td>
		<td>${person.gender}</td>
		<td>${person.age}</td>
		<!-- readonly로 만들기 위해 클릭시  return false -->
		<td><input type="checkbox" ${person.married ? 'checked' : ''} onclick="return false;"/></td>
		</tr>
	</c:forEach>
	</table>
	
	<table>
	<c:forEach items="${map}" var="item">
		<tr>
			<th>${item.key}</th>
			<td>${item.value}</td>
		</tr>
	</c:forEach>
	</table>
	
	<!-- 홍길동, 신사임당, 이순신 -->
	<p>
	<c:forEach items="${list}" var="name" varStatus="vs">
		${name}${vs.count != list.size() ? "," : ""}
		<%-- ${name}${vs.first} --%>
		<%-- ${name}${vs.last} --%>
		<!-- first, last는 boolean형 리턴 -->
		<%-- ${name}${vs.last ? "" ; ","} --%>
	</c:forEach>
	</p>
	<h2>url</h2>
	<img src="${pageContext.request.contextPath}/images/7224f52d52012aff6797dc48358f63c0.jpg" alt="이미지" >
	<img src="<c:url value='/images/7224f52d52012aff6797dc48358f63c0.jpg'/>" alt="이미지">
</body>
</html>