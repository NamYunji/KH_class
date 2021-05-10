<%@page import="java.util.List"%>
<%@page import="com.kh.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 전체 html이 필요한 게 아니라, 멤버 데이터가 들어갈 table html만 있으면 됨 -->
<%
	// list 꺼내오기
	List<Member> list = (List<Member>) request.getAttribute("list");
%>
<table>
<% for(Member member : list){ %>
	<tr>
		<td><img src="<%= request.getContextPath() %>/images/<%= member.getProfile() %>" alt="<%= member.getName() %>" /></td>
		<td><%= member.getId() %></td>
		<td><%= member.getName()%></td>
	</tr>
<% } %>
</table>