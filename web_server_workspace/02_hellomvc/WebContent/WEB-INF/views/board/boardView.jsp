<%@page import="board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
Board board = (Board)request.getAttribute("board");
boolean editable =
		loginMember != null &&
		(
		 	loginMember.getMemberId().equals(board.getWriter())
		 	|| MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())
		);
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판</h2>
	<table id="tbl-board-view">
		<tr>
			<th>글번호</th>
			<td><%= board.getNo() %></td>
		</tr>
		<tr>
			<th>제 목</th>
			<td><%= board.getTitle() %></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%= board.getWriter() %></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%= board.getReadCount() %></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<% if(board.getAttach() != null){ %>
				<%-- 첨부파일이 있을경우만, 이미지와 함께 original파일명 표시 --%>
				<img alt="첨부파일" src="<%=request.getContextPath() %>/images/file.png" width=16px>
				<%-- board no로 요청 - db조회--%>
				<a href="<%= request.getContextPath()%>/board/fileDownload?no=<%= board.getNo()%>"><%= board.getAttach().getOriginalFileName() %></a>
				<% } %>
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><%= board.getContent() %></td>
		</tr>
		<!-- 작성자 또는 관리자일 경우에만 노출-->
		<% if(editable) { %>
		<tr>
			<%-- 작성자와 관리자만 마지막행 수정/삭제버튼이 보일수 있게 할 것 --%>
			<th colspan="2">
				<input type="button" value="수정하기" onclick="updateBoard()">
				<input type="button" value="삭제하기" onclick="deleteBoard()">
			</th>
		</tr>
		<% } %>
	</table>
</section>
<% if(editable) { %>
<!-- 삭제하기를 처리할 숨겨진 폼 -->
<form
	action="<%= request.getContextPath()%>/board/boardDelete"
	name="boardDelFrm"
	method="POST">
<input type="hidden" name="no" value="<%= board.getNo() %>" />
</form>
<script>
function deleteBoard(){
/* 삭제의 경우 확인작업 필요*/
	if(confirm("게시글을 정말 삭제하시겠습니까?")){
		$(document.boardDelFrm).submit();
	}
}
/* 업데이트 폼을 요청 - 페이지 이동 */
function updateBoard(){
	location.href = "<%= request.getContextPath() %>/board/boardUpdate?no=<%= board.getNo()%>";
}
</script>
<% } %>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>