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
	<!-- 댓글 폼 -->
	<hr style="margin-top:30px;" />	
	<div class="comment-container">
        <div class="comment-editor">
            <form action="<%=request.getContextPath()%>/board/boardCommentInsert" method="post" name="boardCommentFrm">
                <input type="hidden" name="boardNo" value="<%= board.getNo() %>" />
                <input type="hidden" name="writer" value="<%= loginMember != null ? loginMember.getMemberId() : ""%>" />
                <!-- boardView페이지는 로그인하지 않고도 볼 수 있음
                	  로그인 안하고 loginMember.getMemberId하면 null point exception
                	 우선 로그인하지 않은 경우는 공란으로 메꿔줌-->
                <input type="hidden" name="commentLevel" value="1" />
                <!-- 댓글이니까 commentLevel은 1로 고정 -->
                <input type="hidden" name="commentRef" value="0" />    
                <!-- commentRef는 대댓글에만 해당 -> 0으로 세팅 -->
				<textarea name="content" cols="60" rows="3"></textarea>
                <button type="submit" id="btn-insert">등록</button>
            </form>
        </div>
		<!--table#tbl-comment-->
	</div>
</section>
<% if(editable) { %>
<!-- 삭제하기를 처리할 숨겨진 폼 -->
<form
	action="<%= request.getContextPath()%>/board/boardDelete"
	name="boardDelFrm"
	method="POST">
<input type="hidden" name="no" value="<%= board.getNo() %>" />
</form>
<!-- 댓글 작성시 로그인 알림용 스크립트 -->
<script>
/* content에 포커스가 갈때 함수 실행 */
$("[name=content]").focus(function(){
	/* 로그인 여부 검사 */
	<% if(loginMember == null){ %>
	/* 로그인을 하지 않았다면, loginAlert()함수 호출 */
	loginAlert();
	<% } %>
});
function loginAlert(){
	/* 경고창을 띄우고 */
	alert("로그인 이후 이용할 수 있습니다.");
	/* memberId 입력부분을 포커스해줌 -> 로그인 유도 */
	$("#memberId").focus();
}
</script>
<% } %>
<!-- 게시글 수정에 대한 스크립트 -->
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
<%@ include file="/WEB-INF/views/common/footer.jsp" %>