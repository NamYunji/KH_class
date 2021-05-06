<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>maven - mybatis</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js"></script>
<script>
$(() => {
	$("#btn-gson-test").click((e) => {
		$.ajax({
			url : "${pageContext.request.contextPath}/gson.do",
			method : "GET",
			dataType : "json",
			success : (data) => {
				console.log(data);	
			},
			error : (xhr, statusText, err) => {
				console.log(xhr, statusText, err);
			}
		});
	});
});
</script>
</head>
<body>
	<h1>Hello Maven</h1>
		<button id="btn-gson-test">gson test</button>
	<h1>Hello Mybatis</h1>
	<h2>student</h2>
	<ul>
		<li><a href="${pageContext.request.contextPath}/student/insertStudent.do">/student/insertStudent.do</a></li>
	</ul>
</body>
</html>