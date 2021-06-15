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
	 
	<!-- 2. GET
			/menus/kr
			/menus/ch
			/menus/jp
	-->
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
					<input type="radio" class="form-check-input" name="taste" id="get-no-taste" value="all" checked>
					<label for="get-no-taste" class="form-check-label">모두</label>&nbsp;
					<input type="radio" class="form-check-input" name="taste" id="get-hot" value="hot" checked>
					<label for="get-hot" class="form-check-label">매운맛</label>&nbsp;
					<input type="radio" class="form-check-input" name="taste" id="get-mild" value="mild">
					<label for="get-mild" class="form-check-label">순한맛</label>
				</div>
				<br />
				<input type="submit" class="btn btn-block btn-outline-success btn-send" value="전송" >
			</form>
		</div>
		<div class="result" id="menuRecommendation-result"></div>
		<script>
		/* 폼은 제출하되, 페이지 이동없이 안에서 비동기 요청 */
		$("#menuRecommendationFrm").submit(e => {
			// 폼제출 방지 : return false;
			// 첫줄에다가 return false하면 밑에는 아무것도 실행안되니까
			// 대신 e.preventDefault()함수 사용
			e.preventDefault();

			// 현재폼
			// submit event가 일어났기 때문에 e.target이 바로 frm
			const $frm = $(e.target);
			// 사용자가 선택한 값 가져오기
			const type = $frm.find("[name=type]:checked").val();

			const taste = $frm.find("[name=taste]:checked").val();
			console.log(type, taste);
			
			$.ajax({
				url:`${pageContext.request.contextPath}/menus/\${type}/\${taste}`,
				success(data){
					console.log(data);
					displayResultTable("menuRecommendation-result", data);
				},
				error: console.log
			});
		});
		</script>
		<!-- 2.POST /menu -->
		<div class="menu-test">
			<h4>메뉴 등록하기(POST)</h4>
			<form id="menuEnrollFrm">
				<input type="text" name="restaurant" placeholder="음식점" class="form-control" />
				<br />
				<input type="text" name="name" placeholder="메뉴" class="form-control" />
				<br />
				<input type="number" name="price" placeholder="가격" class="form-control" />
				<br />
				<div class="form-check form-check-inline">
					<input type="radio" class="form-check-input" name="type" id="post-kr" value="kr" checked>
					<label for="post-kr" class="form-check-label">한식</label>&nbsp;
					<input type="radio" class="form-check-input" name="type" id="post-ch" value="ch">
					<label for="post-ch" class="form-check-label">중식</label>&nbsp;
					<input type="radio" class="form-check-input" name="type" id="post-jp" value="jp">
					<label for="post-jp" class="form-check-label">일식</label>&nbsp;
				</div>
				<br />
				<div class="form-check form-check-inline">
					<input type="radio" class="form-check-input" name="taste" id="post-hot" value="hot" checked>
					<label for="post-hot" class="form-check-label">매운맛</label>&nbsp;
					<input type="radio" class="form-check-input" name="taste" id="post-mild" value="mild">
					<label for="post-mild" class="form-check-label">순한맛</label>
				</div>
				<br />
				<input type="submit" class="btn btn-block btn-outline-success btn-send" value="등록" >
			</form>
		</div>
		<script>
		/**
		* POST /menu
		* post는 message body에 작성되기 때문에, url에 쓰지 않음
		*/
		$("#menuEnrollFrm").submit(e => {
			e.preventDefault(); // 폼 제출 방지
			// 사용자 입력값을 하나씩 가져와서 변수에 담기
			const $frm = $(e.target);
			const restaurant = $frm.find("[name=restaurant]").val(); 
			const name = $frm.find("[name=name]").val(); 
			const price = Number($frm.find("[name=price]").val()); 
			const type = $frm.find("[name=type]:checked").val(); 
			const taste = $frm.find("[name=taste]:checked").val(); 

			// 사용자 입력값을 모아서 객체 생성
			// 속성명과 속성값이 동일한 경우
			// ex. restaurant:restaurant -> restaurant로 줄여쓸 수 있음
			const menu = {
				restaurant,
				name,
				price,
				type,
				taste
			};
			console.log(menu);
			// data로 JSON문자열로 바뀐 값 전달 -> JSON문자열로 바뀌어서 텍스트로 날아감
			// json문자열로 보낼 때는 꼭 contentType 작성
			$.ajax({
				url: "${pageContext.request.contextPath}/menu",
				data: JSON.stringify(menu),
				contentType: "application/json; charset=utf-8",
				method: "POST",
				success(data) {
					console.log(data);
					const {msg} = data;
					alert(msg);
				},
				error: console.log, 
				complete(){
					e.target.reset(); // 폼초기화
				} 
			});
		});
		</script>
	<!-- #3.PUT /menu/123 -->
	<div class="menu-test">
		<h4>메뉴 수정하기(PUT)</h4>
		<p>메뉴번호를 사용해 해당메뉴정보를 수정함.</p>
		<form id="menuSearchFrm">
			<input type="text" name="id" placeholder="메뉴번호" class="form-control" /><br />
			<input type="submit" class="btn btn-block btn-outline-primary btn-send" value="검색" >
		</form>
		<hr />
		<form id="menuUpdateFrm">
			<input type="hidden" name="id" />
			<input type="text" name="restaurant" placeholder="음식점" class="form-control" />
			<br />
			<input type="text" name="name" placeholder="메뉴" class="form-control" />
			<br />
			<input type="number" name="price" placeholder="가격" step="1000" class="form-control" />
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="type" id="put-kr" value="kr" checked>
				<label for="put-kr" class="form-check-label">한식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="put-ch" value="ch">
				<label for="put-ch" class="form-check-label">중식</label>&nbsp;
				<input type="radio" class="form-check-input" name="type" id="put-jp" value="jp">
				<label for="put-jp" class="form-check-label">일식</label>&nbsp;
			</div>
			<br />
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" name="taste" id="put-hot" value="hot" checked>
				<label for="put-hot" class="form-check-label">매운맛</label>&nbsp;
				<input type="radio" class="form-check-input" name="taste" id="put-mild" value="mild">
				<label for="put-mild" class="form-check-label">순한맛</label>
			</div>
			<br />
			<input type="submit" class="btn btn-block btn-outline-success btn-send" value="수정" >
		</form>
	</div>
	<script>
	$("#menuUpdateFrm").submit(e => {
		e.preventDefault();
		const $frm = $(e.target);

		// 객체만들기
/*  		const menu = {
			id : $frm.find("[name=id]").val(),
			restaurant : $frm.find("[name=restaurant]").val(),
			name : $frm.find("[name=name]").val(),
			price : $frm.find("[name=price]").val(),
			type : $frm.find("[name=type]:checked").val(),
			taste : $frm.find("[name=taste]:checked").val()
		}; */

		// formData를 활용해서 객체만들기
		// formData를 사용하면 form안의 값을 쉽게 가져올 수 있음
		// formData 객체는 해당 폼 안의 데이터값을 고스란히 가지고 있음
		// but 보여주지 않음 -> 꺼내는 작업 필요
		const frmData = new FormData(e.target);
		// 빈 menu 객체 생성
		// -> json문자열로 만들기 위해, key, value속성을 menu객체에 옮겨담기
		// 이때, key - value 순이 아닌, value - key순으로 들어옴
		const menu = {};
		frmData.forEach((value, key) => {
			menu[key] = value;
		});
		console.log(menu);
		// ---> formData안의 값들을 menu객체에 옮겨담음!

		$.ajax({
			url: `${pageContext.request.contextPath}/menu/\${menu.id}`,
			method: "PUT",
			data: JSON.stringify(menu),
			contentType: "application/json; charset=utf-8",
			success(data){
				console.log(data);
				const {msg} = data;
				alert(msg);
			},
			error: console.log,
			// 다 처리된 후에는 폼 리셋
			complete(){
				$("#menuSearchFrm")[0].reset();
				$("#menuUpdateFrm")[0].reset();
			}
		});
	});
	
	$("#menuSearchFrm").submit(e => {
		e.preventDefault();
		// $("선택자", context) -> context 하위의 "선택자"를 조회 
		const id = $("[name=id]", e.target).val();
		if(!id) return; // 아이디가 존재하지 않는다면 조기리턴

		$.ajax({
			url: `${pageContext.request.contextPath}/menu/\${id}`,
			method: "GET",
			success(data){
				console.log(data);

				if(data){
					const $frm = $("#menuUpdateFrm");
					const {id, restaurant, name, price, type, taste} = data;
					$frm.find("[name=id]").val(id);
					$frm.find("[name=restaurant]").val(restaurant);
					$frm.find("[name=price]").val(price);
					$frm.find("[name=name]").val(name);
					$frm.find(`[name=type][value=\${type}]`).prop("checked", true);
					$frm.find(`[name=taste][value=\${taste}]`).prop("checked", true);
					
				}
				// 첫번째 방식의 경우
				//else {
				//	alert("해당 메뉴가 존재하지 않습니다.");
				//	$("[name=id]", e.target).select();
				//}
			},
			error(xhr, statusText, err){
				console.log(xhr, statusText, err);

				const {status} = xhr;
				status == 404 && alert("해당 메뉴가 존재하지 않습니다.");
				$("[name=id]", e.target).select();
			}
		})
	});		
	</script>
	<!-- 4. 삭제 DELETE /menu/123 -->    
	<div class="menu-test">
    	<h4>메뉴 삭제하기(DELETE)</h4>
    	<p>메뉴번호를 사용해 해당메뉴정보를 삭제함.</p>
    	<form id="menuDeleteFrm">
    		<input type="text" name="id" placeholder="메뉴번호" class="form-control" /><br />
    		<input type="submit" class="btn btn-block btn-outline-danger btn-send" value="삭제" >
    	</form>
    </div>
    <script>
	$("#menuDeleteFrm").submit(e => {
		e.preventDefault();

		// 아이디값 알아내기
		const id = $("[name=id]", e.target).val();
		if(!id) return; // 아이디값이 존재하지 않으면 조기리턴
		// success : function(){}의 단축형
		// error: console.log -> 자바스크립트가 만들어준 존재하는 함수를 가져다가 메소드로 사용
		$.ajax({
			url: `${pageContext.request.contextPath}/menu/\${id}`,
			method: "DELETE",
			success(data){
				console.log(data);
				const {msg} = data;
				alert(msg);
			},
			error(xhr, statusText, err){
				const {status} = xhr;
				switch(status){
					case 404: alert("해당 메뉴가 존재하지 않습니다."); break;
					default: alert("메뉴 삭제 실패!");
				}
			},
			complete(){
				$(e.target)[0].reset();
			}
		});
	});
    </script>
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
					<td><span class="badge badge-\${taste == 'hot' ? 'danger' : 'warning'}">\${taste}</span></td>
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
