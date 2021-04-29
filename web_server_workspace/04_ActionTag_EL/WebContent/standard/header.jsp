<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <title>include</title> -->
<!-- 현재 페이지로 전달된 값 중의 title을 가져옴 -->
<title>${param.title}</title>
<style>
header, section, footer {
	border : 1px solid #000;
	margin : 10px 0;
}
section {
	height : 500px;
}
</style>
</head>
<body>
	<header>
		<!-- <h1>include</h1> -->
		<h1>${param.title}</h1>
	</header>
	<section>