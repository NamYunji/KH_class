<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax - html</title>
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
<style>
	table{
		board-collapse: collapse;
		board:1px solid black;
		margin:5px;
	}
	th, td{
		border:1px solid black;
	}
	table img{
		width:150px;
	}
</style>
</head>
<body>
	<h1>html</h1>
	<input type="button" value="실행" id="btn" />
	<div class="wrapper"></div>
<script>
$(btn).click(function(){
	$.ajax({
		url: "<%= request.getContextPath() %>/html",
		dataType: "html", // dataType이 html이지만 역시 생략 가능!
		success : function(data){
			console.log(data);
			$(".wrapper").html(data);
		},
		error: function(xhr, status, err){
			console.log(xhr, status, err);
		}
	});
});
</script>
</body>
</html>