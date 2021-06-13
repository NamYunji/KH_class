<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu - RestAPI</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- bootstrap js: jquery load 이후에 작성할것.-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<!-- bootstrap css -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

<!-- 사용자작성 css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style.css" />

<style>
div.menu-test{width:50%; margin:0 auto; text-align:center;}
div.result{width:70%; margin:0 auto;}
</style>

</head>
<body>
<div id="container">
	<header>
		<div id="header-container">
			<h2>Menu - RestAPI</h2>
		</div>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">
				<img src="${pageContext.request.contextPath }/resources/images/logo-spring.png" alt="스프링로고" width="50px" />
			</a>
		  	<!-- 반응형으로 width 줄어들경우, collapse버튼관련 -->
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
		  	</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				
			 </div>
		</nav>
	</header>

	<section id="content">

    <!-- 1.GET /menus-->
    <div class="menu-test">
        <h4>전체메뉴조회(GET)</h4>
        <input type="button" class="btn btn-block btn-outline-success btn-send" id="btn-menus" value="전송" />
    </div>
    <div class="result" id="menus-result"></div>
    <script>
    // displayResultTable("menus-result", data); 함수호출
    // - displayResultTable("어디에 테이블을 만들건지 id값", data)
	$("#btn-menus").click(()=> {
		$.ajax({
			url: "${pageContext.request.contextPath}/menus",
			method: "GET",
			success(data){
				console.log(data);
				displayResultTable("menus-result", data);
			},
			error: console.log
		})
	});
   </script>
	    
	<div id="menu-container" class="text-center">
		<div class="menu-test">
			<h4>추천메뉴(GET)</h4>
			<form id="menuRecommendationFrm">
				<div class="form-check form-check-inline">
					<input type="radio" class="form-check-input" name="type" id="get-no-type" value="all" checked>
					<label for="get-no-type" class="form-check-label">모두</label>&nbsp;
					<input type="radio" class="form-check-input" name="type" id="get-kr" value="kr">
					<label for="get-kr" class="form-check-label">한식</label>&nbsp;
					<input type="radio" class="form-check-input" name="type" id="get-ch" value="ch">
					<label for="get-ch" class="form-check-label">중식</label>&nbsp;
					<input type="radio" class="form-check-input" name="type" id="get-jp" value="jp">
					<label for="get-jp" class="form-check-label">일식</label>&nbsp;
				</div>
				<br />
				<div class="form-check form-check-inline">
					<input type="radio" class="form-check-input" name="taste" id="get-no-taste" value="" checked>
					<label for="get-no-taste" class="form-check-label">모두</label>&nbsp;
					<input type="radio" class="form-check-input" name="taste" id="get-hot" value="hot" checked>
					<label for="get-hot" class="form-check-label">매운맛</label>&nbsp;
					<input type="radio" class="form-check-input" name="taste" id="get-mild" value="mild">
					<label for="get-mild" class="form-check-label">순한맛</label>
				</div>
				<br />
				<input type="button" class="btn btn-block btn-outline-success btn-send" value="전송" >
			</form>
		</div>
		<div class="result" id="menuRecommendation-result"></div>
	</div>
	</section>
		<footer>
			<p>&lt;Copyright 2017. <strong>KH정보교육원</strong>. All rights reserved.&gt;</p>
		</footer>
<script>
function displayResultTable(id, data){
	const $container = $("#" + id);
	/* 테이블 동적 생성 */
	let html = "<table class='table'>";
	html += "<tr><th>번호</th><th>음식점</th><th>메뉴</th><th>가격</th><th>타입</th><th>맛</th></tr>";

	/* 배열로 넘어온 데이터 처리 : forEach */
	/* 주의 : 
		mybatis session.selectList는 조회된 것이 아무것도 없어도 null이 넘어오지 않음
		데이터가 없다면 빈 list를 리턴
		무조건 배열은 있는데 데이터가 있느냐 없느냐의 차이
	*/
	/* 검색된 데이터가 있는 경우 */
	/* 인덱스와 menu객체가 넘어옴 */
	if(data.length > 0) {
		$(data).each((i, menu) => {
			/* menu객체의 것들을 하나씩 변수에 옮겨담기 */
			const {id, restaurant, name, price, type, taste} = menu;
			/* 변수의 값들을 td안에 넣어줌 */
			/* [`] template string 사용 : 여러줄 입력 가능 */
			html +=`
				<tr>
					<td>\${id}</td>
					<td>\${restaurant}</td>
					<td>\${name}</td>
					<td>\${price}</td>
					<td>\${type}</td>
					<td>\${taste}</td>
				</tr>`;
		}); 
	}
	/* 검색된 데이터가 없는 경우 */
	else {
		html += "<tr><td colspan='6'>검색된 결과가 없습니다.</td></tr>";
	}

	html += "</table>";
	/* 만들어준 html을 추가해줌 */
	$container.html(html);
}
</script>
	</div>
	</body>
</html>
