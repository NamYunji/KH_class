<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Dev 목록" name="title"/>
</jsp:include>
<table class="table w-75 mx-auto">
    <tr>
      <th scope="col">번호</th>
      <th scope="col">이름</th>
      <th scope="col">경력</th3>
      <th scope="col">이메일</th>
      <th scope="col">성별</th>
      <th scope="col">개발가능언어</th>
      <th scope="col">수정 | 삭제</th>
    </tr>
    <c:forEach items="${list}" var="dev" varStatus="vs">
    <tr>
    	<td scope="row">${vs.count }</td>
    	<td>${dev.name}</td>
    	<td>${dev.career}년</td>
    	<td>${dev.email}</td>
    	<td>${dev.gender}</td>
    	<td>
	    	<c:forEach items="${dev.lang}" var="lang" varStatus="vs">
	    		${lang}${vs.last? "" : ","}
	    	</c:forEach>
	    </td>
	    <td>
	    	<button class="btn btn-outline-secondary" onclick="updateDev(this);" data-no="${dev.no}">수정</button>
	    	<button class="btn btn-outline-danger" onclick="deleteDev(this);" data-no="${dev.no}">삭제</button>
	    </td>
    </tr>
    </c:forEach>
</table>
<!-- 삭제하기 처리를 처리하는 form -->
<form
	name="devDelFrm"
	action="${pageContext.request.contextPath}/demo/deleteDev.do"
	method="POST">
	<input type="hidden" name="no" value=""/>
</form>
<script>
function updateDev(btn){
	// GET방식으로 /demo/updateDev?no=123 ---> devUpdateForm.jsp
	// POST방식으로 /demo/updateDev.do ---> redirect:/demo/devList.do
	
	// 수정처리해야 할 dev의 no가져오기
	var no = $(btn).data("no");
	console.log(btn, no);
	// \${no} - ${no}는 el이 아니므로 
	// 서버단이 아닌 브라우져에 와서 실행하라는 의미로 \를 붙임 -> 문자그대로의 ${no}입니다!
	// no값을 가지고 updateDev로 url이동
	location.href = `${pageContext.request.contextPath}/demo/updateDev.do?no=\${no}`;
}
function deleteDev(btn){
	// POST방식으로 /demo/deleteDev.do ---> redirect:/demo/devList.do
	// 별도의 form을 만들어서 처리
	var no = $(btn).data("no");
	if(confirm(no + "번 개발자 정보를 정말 삭제하시겠습니까?")){
		var $frm = $(document.devDelFrm);
		$frm.find("[name=no]").val(no);
		$frm.submit();
	}
}
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
