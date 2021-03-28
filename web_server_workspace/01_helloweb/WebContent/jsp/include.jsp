<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- include 지시어 사용
	 file="jsp의 주소"
	  이 jsp를 가져와주세요! 하는 것
 --%>
<%@ include file="/jsp/header.jsp" %>s
		<h1>Content1</h1>
		<p><%= name %>님, 반갑습니다.</p>
		<a href="/web/jsp/another.jsp">another.jsp</a>
		<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
		   Neque quae doloribus earum optio minus ad voluptatum totam
		   quis accusantium aliquid odio tenetur quos esse aut numquam
		   error explicabo maiores suscipit.</p>
		   
		<script>
		$(function(){
		$("h1").css("color", "deeppink");			
		});
		</script>
<%@ include file="/jsp/footer.jsp" %>
 s
 
 
 
 s