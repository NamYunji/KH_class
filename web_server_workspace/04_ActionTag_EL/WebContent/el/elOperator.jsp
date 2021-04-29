<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kh.person.model.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String str1 = "안녕";
	String str2 = new String("안녕");

	int big = 100;
	int small = 30;
	
	Person p1 = new Person("honggd", "혼길동", '남', 35, true);
	Person p2 = new Person("honggd", "혼길동", '남', 35, true);
	
	List<String> list = null; // null인 상태
	list = new ArrayList<>(); // null은 아니고 요소가 없는 상태
	list.add("hello world");
	
	// 밑에서 사용하려면 무조건 pageContext속성에 담아야 함
	pageContext.setAttribute("str1", str1);
	pageContext.setAttribute("str2", str2);
	pageContext.setAttribute("big", big);
	pageContext.setAttribute("small", small);
	pageContext.setAttribute("p1", p1);
	pageContext.setAttribute("p2", p2);
	pageContext.setAttribute("list", list);
	
	pageContext.setAttribute("emptyStr", "");	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>El Operator</title>
</head>
<body>
	<h1>El Operator</h1>
	<!-- 주된 목적 : 값을 html로 뿌리기 위함 
		  간단한 연산들은 처리가능-->
	<h2>산술연산</h2>
	<p>${big + small}</p>	<!-- 130 -->
	<p>${big - small}</p>	<!-- 70 -->
	<p>${big - '20'}</p>	<!-- 80 - 산술연산자가 나오면 무조건 숫자로 간주하고 형변환 시도-->
	<%-- <p>${big - "ab"}</p> --%>	<!-- 숫자가 아닌데 형변환하려고 하니까 오류 java.lang.NumberFormatException: For input string: "ab"-->
	<p>${big * small}</p>	<!-- 3000 -->
	<p>${big / small} ${big div small}</p>	<!-- 3.3333333333333335 - 실수와 정수 구분 없이 무조건 실수로 처리 -->
	<p>${big % small} ${big mod small}</p>	<!-- 10 -->
	
	<%-- <p>${str1 + str2}</p> --%>	<!-- java.lang.NumberFormatException: For input string: "안녕" -->
	<p>${str1}${str2} ${str1.concat(str2)}</p> <!-- 안녕안녕 안녕안녕 -->
	
	<h2>비교연산 : 좌항기준</h2>
	<p>${big > small} ${big gt small}</p>	<!-- true true -->
	<p>${big < small} ${big lt small}</p>	<!-- false false -->
	<p>${big >= small} ${big ge small}</p>	<!-- true true -->
	<p>${big <= small} ${big le small}</p>	<!-- false false -->
	<p>${big == small} ${big eq small}</p>	<!-- false false -->
	<p>${big != small} ${big ne small}</p>	<!-- true true -->
	
	<!-- 문자열 비교연산 -->
	<hr />
	<p><%= str1 == str2 %> ${str1 == str2} ${str1 eq str2 }</p> <!-- false true true -->
	<!-- 스크립틀릿의 동등비교연산 - 자바의 동등비교연산 - 주소값 지교 -> false
		 el의 동등비교연산은 내부적으로 equals 사용 -->
	<p><%= str1 != str2 %> ${str1 != str2} ${str1 ne str2 }</p> <!-- true false false -->
	
	<p>${p1 == p2} ${p1 eq p2}</p> <!-- [오버라이드 전] false false -> [오버라이드 후] true true -->
	<!-- 필드의 내용이 같으면 같은 객체로 취급하고 싶다면? equals와 hash코드 오버라이드 -->
	
	<!-- 객체가 null이거나 요소가 없는지의 여부 -->
	<p>${empty list} ${not empty list}</p>	
	<!-- list --- [null]true -> [요소가 없음]true -> [요소추가]false-->
	<p>${empty emptyStr} ${not empty emptyStr}</p>	
	<!-- 문자열 --- true (null은 아니나 문자열이 비어있으므로 -->
	<p></p>
</body>
</html>