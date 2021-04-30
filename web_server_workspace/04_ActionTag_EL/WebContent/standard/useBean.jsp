<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--
jsp action을 이용한 데이터 가져오기 방법, 액션태그는 자바코드로 변환됨 -> 클라이언트에서는 확인불가
[id] id로 속성에 담은 이름을 꺼내줌 
[class] 어떤 타입인지 적어줌, 클래스는 패키지를 포함한 풀네임으로 적기
[scope] 기본값 : 페이지 콘텍스트 (현재페이지)에서만 사용가능
		session, application, request 등을 작성가능
		어디에 저장한 걸 가져오는지를 저장함
		pageContext.setAttribute으로 저장 -> scope="pageContext"
		request.setAttribute -> scope="request"
		session.setAttribute -> scope="session"
		application.setAttribute -> scope="application"
		[scope의 작동방식]
			해당 scope에서 id와 동일한 속성명으로 저장된 객체를 가져온다
			scope을 안쓰면 자동으로 현재페이지에서만 사용 가능
			존재하지 않으면, 해당타입의 객체를 하나 생성한다.
						
property의 접근방식 : OGNL (Object Graph Navigation Language)
- getter : getter에서 get을 제외하고 소문자로 시작하는 이름을 가져다 씀
- setter : setter에서 set을 제외하고 소문자로 시작하는 이름을 가져다 씀 
cf. 자바언어를 사용하는 프레임워크들은 이 OGNL방식을 많이 사용함
	el도 이 OGNL의 property접근 방식을 사용한다.

-->
<!-- scope내에 객체가 있는 경우 -> honggd라는 객체를 가져옴 -->
<jsp:useBean id="honggd" class="com.kh.person.model.vo.Person" scope="request"></jsp:useBean>
<!-- scope내에 객체가 없는 경우 -> 객체 생성 -->
<jsp:useBean id="sinsa" class="com.kh.person.model.vo.Person"/> <!-- 종료태그 없이 시작태그에 /로 끝내도 됨-->
<!-- new Person()으로 호출하지 않아도 이렇게 객체 생성 가능
	-> Person person = new Person();과 같은 작용 (아직은 값이 비어있는 상태) -->
<!-- 비어있는 객체에 값 setting -->
<jsp:setProperty property="id" value="sssinsa" name="sinsa"/>
<jsp:setProperty property="name" value="씬사임당" name="sinsa"/>
<jsp:setProperty property="gender" value="여" name="sinsa"/>
<jsp:setProperty property="age" value="50" name="sinsa"/>
<jsp:setProperty property="married" value="true" name="sinsa"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>useBean</title>
<style>
table, th, td {
	border : 1px solid #000;
	padding : 5px;
	margin-botton : 1px;
}
</style>
</head>
<body>
	<h1>useBean</h1>
	
	<!-- HONGGD -->
	<table>
		<tr>
			<th>id</th>
			<!-- 
			[값 꺼내쓰기]
			property속성 : 
				속성값은 vo객체의 getter에서 get을 제외하고 소문자로 시작하는 나머지 이름을 적음
				보통은 vo클래스의 field명과 같음
				근데 field명을 그대로 가져오는 겡 아니라 getter를 따오는 것
				만약 필드명과 getter명이 다르다면 여기에 getter명을 적어야 하는 것
			name속성 : 
				useBean의 아이디 -->
			<td><jsp:getProperty property="id" name="honggd" /></td> <!-- getId -> id -->
		</tr>
		<tr>
			<th>name</th>
			<td><jsp:getProperty property="name" name="honggd" /></td>
		</tr>
		<tr>
			<th>gender</th>
			<td><jsp:getProperty property="gender" name="honggd" /></td>
		</tr>
		<tr>
			<th>age</th>
			<td><jsp:getProperty property="age" name="honggd" /></td>
		</tr>
		<tr>
			<th>married</th>
			<td><jsp:getProperty property="married" name="honggd" /></td> <!-- isMarried -> married -->
		</tr>
	</table>
	
	<!-- SINSA -->
	<table>
		<tr>
			<th>id</th>
			<td><jsp:getProperty property="id" name="sinsa" /></td> 
		</tr>
		<tr>
			<th>name</th>
			<td><jsp:getProperty property="name" name="sinsa" /></td>
		</tr>
		<tr>
			<th>gender</th>
			<td><jsp:getProperty property="gender" name="sinsa" /></td>
		</tr>
		<tr>
			<th>age</th>
			<td><jsp:getProperty property="age" name="sinsa" /></td>
		</tr>
		<tr>
			<th>married</th>
			<td><jsp:getProperty property="married" name="sinsa" /></td>
		</tr>
		<!-- 비어있는 객체이기 때문에, 기본값 (null,0)으로 나옴 
		    값을 넣으려면 setProperty로 작성-->
	</table>

</body>
</html>