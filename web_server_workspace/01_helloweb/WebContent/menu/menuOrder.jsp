<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String main = request.getParameter("main_menu");
String side = request.getParameter("side_menu");
String drink = request.getParameter("drink_menu");

int price = (int)request.getAttribute("price");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 메뉴</title>
<style>
#main {
color : blue;
font-size : 2em;
}

#side {
color : purple;
font-size : 1.5em;
}

#drink {
color : lime;
}

#price {
color : red;
font-size : 2em;
}
</style>
</head>
<body>
<h1>감사합니다.</h1>
<span id="main"><%= main %></span>,
<span id="side"><%= side %></span>,
<span id="drink"><%= drink %></span>를 주문하셨습니다.
<br/> 총 결제금액은
<span id="price"><%= price %>원</span>입니다.
</body>
</html>