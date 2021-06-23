<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="회원등록" name="title"/>
</jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member.css" />

<div id="enroll-container" class="mx-auto text-center">
	<form 
		name="memberEnrollFrm" 
		action="${pageContext.request.contextPath}/member/memberEnroll.do" 
		method="post">
		<table class="mx-auto">
			<tr>
				<th>아이디</th>
			    <td>
			        <div id="memberId-container">
			            <input type="text" class="form-control" placeholder="아이디 (4글자이상)" name="id" id="id" required>
			            <span class="guide ok">이 아이디는 사용가능합니다.</span>
			            <span class="guide error">이 아이디는 사용할 수 없습니다.</span>
			            <input type="hidden" id="idValid" value="0"/> <!-- 이걸 보고 memberForm을 submit하는지 마는지의 여부를 결정 -->
			        </div>
			    </td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" class="form-control" name="password" id="password" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" class="form-control" id="passwordCheck" required>
				</td>
			</tr>  
			<tr>
				<th>이름</th>
				<td>	
					<input type="text" class="form-control" name="name" id="name" required>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>		
					<input type="date" class="form-control" name="birthday" id="birthday"/>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" class="form-control" placeholder="abc@xyz.com" name="email" id="email">
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" class="form-control" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" class="form-control" placeholder="" name="address" id="address">
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<div class="form-check form-check-inline">
						<input type="radio" class="form-check-input" name="gender" id="gender0" value="M">
						<label  class="form-check-label" for="gender0">남</label>&nbsp;
						<input type="radio" class="form-check-input" name="gender" id="gender1" value="F">
						<label  class="form-check-label" for="gender1">여</label>
					</div>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<div class="form-check form-check-inline">
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby0" value="운동"><label class="form-check-label" for="hobby0">운동</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby1" value="등산"><label class="form-check-label" for="hobby1">등산</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby2" value="독서"><label class="form-check-label" for="hobby2">독서</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby3" value="게임"><label class="form-check-label" for="hobby3">게임</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby4" value="여행"><label class="form-check-label" for="hobby4">여행</label>&nbsp;
					 </div>
				</td>
			</tr>
		</table>
		<input type="submit" value="가입" >
		<input type="reset" value="취소">
	</form>
</div>
<script>
$("#id").keyup(e => {
	const id = $(e.target).val();
	const $error = $(".guide.error");
	const $ok = $(".guide.ok");
	const $idValid = $("#idValid"); // 0 -> 1 (중복검사 성공시)

	if(id.length < 4) {
		// 4글자 이상을 썼다가 지우는 경우를 대비해서
		$(".guide").hide(); // 있던 guide를 다 감추기
		$idValid.val(0); // 다시 작성하는 경우를 대비, idValid를 다시 0으로 만들기
		return; // 네글자 이상일 때만 검사할 수 있도록 return
	}
	// {id:id} -> {id}로 줄여쓸 수 있음 -> {id : "abcde"}
	$.ajax({
		url : "${pageContext.request.contextPath}/member/checkIdDuplicate3.do",
		data : {id},
		success : data => {
		// success : ({available}) => {
			console.log(data); // {"available" : true} 이런식으로 json으로 넘어올 것
			const {available} = data;
			// 사용가능한 경우
			// if(data.available){
			if(available) {
				$ok.show();
				$error.hide();
				$idValid.val(1);
			}
			// 사용불가한 경우
			else {
				$ok.hide();
				$error.show();
				$idValid.val(0);
			}
		},
		error : (xhr, stautsText, err) => {
			console.log(xhr, statusText, err);
		}
	});
});
	
$("#passwordCheck").blur(function(){
	var $password = $("#password"), $passwordCheck = $("#passwordCheck");
	if($password.val() != $passwordCheck.val()){
		alert("패스워드가 일치하지 않습니다.");
		$password.select();
	}
});
	
$("[name=memberEnrollFrm]").submit(function(){
	var $id = $("#id");
	if(/^\w{4,}$/.test($id.val()) == false) {
		alert("아이디는 최소 4자리이상이어야 합니다.");
		$id.focus();
		return false;
	}

	// 아이디 중복검사 완료전에는 제출되지 않도록
	var $idValid = $("#idValid");
	if($idValid.val() == 0) {
		alert("아이디 중복검사 해주세요.");
		$id.focus();
		return false;
	}
	return true;
});
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

