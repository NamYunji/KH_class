<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section id=enroll-container>
	<h2>회원 가입 정보 입력</h2>
	<form name="memberEnrollFrm" action="" method="post">
		<table>
			<tr>
				<th>아이디<sup>*</sup></th>
				<td>
					<input type="text" placeholder="4글자이상" name="memberId" id="memberId_" required>
				</td>
			</tr>
			<tr>
				<th>패스워드<sup>*</sup></th>
				<td>
					<input type="password" name="password" id="password_" required><br>
				</td>
			</tr>
			<tr>
				<th>패스워드확인<sup>*</sup></th>
				<td>	
					<input type="password" id="password2" required><br>
				</td>
			</tr>  
			<tr>
				<th>이름<sup>*</sup></th>
				<td>	
				<input type="text"  name="memberName" id="memberName" required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" ><br />
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰<sup>*</sup></th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<input type="radio" name="gender" id="gender0" value="M" checked>
					<label for="gender0">남</label>
					<input type="radio" name="gender" id="gender1" value="F">
					<label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동"><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산"><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서"><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임"><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행"><label for="hobby4">여행</label><br />
				</td>
			</tr>
		</table>
		<input type="submit" value="가입" >
		<input type="reset" value="취소">
	</form>
</section>
<script>
$(document.memberEnrollFrm).submit(function(){
	var $memberId = $("#memberId_");
	if(/^[a-z0-9!@#$$%^&*()]{4,}/g.test($memberId.val()) == false) {
		alert("아이디는 최소 4자리이상이어야 합니다.");	
		$memberId.select();
		return false;
	}
	var $p1 = $("#password_");
	if(/^[a-z0-9!@#$$%^&*()]{4,}/g.test($p1.val()) == false) {
		alert("유효한 패스워드를 입력하세요.");			
		$p1.select();
		return false;
	}
	var $p2 = $("#password2");
	if($p1.val() != $p2.val()) {
		alert("패스워드가 일치하지 않습니다.");			
		$p2.select();
		return false;
	}	
	var $memberName = $("#memberName");
	if(/^[가-힣]{2,}/.test($memberName.val()) == false) {
		alert("이름은 한글 두글자 이상이어야 합니다.");	
		$memberName.select();
		return false;
	}
	var $phone = $("#phone");
	$phone.val($phone.val().replace(/[^0-9]/g, "")); // 숫자아닌 문자(복수개)제거하기
	if(/^010[0-9]{8}$/.test($phone.val()) == false) {
		alert("유효한 휴대폰번호를 입력하세요.");
		$phone.select();
		return false;
	}
	return true;
});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
