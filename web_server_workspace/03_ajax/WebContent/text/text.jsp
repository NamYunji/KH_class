<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax - text</title>
<style>
	table{
		board-collapse: collapse;
		board:1px solid black;
		margin:5px;
	}
	th, td{
		border:1px solid black;
	}
	table img{
		width:150px;
	}
</style>
<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.js"></script>
</head>
<body>
	<h1>text</h1>
	<input type="button" value="실행" id="btn1" />
	<div class="wrapper"></div>
	<hr />
	<h2>csv</h2>
	<input type="button" value="실행" id="btn2" />
	<div class="container"></div>
<script>
$(btn1).click(function(){
	console.log("btn1 clicked!");
	// ajax함수 호출
	// $.ajax에 객체를 전달하는 방식
	$.ajax({
		// 솎성 전달
		// url속성 : 어디로 요청할지
		url:"<%=request.getContextPath()%>/text",
		// data속성 : 서버에 전달되는 값
		// data : "name=podo&num=12345", // 직렬화된 형태 (&구분자로 여러개가 일자로 연결됨)
		// 위와 같이 직접 만들지 않고, 객체를 넘기면 jquery가 알아서 직렬화된 형태로 만들어줌 - 객체형태 
		data:{ 
			name:"strawberry",
			num:34.56,
			flag:false
		},
		// method속성 : 어떤 방식으로 전송할지 (기본값 : get, 생략가능)
		method:"POST",
		// dataType속성 : 응답 데이터형식 (뭐가 리턴될것이라 예상하는지 (text, html, xml, json ...))
		dataType:"text",
		// 비동기요청 간 자동으로 호출되는 함수(메소드) : beforeSend, success, error, complete
		// 함수명:function(){ 함수작성 };
		// 요청전후에 처리할 것이 있다면 beforeSend|complete 사용, 아니면 작성하지 않아도 됨 (아무것도 실행하지 않음)
		// 클릭했을 때 beforeSend로 loading이미지를 띄워놓고, 요청이 끝나면 complete에서 제거
		// beforeSend메소드 : 전송전 실행 콜백함수
		beforeSend:function(){
			// 전송전에 console창에 출력함
			console.log("beforeSend");
		},
		// success메소드 : 실행요청 성공시 실행 콜백함수
		// 매개인자로 응답 message(data)가 전달됨
		success:function(data){
			console.log("success call!");
			// data출력
			console.log(data);
			// 성공했다면 wrapper div에 data를 추가
			$(".wrapper").html(data);
		},
		// error메소드 : 요청 오류시 실행 콜백함수
		// 매개인자로 xhr, status, error 전달
		// 요청을 보냈는데 오류가 날 경우 (200번의 정상코드가 아닐 경우)
		// xhr : xmlHttpRequest 객체 (내부적으로 비동기요청을 처리하는 객체)
		// status : text status
		//	xhr객체의 statusText만 따로 꺼낸 것
		// error : 던져진 오류객체
		error:function(xhr, status, error){
			console.log("error call!");
			console.log(xhr, status, error);
		},
		// complete메소드 : (=finally) 요청 성공/오류 상관없이 무조건 마지막에 호출되는 콜백함수
		complete:function(){
			console.log("complete call!");
		}
	});
});
$(btn2).click(function(){
	$.ajax({
		url:"<%=request.getContextPath()%>/csv",

		success:function(data){
			console.log(data);
			var $table = $("<table></table>");
			var members = data.split("\n");
			members=members.slice(0, members.length-1);
			console.log(members);
			
			$.each(members, function(index, member){
				console.log(index,member);
				var arr=member.split(",");
				
				var tr = "<tr>";
				tr+="<td>"+arr[0]+"</td>";
				tr+="<td>"+arr[1]+"</td>";
				tr+="<td><img src='<%= request.getContextPath()%>/images/"+arr[2]+"'/></td>";
				tr+="</tr>";
				$table.append(tr);
			});
			$(".container").html($table);
		},
		error:function(xhr, status, err){
			console.log(xhr, status, err);
		}
	});
});
</script>
</body>
</html>