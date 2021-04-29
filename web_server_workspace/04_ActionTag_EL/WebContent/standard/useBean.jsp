<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--
jsp action을 이용한 데이터 가져오기 방법, 액션태그는 자바코드로 변환됨 -> 클라이언트에서는 확인불가
[id] id로 속성에 담은 이름을 꺼내줌 
[class] 어떤 타입인지 적어줌, 클래스는 풀네임으로
[scope] 기본값 : 페이지 콘텍스트, 어디에 저장했는지를 적어주는 것, application, request 등등
		scope의 작동방식 : 해당 scope에서 id와 동일한 속성명으로 저장된 객체를 가져온다
						scope을 안쓰면 자동으로 현재페이지에서만 사용 가능
						존재하지 않으면, 해당타입의 객체를 하나 생성한다.
						
property의 접근방식 : OGNL (Object Graph Navigation Language)
- getter : getter에서 set을 제외하고 소문자로 시작하는 이름을 가져다 씀
- setter : setter에서 set을 제외하고 소문자로 시작하는 이름을 가져다 씀 
cf. el도 이 OGNL의 property접근 방식을 사용한다.
-->
<jsp:useBean id="honggd" class="com.kh.person.model.vo.Person" scope="request"></jsp:useBean>
<jsp:useBean id="sinsa" class="com.kh.person.model.vo.Person" scope="request"/>
<!-- newPerson생성하지 않아도 이렇게 객체 생성 가능 -->
<!-- 종료태그 없이 시작태그에 /로 끝내도 됨-->
<!-- 객체에 setting -->
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
			<!-- property속성값은 vo객체의 getter에서 get을 제외하고 소문자로 시작하는 나머지 이름을 적음, 보통은 field명과 같음, 근데 field명을 그대로 가져오는 겡 아니라 getter를 가져오는 것.
			만약 필드명과 getter명이 다르다면 여기에 getter명을 적어야 하는 것-->
			<!-- getId -> id -->
			<td><jsp:getProperty property="id" name="honggd" /></td>
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
			<!-- isMarried -> married -->
			<td><jsp:getProperty property="married" name="honggd" /></td>
		</tr>
	</table>
	
	<!-- SINSA -->
	<table>
		<tr>
			<th>id</th>
			<!-- property속성값은 vo객체의 getter에서 get을 제외하고 소문자로 시작하는 나머지 이름을 적음, 보통은 field명과 같음, 근데 field명을 그대로 가져오는 겡 아니라 getter를 가져오는 것.
			만약 필드명과 getter명이 다르다면 여기에 getter명을 적어야 하는 것-->
			<!-- getId -> id -->
			<!-- name값은 위 usebean의 아이디 -->
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
			<!-- isMarried -> married -->
			<td><jsp:getProperty property="married" name="sinsa" /></td>
		</tr>
		<!-- 비어있는 객체이기 때문에, 기본값 (null,0)으로 나옴 
		    값을 넣으려면 setProperty로 작성-->
	</table>

</body>
</html>