<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	List<Member> list = (List<Member>) request.getAttribute("list");
	// 사용자가 입력한 searchType과 searchKeyword를 꺼내오기
	String type = request.getParameter("searchType");
	String kw = request.getParameter("searchKeyword"); 
%>
<!-- 관리자용 admin.css link -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" />
<style>
div#search-container {margin:0 0 10px 0; padding:3px; background-color: rgba(0, 188, 212, 0.3);}
/* memberId를 기본값으로 사용하기 위해 type==null일 경우도 추가 */
div#search-memberId {display: <%= type == null || "memberId".equals(type) ? "inline-block" : "none" %>;}
div#search-memberName{display: <%= "memberName".equals(type) ? "inline-block" : "none" %>;}
div#search-gender{display: <%= "gender".equals(type) ? "inline-block" : "none" %>;}
</style>
<section id="memberList-container">
	<h2>회원관리</h2>
<!-- SEARCH BAR -->
	<div id="search-container">
		검색타입 : 
        <select id="searchType">
            <option value="memberId" <%= "memberId".equals(type) ? "selected" : "" %>>아이디</option>		
            <option value="memberName" <%= "memberName".equals(type) ? "selected" : "" %>>회원명</option>
            <option value="gender" <%= "gender".equals(type) ? "selected" : "" %>>성별</option>
        </select>
        <div id="search-memberId" class="search-type">
            <form action="<%=request.getContextPath()%>/admin/memberFinder">
                <input type="hidden" name="searchType" value="memberId"/>
                <input type="text" name="searchKeyword"  size="25" value="<%= "memberId".equals(type) ? kw : "" %>" placeholder="검색할 아이디를 입력하세요."/>
                <button type="submit">검색</button>			
            </form>	
        </div>
        <div id="search-memberName" class="search-type">
            <form action="<%=request.getContextPath()%>/admin/memberFinder">
                <input type="hidden" name="searchType" value="memberName"/>
                <input type="text" name="searchKeyword" size="25" value="<%= "memberName".equals(type) ? kw : "" %>" placeholder="검색할 이름을 입력하세요."/>
                <button type="submit">검색</button>			
            </form>	
        </div>
        <div id="search-gender" class="search-type">
            <form action="<%=request.getContextPath()%>/admin/memberFinder">
                <input type="hidden" name="searchType" value="gender"/>
                <input type="radio" name="searchKeyword" value="M" <%= "gender".equals(type) && "M".equals(kw) ? "checked" : "" %>> 남
                <input type="radio" name="searchKeyword" value="F" <%= "gender".equals(type) && "F".equals(kw) ? "checked" : "" %>> 여
                <button type="submit">검색</button>
            </form>
        </div>
    </div>
    <!-- LIST TABLE -->
	<table id="tbl-member">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>회원권한</th>
				<th>성별</th>
				<th>생년월일</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>취미</th>
				<th>가입일</th>
			</tr>
		</thead>
		<tbody>
		<% if (list == null || list.isEmpty()) {%>
			<tr>
				<td colspan="10" style="text-align:center;">조회된 회원이 없습니다.</td>
			</tr>
		<%-- 반복문을 통해 member객체를 하나씩 꺼내서, tr태그에 하나씩 넣기
			 option값들에 대한 null처리 --%>
		<% }
			else { 
				for(Member m : list){
		%>
				<tr>
					<td><%= m.getMemberId() %></td>
					<td><%= m.getMemberName() %></td>
					<td>
						<%-- 모든 select태그를 관리하기 위해 class값 부여 
							 data속성 - 태그에 value를 저장했다가 꺼내쓰기 가능 --%>
						<select class="member-role" data-member-id="<%= m.getMemberId() %>">
							<option
								value="<%= MemberService.ADMIN_ROLE %>"
								<%= MemberService.ADMIN_ROLE.equals(m.getMemberRole()) ? "selected" : "" %>>관리자</option>
							<option 
								value="<%= MemberService.MEMBER_ROLE %>"
								<%= MemberService.MEMBER_ROLE.equals(m.getMemberRole()) ? "selected" : "" %>>일반</option>
						</select>
					</td>
					<td><%= "M".equals(m.getGender()) ? "남" : "여" %></td>
					<td><%= m.getBirthday() != null ? m.getBirthday() : "" %></td>
					<td><%= m.getEmail() != null ? m.getEmail() : ""  %></td>
					<td><%= m.getPhone() %></td>
					<td><%= m.getAddress() != null ? m.getAddress() : "" %></td>
					<td><%= m.getHobby() != null ? m.getHobby() : "" %></td>
					<td><%= m.getEnrollDate() %></td>
				</tr>
		<% 		}
			} %>
		</tbody>
	</table>
	 <!-- PAGE BAR -->
	<div id="pageBar">
		<%= request.getAttribute("pageBar") %>
	</div>
</section>
<!--  memberRole 변경 - 업데이트니까 폼을 만들어야 함 -->
<form action="<%= request.getContextPath() %>/admin/memberRoleUpdate"
	  name="memberRoleUpdateFrm"
	  method="POST" >
	  <!-- memberId와 memberRole을 전송 -->
	  <input type="hidden" name="memberId" />
	  <input type="hidden" name="memberRole" />
</form>
<script>
/* LIST TABLE */
/* memberId의 memberRole을 뭐로 바꿀지 */
$(".member-role").on("change", function(){
	/* data속성으로 memberId를 가져옴 */
	var memberId = $(this).data("memberId");
	var memberRole = $(this).val();
	// alert(memberId + " : " + memberRole);
	// confirm을 받아서 true일 때만 처리
	var memberRoleStr = 
		memberRole == "<%= MemberService.ADMIN_ROLE%>" ?
				"관리자" : "일반";
	if(confirm("[" + memberId + "] 회원의 권한을 " + memberRoleStr + "로 변경하시겠습니까?")) {
		var $frm = $(document.memberRoleUpdateFrm);
		$frm.find("[name=memberId]").val(memberId);
		$frm.find("[name=memberRole]").val(memberRole);
		$frm.submit();
	}
	else {
		//기본 선택된 option태그로 다시 변경
		$(this).children("[selected]").prop("selected", true);
	}
});
/* SEARCH BAR */
/**
 * #searchType이 변경되면, 해당 form이 노출되어야 한다.
 */ 
$(searchType).change(function(){
	// searchType의 value값을 보면 뭘 선택했는지 확인할 수 있음
	var searchTypeVal = $(this).val();

	$(".search-type")
		.hide() // 모든 div를 다 감추고 
		.filter("#search-" + searchTypeVal) // 해당 선택자와 일치하는 것을 찾음
		.css("display", "inline-block"); // 그것의 display를 inline-block으로 바꾸기
});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
