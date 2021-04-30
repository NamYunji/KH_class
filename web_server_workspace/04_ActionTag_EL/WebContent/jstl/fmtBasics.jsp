<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ko_kr"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>fmt basics</title>
</head>
<body>
	<h1>fmt basics</h1>
	<!-- 숫자 세자리마다 콤마를 찍거나,
	자리수를 맞춘다는 식의 숫자관련 형식, 또는 연원일 시분초를 어떤 형식으로 보여줄건지에 대한 포매팅 가능 -->
	<h2>숫자</h2>
	<c:set var="num1" value="1234567890"></c:set>
	<p><fmt:formatNumber value="${num1}" pattern="#,###"></fmt:formatNumber></p>
	<!-- 1,234,567,890 -->
	<p><fmt:formatNumber value="${num1}" type="currency"></fmt:formatNumber></p>
	<!-- ￦1,234,567,890 -->
	<c:set var="num2" value="123.456"/>
	<p><fmt:formatNumber value="${num2}" pattern="#.##"/></p>
	<!-- 123.46    -> 반올림해서 소수점 이하 자리를 맞춰줌 -->
	<p><fmt:formatNumber value="${num2}" pattern="#.####"/></p>
	<!-- 123.456 -->
	<p><fmt:formatNumber value="${num2}" pattern="0.0000"/></p>
	<!-- 123.4560  -> 자리수가 남으면 0을 채워줌 -->
	<c:set var="num3" value="0.02"/>
	<p><fmt:formatNumber value="${num3}" type="percent"/></p>
	<!-- 2% -->
	
	<h2>날짜</h2>
	<c:set var="now" value="<%= new Date() %>"/>
	<p><fmt:formatDate value="${now}" type="date"/></p> <!-- 2021. 4. 30 -->
	<p><fmt:formatDate value="${now}" type="time"/></p> <!-- 오후 5:53:44 -->
	<p><fmt:formatDate value="${now}" type="both"/></p> <!-- 2021. 4. 30 오후 5:53:44 -->
	<p><fmt:formatDate value="${now}" pattern="yy/MM/dd(E)"/></p> <!-- 21/04/30(금) -->
	<p><fmt:formatDate value="${now}" pattern="HH:mm:ss"/></p> <!-- 17:55:13 -->
</body>
</html>