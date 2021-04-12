<%@page import="board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>    
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<%
	// board를 넘겨받아서 board객체에 담기
	Board board = (Board)request.getAttribute("board");
%>
<section id="board-container">
<h2>게시판 수정</h2>
<form
	name="boardUpdateFrm"
	action="<%=request.getContextPath() %>/board/boardUpdate"
	method="post"
	enctype="multipart/form-data">
	<!-- pk번호가 있어야 update문에서 몇번을 찾아서 수정하라는 식으로 전개됨 -->
	<!-- 게시글 번호를 노출할 필요가 없으니 hidden으로 작성 -->
	<input type="hidden" name="no" value="<%= board.getNo() %>" />
	<table id="tbl-board-view">
	<tr>
		<th>제 목</th>
		<td><input type="text" name="title" value="<%= board.getTitle() %>" required></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>
			<input type="text" name="writer" value="<%= board.getWriter() %>" readonly/>
		</td>
	</tr>
	<tr>
		<!-- input:file의 value속성은  보안성을 이유로 임의변경할 수 없다.
			 input:file태그가 정한 방법대로 파일을 선택해야만 한다. -->
		<th>첨부파일</th>
		<td >
			<input type="file" name="upFile">
			<% if(board.getAttach() != null) {%>
			<!-- 원래 파일명을 보여주는 차선책 -->
			<p style="margin:5px 0;">
				<img src="<%= request.getContextPath() %>/images/file.png" width="16px"/>
				<%= board.getAttach().getOriginalFileName() %>
				<!-- 삭제 체크박스
					체크했다면 value로 attachment의 고유번호 -->
				<input type="checkbox" name="delFile" id="delFile" value="<%= board.getAttach().getNo()%>"/>
				<label for="delFile">삭제</label>
			</p>
			<% } %>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<!-- textarea는 value속성이 아니라 시작태그와 종료태그 사이에 작성. -->
		<td><textarea rows="5" cols="50" name="content"><%= board.getContent() %></textarea></td>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="수정하기"/>
			<input type="button" value="취소" onclick="history.go(-1);"/>
		</th>
	</tr>
</table>
</form>
</section>
<script>
/* 파일 선택 변경시 나타나는 이벤트 */
$("[name=upFile]").change(function(){
	console.log($(this).val()); // 파일 선택 - 파일명, 선택 x - 빈문자열
	if($(this).val() != ""){
		// 파일 선택 -> 체크
		$('#delFile').prop("checked", true);
	} else {
		// 파일 선택 취소 -> 체크 해제
		$('#delFile').prop("checked", false);
	}
});
$(document.boardUpdateFrm).submit(function (){
	var $content = $("[name=content]");
	if(/^(.|\n)+$/.test($content.val()) == false){
		alert("내용을 입력하세요");
		return false;
	}
	return true;
});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
