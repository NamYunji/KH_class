<%@page import="board.model.vo.BoardComment"%>
<%@page import="java.util.List"%>
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
	List<BoardComment> commentList = (List<BoardComment>) request.getAttribute("commentList");
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
				<a href="/*/board/fileDownload?no=<%= board.getNo()%>"><%= board.getAttach().getOriginalFileName() %></a>
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
		<!--table#tbl-comment -->
		<% if(commentList != null && !commentList.isEmpty()){ %>	
		<table id="tbl-comment">
		<% for(BoardComment bc : commentList){ 
			boolean removable =
					loginMember != null &&
					(
				 		loginMember.getMemberId().equals(bc.getWriter())
				 		|| MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())
					);
			if(bc.getCommentLevel() == 1){
		%>	
			<!-- 댓글 : commentLevel = 1 -->
			<tr class="level1">
				<td>
					<!-- sub태그 : 글씨를 첨자처럼 작게 나타내주는 태그 -->
					<sub class="comment-writer"><%= bc.getWriter() %></sub>
					<sub class="comment-date"><%= bc.getRegDate() %></sub>
					<br />
					<%= bc.getContent() %>
				</td>
				<td>
					<!-- 답글 버튼 
						 value속성으로는 몇번 댓글에 대한 답글인지 - 댓글 번호-->
					<button class="btn-reply" value="<%= bc.getNo() %>">답글</button>
					<% if(removable) { %>
					<button class="btn-delete" value="<%= bc.getNo() %>">삭제</button>
					<% } %>
				</td>
			</tr>	
		<% 	} 
			else {%>
				<!-- 대댓글-->
				<tr class="level2">
				<td>
					<!-- sub태그 : 글씨를 첨자처럼 작게 나타내주는 태그 -->
					<sub class="comment-writer"><%= bc.getWriter() %></sub>
					<sub class="comment-date"><%= bc.getRegDate() %></sub>
					<br />
					<%= bc.getContent() %>
				</td>
				<!-- 대댓글에는 더이상 답글 버튼을 제공하지 않음 -->
				<td>
					<% if(removable) { %>
					<button class="btn-delete" value="<%= bc.getNo() %>">삭제</button>
					<% } %>
				</td>
			</tr>	
		<% 		}
			} %>
		</table>
		<% } %>			
	</div>
</section>
<% if(editable) { %>
<!-- 게시글 삭제하기를 처리할 숨겨진 폼 -->
<form
	action="<%= request.getContextPath() %>/board/boardDelete" 
	name="boardDelFrm"
	method="POST">
<input type="hidden" name="no" value="<%= board.getNo() %>" />
</form>
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
<% } %>
<!-- 댓글,답글 삭제하기를 처리할 숨겨진 폼 -->
<form
	action="<%= request.getContextPath()%>/board/boardCommentDelete"
	name="boardCommentDelFrm"
	method="POST">
<!-- name값으로 boardComment의 no -->
<input type="hidden" name="no" />
<!-- 삭제 후 현재페이지로 돌아옴 - 돌아올 게시글 번호도 필요함
	 무엇을 삭제하든 게시물번호는 동일하므로 value로 고정해줌 -->
<input type="hidden" name="boardNo" value="<%= board.getNo() %>" />
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
/* 버튼을 먼저 누를경우, 유효성 검사 */
// document객체(상위)에서 submit이벤트에서 핸들링하는데, boardCommentFrm이 요청했을 때
// form위의 태그들, 그 위의 태그들까지 모두 document객체에 의해 관리됨, 최상위부모태그인 document객체에 적용
// document객체가 아니더라도 두개의 form을 아우를 수 있는 부모태그면 ok
// name=boardCommentFrm인 이벤트타겟에 submit 이벤트가 document에 전달이 되면 해당 function을 실행해라
$(document).on('submit', '[name=boardCommentFrm]', function(e){
// $(document.boardCommentFrm).submit(function(){
	/* 로그인 여부 검사 */
	<% if(loginMember == null){ %>
	/* 로그인을 하지 않았다면, loginAlert()함수 호출 */
	loginAlert();
	/* 제출되지 않도록 처리 */
	return false;
	<% } %>
	// 댓글내용
	// 두번째 인자로 context 지정 (이 하위에서 이 선택자를 찾음)
	var $content = $("[name=content]", e.target);
	/* 아무문자나 개행문자까지도 하나라도 있는지 검사 */
	if(/^(.|\n)+$/.test($content.val()) == false){
		/* 알림창 띄우기 */
		alert("댓글내용을 작성하세요.");
		/* content부분에 커서 포커스 처리 */
		$content.focus();
		/* 제출되지 않도록 처리 */
		return false;
	}
});
function loginAlert(){
	/* 경고창을 띄우고 */
	alert("로그인 이후 이용할 수 있습니다.");
	/* memberId 입력부분을 커서포커스해줌 -> 로그인 유도 */
	$("#memberId").focus();
}
/* 답글 버튼 클릭시 폼 제공 */
$(".btn-reply").click(function(){
	// 로그인 안했을 경우, 대댓글 못쓰도록 경고창 띄우고 밑에것이 처리안되도록 return처리
	<% if(loginMember == null){ %>
		loginAlert();
		return;
	<% } %>
	// 대댓글 작성폼 동적으로 생성
	// 바깥과 안쪽의 쌍따음표|홑따음표가 겹치지 않도록 주의
	var html = "<tr>";
	html += "<td  colspan = '2' style='display : none; text-align : left'>";
	// form 시작
    html += '<form action="<%=request.getContextPath()%>/board/boardCommentInsert" method="post" name="boardCommentFrm">';
    html += '<input type="hidden" name="boardNo" value="<%= board.getNo() %>" />';
    html += '<input type="hidden" name="writer" value="<%= loginMember != null ? loginMember.getMemberId() : ""%>"/>';
    html += '<input type="hidden" name="commentLevel" value="2" />'; // commentLevel은 2로 설정
    html += '<input type="hidden" name="commentRef" value="' + $(this).val() + '" />';  // 대댓글 -> commentRef값이 있음
    html += '<textarea name="content" cols="60" rows="2"></textarea>';
    html += '<button type="submit" class="btn-insert-reply">등록</button>'; // 여러개일수 있으므로 id값이 아닌 class값으로 처리
	html += '</form>';
	html += "</td";
	html += "</tr>";
	
	// 버튼의 tr태그 찾기
	var $trOfBtn = $(this).parent().parent(); // 버튼의 parent(td), 그 td의 parent(tr)
	// 위에서 만든 html을 jQuery객체로 만든 후 insertAfter
	$(html)
		.insertAfter($trOfBtn) // 버튼 태그가 속한 tr태그 다음요소로 html을 추가함
		.children("td") // tr태그의 자식태그(td)를 찾아서
		.slideDown(800); // slideDown효과를 주기
		
	// 버튼은 1회용으로 처리
	// 한번만 버튼이 생성되고 그 후로는 답글 버튼을 눌러도 답글폼이 만들어지지 않도록
	$(this).off("click"); // click 이벤트 핸들러를 막기
});
/* 삭제버튼 클릭시 이벤트 핸들러 */
$(".btn-delete").click(function(){
	if(confirm("해당 댓글을 삭제하시겠습니까?")){
		var $frm = $(document.boardCommentDelFrm);
		var boardCommentNo = $(this).val(); // 삭제 button의 val를 가져옴
		// 어떤 댓글/답글에 대한 삭제버튼인지 가져와야 함
		// no의 value값을 buttonCommentNo로 세팅
		$frm.find("[name=no]").val(boardCommentNo);
		$frm.submit();
		}
	});
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>