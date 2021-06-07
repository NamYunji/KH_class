<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판" name="title"/>
</jsp:include>
<style>
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
tr[data-no]{
	cursor : pointer;
}
</style>
<script>
function goBoardForm(){
	location.href = "${pageContext.request.contextPath}/board/boardForm.do";
}

$(() => {
	$("tr[data-no]").click(e => {
		// 화살표 함수안에서는 this가 이벤트 타겟객체(e.targer)가 아니다.
		// 기본적으로는 윈도를 가리킴
		// e.targer으로 접근해야 하는데, e.target이 tr태그가 아니라 클릭한 td태그를 가리킴
		// 실제 클릭이 일어난 건 td태그, 이벤트가 부모(tr)로 전파된 것! - bubbling
		console.log(e.target);
		// tr에 적혀있는 data-no를 가져오기
		var $tr = $(e.target).parent();
		var no = $tr.data("no");

		location.href = "${pageContext.request.contextPath}/board/boardDetail.do?no=" + no;
	});
});
</script>
<section id="board-container" class="container">
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-outline-success" onclick="goBoardForm();"/>
	<table id="tbl-board" class="table table-striped table-hover">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th> <!-- 첨부파일 있을 경우, /resources/images/file.png 표시 width: 16px-->
			<th>조회수</th>
		</tr>
		<c:forEach items="${list}" var="board">
		<tr data-no="${board.no}">
			<td>${board.no}</td>
			<td>${board.title}</td>
			<td>${board.memberId}</td>
			<td><fmt:formatDate value="${board.regDate}" pattern="yy-MM-dd"/></td>
			<td>
				<c:if test="${board.hasAttachment}">
				<img src="${pageContext.request.contextPath}/resources/images.file.png" width="16px"/>
				</c:if>
			</td>
			<td>${board.readCount}</td>
		</tr>
		</c:forEach>
	</table>
	
	${pageBar}
</section> 

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
