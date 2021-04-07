<%@page import="board.model.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
List<Board> list = (List<Board>) request.getAttribute("list");
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판 </h2>
	<!-- 글쓰기 버튼, 로그인시에만 보이도록 -->
	<% if(loginMember != null) { %>
	<input type="button" value="글쓰기" id="btn-add"
			onclick="location.href='<%= request.getContextPath()%>/board/boardForm';"/>
	<% } %>
	<table id="tbl-board">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th><%--첨부파일이 있는 경우 /images/file.png 표시 width:16px --%>
			<th>조회수</th>
		</tr>
		<tbody>
		<% if (list == null || list.isEmpty()) {%>
			<tr>
				<td colspan="6" style="text-align:center;">조회된 게시글이 없습니다.</td>
			</tr>
		<%-- 반복문을 통해 member객체를 하나씩 꺼내서, tr태그에 하나씩 넣기
			 option값들에 대한 null처리 --%>
		<% }
			else { 
				for(Board b : list){
		%>
				<tr>
					<td><%= b.getNo() %></td>
					<td><%= b.getTitle() %></td>
					<td><%= b.getWriter() %></td>
					<td><%= b.getRegDate() %></td>
					<td>
						<% if(b.getAttach() != null){ %>
						<img src="<%= request.getContextPath() %>/images/file.png" width="16px"/>
						<% } %>
					</td>
					<td><%= b.getReadCount() %></td>
				</tr>
		<% 		}
			} %>
		</tbody>
	</table>

	<div id='pageBar'>
		<%= request.getAttribute("pageBar") %>
	</div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
