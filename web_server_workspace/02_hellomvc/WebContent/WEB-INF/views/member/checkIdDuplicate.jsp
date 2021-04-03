<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String memberId = request.getParameter("memberId");
	boolean available = (boolean)request.getAttribute("available");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디중복검사</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.js"></script>
<style>
div#checkId-container{text-align:center; padding-top:50px;}
span#duplicated{color:red; font-weight:bold;}
</style>
</head>
<body>
	<div id="checkId-container">
	<% if(available) { %>
		<%-- 아이디 사용가능한 경우 --%>
		<p>[<%= memberId %>]는 사용가능합니다.</p>
		<input type="button" value="사용하기" onclick="setMemberId();"/>
	<% } else { %>
		<%-- 아이디 사용불가한 경우 -> 다시한번 입력할 수 있는 폼 제시--%>
		<p>[<span id="duplicated"><%= memberId %></span>]는 이미 사용중입니다.</p>
		<form name="checkIdDuplicateFrm">
			<input type="text" name="memberId" placeholder="아이디를 입력하세요."/>
			<input type="button" value="중복검사" onclick="checkIdDuplicate();"/>
		</form>
	<% } %>
	</div>
<script>
/**
 * 사용가능한 아이디를 찾은 경우
 * 1. 아이디를 부모윈도우의 #memberId_에 대입
 * 2. #idValid의 값을 1로 변경
 */
function setMemberId() {
	// 부모윈도우 opener 키워드로 접근
	// $frm - memberEnroll.jsp(부모윈도우)의 memberEnrollFrm
	// opener는 window객체
	var $frm = $(opener.document.memberEnrollFrm);
	// 1. memberId_의 value값에 현재 ok받은 memberId값으로 대입
	$frm.find("#memberId_").val('<%= memberId%>');
	// 2. idValid의 값을 1로 바꿔줌
	$frm.find("#idValid").val(1);
	// 3. 창닫기
	self.close();
}
/**
 * 아이디 중복검사 함수
 */
function checkIdDuplicate(){
	var $memberId = $("[name=memberId]");
	if(/^[a-z0-9_]{4,}/g.test($memberId.val()) == false) {
		alert("유효한 아이디를 입력해주세요.");
		$memberId.select();
		return;
	}
	
	// 폼제출 
	$frm = $(document.checkIdDuplicateFrm); // cf. name값과 id값은 직접적으로 호출 가능
	// $(selector).attr(attribute,value)
	$frm.attr("action", "<%= request.getContextPath() %>/member/checkIdDuplicate")
		.attr("method", "POST")
		.submit();
}
</script>
</body>
</html>
