<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시글 작성" name="title"/>
</jsp:include>
<style>
div#board-container{width:400px; margin:0 auto; text-align:center;}
div#board-container input{margin-bottom:15px;}
/* 부트스트랩 : 파일라벨명 정렬*/
div#board-container label.custom-file-label{text-align:left;}

</style>
<script>
/* textarea에도 required속성을 적용가능하지만, 공백이 입력된 경우 대비 유효성검사를 실시함. */
function boardValidate(){
	var $content = $("[name=content]");
	if(/^(.|\n)+$/.test($content.val()) == false){
		alert("내용을 입력하세요");
		return false;
	}
	return true;
}

// 파일 첨부시 선택한 파일명이 반영되도록 설정
$(() => {
	// 파일 선택 또는 취소시
	$("[name=upFile]").change(e => {
		// 1. 파일명 가져오기
		// files속성의 0번지 : 이번에 선택한 파일
		var file = $(e.target).prop('files')[0];
		console.log(file);
		// 파일 선택시 : File {name: "졸업사진.jpg", .....} -> input:file안에 실제 file객체가 존재함
		// 파일 취소시 : undefined
		
		// 2.1. label 가져오기
		var $label = $(e.target).next(); // 형제요소
		
		// 2.2. label에 적용
		// 자동형변환 : 존재하면 true, undefined는 false
		// 파일이 존재하면 file의 name을 존재하지 않으면 "파일을 선택하세요"
		$label.html(file ? file.name : "파일을 선택하세요.");
	});
})
</script>
<div id="board-container">
	<form 
		name="boardFrm" 
		action="${pageContext.request.contextPath}/board/boardEnroll.do" 
		method="post"
		onsubmit="return boardValidate();"
		enctype="multipart/form-data">
		<input type="text" class="form-control" placeholder="제목" name="title" id="title" required>
		<input type="text" class="form-control" name="memberId" value="${loginMember.id}" readonly required>
		<!-- input:file소스 : https://getbootstrap.com/docs/4.1/components/input-group/#custom-file-input -->
		<!-- 첨부파일1 -->
		<div class="input-group mb-3" style="padding:0px;">
		  <div class="input-group-prepend" style="padding:0px;">
		    <span class="input-group-text">첨부파일1</span>
		  </div>
		  <div class="custom-file">
		  	<!-- multiple속성 추가 -->
		    <input type="file" class="custom-file-input" name="upFile" id="upFile1" multiple>
		    <label class="custom-file-label" for="upFile1">파일을 선택하세요</label>
		  </div>
		</div>
		<!-- 첨부파일2 -->
		<div class="input-group mb-3" style="padding:0px;">
		  <div class="input-group-prepend" style="padding:0px;">
		    <span class="input-group-text">첨부파일2</span>
		  </div>
		  <div class="custom-file">
		    <input type="file" class="custom-file-input" name="upFile" id="upFile2" >
		    <label class="custom-file-label" for="upFile2">파일을 선택하세요</label>
		  </div>
		</div>
		
	    <textarea class="form-control" name="content" placeholder="내용" required></textarea>
		<br />
		<input type="submit" class="btn btn-outline-success" value="저장" >
	</form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
