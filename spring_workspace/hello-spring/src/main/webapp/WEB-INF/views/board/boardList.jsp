<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판" name="title"/>
</jsp:include>
<!-- autocomplete 관련 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
tr[data-no]{
	cursor: pointer;
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

	// keyup이벤트가 일어나면 알아서 autocomplete 함수 호출됨
	// 그때마다 사용자 입력값을 서버로 보내서 그것과 일치하는 게시글들을 가져오기
	$("#searchTitle").autocomplete({
  		source: function(request, response){
 		  //console.log(request);
 		  //console.log(response);
 		  //response([{label:'a', value:'a'}, {label:'b', value:'b'}]);
 		  
 		  //사용자입력값전달 ajax요청 -> success함수안에서 response호출 
  	 	  $.ajax({
			url: "${pageContext.request.contextPath}/board/searchTitle.do",
			data: {
				// searchTitle이라는 key값으로 request.term을 보냄
				searchTitle: request.term
			},
			// success : function(data){
			// 객체 안 메소드의 경우 아래와 같이 사용 가능
			success(data){
				console.log(data);
				const {list} = data;
				// 배열을 하나 새로 만들기
				// map 이용 - 배열의 요소를 가져와서 다른 형식으로 변환 가능
				// board를 가져와서 board에 있는 title을 꺼내서 label과 value 지정
				// 우리는 board no값으로 페이지 이동하므로 no값을 함께 넣어줌
				const arr = 
				list.map(({no, title}) => ({
					// console.log(board);
					label: title,
					value: title,
					no
					}));
				console.log(arr);
				// response에 전달
				response(arr);
			},
			error(xhr, statusText, err){
				console.log(xhr, statusText, err);
			}
  	  	  });
		},
		// 클릭했을때, 해당게시글 상세페이지로 이동
		select: function(event, selected){
			// console.log("select : ", selected);
			// item이라는 속성값으로 아까만든 label과 value가 들어감
			// -> value를 얻고 싶다면 item.value로 찾아야 함
			
			// item속성의 no속성 가져오기
			const {item: {no}} = selected;
			location.href = "${pageContext.request.contextPath}/board/boardDetail.do?no=" + no;
		},
		focus: function(event, focused){
		 return false;
		},
		// autofocus : true -> 처음것이 바로 선택됨
		// minLength : n -> 몇글자를 쳐야 검색이 시작될지
		autoFocus: true, 
		minLength: 2
  });
});
</script>
<section id="board-container" class="container">
	<input type="search" placeholder="제목 검색..." id="searchTitle" class="form-control col-sm-3 d-inline" autofocus/>
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
				<img src="${pageContext.request.contextPath}/resources/images/file.png" width="16px" alt="" />
				</c:if>
			</td>
			<td>${board.readCount}</td>
		</tr>
		</c:forEach>
		
	</table>
	
	${pageBar}
	
</section> 

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>