<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- security관련 taglib -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${param.title}</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- bootstrap js: jquery load 이후에 작성할것.-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<!-- bootstrap css -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

<!-- 사용자작성 css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style.css" />

<%-- RedirectAttributes.addFlashAttribute의 저장된 속성값 사용(1회용) --%>
<c:if test="${not empty msg}">
<script>
alert("${msg}");
</script>
</c:if>

</head>
<body>
<div id="container">
	<header>
		<div id="header-container">
			<h2>${param.title}</h2>
		</div>
		<!-- https://getbootstrap.com/docs/4.0/components/navbar/ -->
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">
				<img src="${pageContext.request.contextPath }/resources/images/logo-spring.png" alt="스프링로고" width="50px" />
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
		  	</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav mr-auto">
			    	<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/board/boardList.do">게시판</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/memberList.do">회원관리</a></li>
                    
			    </ul>
			    <!-- 로그인 한 경우 -->
			    <!-- access : isAuthenticated()함수 -> 인증함 : true  -->
			    <!-- true면 안의 내용이 실행됨 -->
			    <sec:authorize access="isAuthenticated()">
			    <!-- authentication : 인증한 객체를 가지고 있는 태그 -->
			    <!-- property : principal.username -> 인증한 객체의 아이디 -->
			    <sec:authentication property="principal.username"/>님, 안녕하세요.
			    <!-- property : authorities -> 인증한 객체가 가지고 있는 권한 -->
			    <sec:authentication property="authorities"/>
			    &nbsp;
			    <!-- action값으로 로그아웃 하고자 하는 주소
			    	 method를 post로 설정 -->
			    <!-- logout버튼의 타입을 submit으로 하면 로그아웃 버튼을 누르면 폼 제출 -->
			    <!-- form namespace를 사용했기 때문에 csrf 토큰값이 발행됨 -->
			    <form:form class="d-inline" action="${pageContext.request.contextPath}/member/memberLogout.do" method="POST">
			    	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">로그아웃</button>
			    </form:form>
			    </sec:authorize>
			    
			    <!-- 로그인하지 않은 경우 -->
			    <!-- access : isAnonymous()함수 -> 인증하지 않음 : true -->
			    <sec:authorize access="isAnonymous()">
			    <button class="btn btn-outline-success my-2 my-sm-0" type="button" onclick="location.href='${pageContext.request.contextPath}/member/memberLogin.do';">로그인</button>
                &nbsp;
                <button class="btn btn-outline-success my-2 my-sm-0" type="button" onclick="location.href='${pageContext.request.contextPath}/member/memberEnroll.do';">회원가입</button>
			    </sec:authorize>
			 </div>
		</nav>
	</header>
	<section id="content">
	<sec:authentication property="principal"/>
	<sec:authentication property="credential"/>
	<sec:authentication property="authority"/>