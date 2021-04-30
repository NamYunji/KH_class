<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ActionTag | EL</title>
<style>
ul {
	list-style : none;
	padding-left : 20px;
	/* ul태그 기본적 padding 옵션은 40px */
}
</style>
</head>
<body>
	<h1>ActionTag | EL</h1>
	<h2>standard action</h2> <!-- jsp로 시작하는 태그들 -->
	<ul>
		<!-- 그동안 contextPath를 가져오던 방식 - request.getContextPath()
		<li><a href="< %= request.getContextPath() %>"></a></li> -->
		<!-- el문법의 contextpath를 반환하는 방식 -->
		<li><a href="${pageContext.request.contextPath}/standard/useBean.do">useBean</a></li>
		<!-- <a href="/action/standard/useBean.do">useBean</a> - 이렇게 바뀌어 있음-->
		<!-- .do는 그냥 확장자 형식을 딴것. 아무의미가 없지만 .do해서 마치 파일의 확장자처럼 사용 -->
		<!-- include 태그 (scriptlet의 include와 다름) -->
		<li><a href="${pageContext.request.contextPath}/standard/include.jsp">include</a></li>
	</ul>
	
	<h2>el</h2>
		<!-- jstl 사용시 el과 함께 쓰기 전에 el을 먼저 배움 -->
		<ul>
		<!-- .do의 의미 : 서블릿으로 요청하겠다는 의미 -->
		<!-- param -> el에서 접근가능 -->
			<li><a href="${pageContext.request.contextPath}/el/elBasics.do?pname=아이폰&pcount=10&option=black&option=128gb">el Basics</a></li>
			<li><a href="${pageContext.request.contextPath}/el/elOperator.jsp">el Operator</a></li>
		</ul>
		
	<h2>jstl</h2>
		<ul>
			<li><a href="${pageContext.request.contextPath}/jstl/coreBasics.do?num1=234&num2=432">core basics</a></li>
			<li><a href="${pageContext.request.contextPath}/jstl/fmtBasics.jsp">fmt basics</a></li>
			<li><a href="${pageContext.request.contextPath}/jstl/fnBasics.jsp">fn basics</a></li>
		</ul>
</body>
</html>