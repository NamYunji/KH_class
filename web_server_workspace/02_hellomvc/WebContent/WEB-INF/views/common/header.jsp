<%@page import="member.model.service.MemberService"%>
<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// 사용자 알림메시지
	// return타입이 object이므로 다운캐스팅 필요
	String msg = (String)session.getAttribute("msg");
	// 한번 읽어내고, 메시지 변수에 담은 후에는 세션에서 바로 지워버리기 -> 일회용 msg만들기
	if(msg != null) session.removeAttribute("msg");
	String loc = (String)request.getAttribute("loc");	
	System.out.println("msg@header.jsp = " + msg);
	Member loginMember = (Member)session.getAttribute("loginMember");
	
	// 사용자 쿠키처리
	String saveId = null;
	// 헤더에서 이 처리 - 모든 jsp에서 header.jsp를 포함 - 모든 요청에서 이걸 검사하겠다는 것
	// 쿠키 가져오기 - getCookies() -> cookie 배열 리턴
	Cookie[] cookies = request.getCookies();
	// 쿠키 열람
	// 처음 접속했을 때는 cookie가 null이기 때문에, null이 아닐 때만 검사
	if(cookies != null){
		for(Cookie c : cookies){
			// key, value형식 (name, value)
			String name = c.getName();
			String value = c.getValue();
			System.out.println(name + " : " + value);
			
			// saveId라는 name값이 있다면 saveId에 value(사용자 아이디)를 저장
			if("saveId".equals(name))
				saveId = value;
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello MVC</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css" />
<%-- jquery 추가
회사마다 프로그램을 가져다쓸 수 있기 때문에
어플리케이션 이름부분을 리터럴로 쓰지 않고 변수처리
request.getContextPath() -> 어플리케이션의 context루트를 가져옴
 --%>
<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.js"></script>
<script>
/* msg를 페이지 실행하자마자 보여줌 */
/* alert(< % = msg % >); */
/* alert(로그인에 성공했습니다.); -> alert("로그인에 성공했습니다.");*/
/* null이 아닌 경우에만 alert하도록 */
<% if(msg != null) { %>
	alert("<%= msg %>"); 
<% } %>

<%-- 로그인 실패시 -> location.href - 페이지 이동명령 --%>
<% if(loc != null) { %>
	location.href = "<%= loc %>";
<% } %>
/* 로그인 폼 유효성 검사 */
$(function(){
	$("#loginFrm").submit(function(){
		/* 선택자가 아닌 태그객체 전달 */
		var $memberId = $(memberId);
		var $password = $(password);
		
		if(/^.{4,}/.test($memberId.val()) == false){
			alert("유효한 아이디를 입력하세요.");
			$memberId.select();
			return false;
		}
		if(/^.{4,}/.test($password.val()) == false){
			alert("유효한 비밀번호를 입력하세요.");
			$password.select();
			return false;
		}
	});
});
</script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>

			<div class="login-container">
			<% if(loginMember == null) {%>
				<!-- 로그인폼 시작 -->
				<!-- 비밀번호가 URL에 드러나지 않도록, POST로 처리 -->
				<form id="loginFrm" action="<%= request.getContextPath() %>/member/login" method="POST">
					<table>
						<tr>
							<td><input type="text" name="memberId" id="memberId"
										placeholder="아이디" tabindex="1"
										value="<%= saveId != null ? saveId : "" %>"></td>
									<%-- 쿠키값이 있다면, saveId 출력, 아니면  빈문자열 --%>
							<td><input type="submit" value="로그인" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password" placeholder="비밀번호" tabindex="2"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><input type="checkbox" name="saveId" id="saveId"
							<%= saveId != null ? "checked" : ""%>/>
							<%-- saveId가 null이 아니면 checked 아니면 빈문자열 --%> 
							<label for="saveId">아이디저장</label>&nbsp;&nbsp;
							<input type="button" value="회원가입" onclick="location.href='<%= request.getContextPath()%>/member/memberEnroll';"></td>
						</tr>
					</table>
				</form>
			<!-- 로그인폼 끝-->
			<% } else { %>
			<%-- 로그인 성공시 --%>
			<table id="login">
				<tr>
					<td><%= loginMember.getMemberName() %>님, 안녕하세요.</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="내정보보기" onclick="location.href='<%= request.getContextPath() %>/member/memberView'"/>
						<%-- 클릭시 로그아웃 페이지로 이동 --%>
						<input type="button" value="로그아웃"
							onclick="location.href='<%= request.getContextPath() %>/member/logout';"/>
					</td>
				</tr>
			</table>
			<% } %>
			</div>
			<!-- 메인메뉴 시작 -->
			<nav>
				<ul class="main-nav">
					<li class="home"><a href="<%=request.getContextPath()%>">Home</a></li>
					<li class="notice"><a href="#">공지사항</a></li>
					<li class="board"><a href="#">게시판</a></li>
					<%-- 관리자 로그인 시에만 관리자메뉴 노출
						 header.jsp는 로그인하지 않고도 볼 수 있기 대문에, loginMember가 null인 경우는 null pointer exception --%>
					<% if(loginMember != null && MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())){ %>
					<li class="members"><a href="<%= request.getContextPath()%>/admin/memberList">회원관리</a></li>
					<% } %>
				</ul>
			</nav>
			<!-- 메인메뉴 끝-->
		</header>
		<section id="content">