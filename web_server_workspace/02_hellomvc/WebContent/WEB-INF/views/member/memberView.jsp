<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Arrays"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
// header.jsp에 이미 loginMember가 있으니, 그냥 가져다 쓰면 됨
String memberId = loginMember.getMemberId();
/* String password = loginMember.getPassword(); */
String memberName = loginMember.getMemberName();
Date birthday = loginMember.getBirthday();
String email = loginMember.getEmail() != null ? loginMember.getEmail() : "";
String phone = loginMember.getPhone();
String address = loginMember.getAddress() != null ? loginMember.getAddress() : "";
String gender = loginMember.getGender() != null ? loginMember.getGender() : "";
String hobby = loginMember.getHobby(); // 문자열로 되어있음 (등산, 게임...)

List<String> hobbyList = null;
// hobby가 하나도 체크되지 않으면 hobbyList는 null일 수도 있음/
if(hobby != null){
	// 문자열 배열
	String[] arr = hobby.split(",");
	// 문자열 리스트 (Arrays.asList(문자열 배열))
	hobbyList = Arrays.asList(arr); // String[] -> List<String>
	
}
%>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form id="memberUpdateFrm" method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="memberId" id="memberId_" value="<%= memberId %>" readonly>
				</td>
			</tr>
<%-- 			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password_" value="<%= password %>" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="password2" value="<%= password %>" required><br>
				</td>
			</tr>  --%>
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%= memberName %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%= birthday %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%= email %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= phone %>" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address" value="<%= address  %>"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
			       	<input type="radio" name="gender" id="gender0" value="M"
					<%= "M".equals(gender) ? "checked" : ""%>/>
					<label for="gender0">남</label>
					<input type="radio" name="gender" id="gender1" value="F"
					<%= "F".equals(gender) ? "checked" : ""%>/>
					<label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<%-- hobbyChecked(hobbyList, "운동") - 리턴값 : checked | "" --%>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%= hobbyChecked(hobbyList, "운동") %>><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%= hobbyChecked(hobbyList, "등산") %>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%= hobbyChecked(hobbyList, "독서") %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%= hobbyChecked(hobbyList, "게임") %>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%= hobbyChecked(hobbyList, "여행") %>><label for="hobby4">여행</label><br />
				</td>
			</tr>
		</table>
        <input type="button" onclick="updateMember();" value="정보수정"/>
        <input type="button" onclick="updatePassword();" value="비밀번호 변경"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<form name="memberDelFrm" action="<%= request.getContextPath() %>/member/memberDelete" method="POST">
	<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
</form>
<script>
function updatePassword(){
	location.href = "<%= request.getContextPath() %>/member/updatePassword";
}
/* $("#password2").blur(function(){
	var $p1 = $("#password_");
	var $p2 = $("#password2");
	if($p1.val() != $p2.val())
		alert("패스워드가 일치하지 않습니다.");
	$p1.select();
}); */
/**
 * 유효성 검사
 * memberId를 제외하고, 회원가입의 유효성검사와 동일하다.
 */
$("#memberUpdateFrm").submit(function(){
/* 	var $p1 = $("#password_");
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
	}	 */
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
/*
 * 수정, 탈퇴 처리 함수
 */
function updateMember(){
	$("#memberUpdateFrm")
	.attr("action","<%=request.getContextPath() %>/member/memberUpdate")
	.submit();
}
function deleteMember(){
    if(confirm("정말로 탈퇴하시겠습니까?")){
    	$(document.memberDelFrm).submit();
    }
}
</script>
<%!
	// jsp에서만 쓸 수 있는 메소드 선언문
	public String hobbyChecked(List<String> hobbyList, String hobby){
		return hobbyList != null && hobbyList.contains(hobby) ? "checked" : "";
}
%>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
