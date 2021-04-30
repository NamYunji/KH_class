<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jsp의 include를 이용한 페이지 재사용  -->
<!-- include할 때 값을 전달하는 것 -->
	<!-- title이라는 이름으로 include라는 값을 전달 -->
<jsp:include page="/standard/header.jsp">
	<jsp:param value="INCLUDE" name="title"/>
</jsp:include>
		<article>
		<h2>Lorem ipsum dolor sit amet, consectetur adipisicing elit. 
			Magni incidunt quibusdam obcaecati minima fugiat officia praesentium doloribus iure voluptate at numquam eius sapiente fuga.
			Quia voluptas ratione inventore aliquid quos.</h2>	
		<a href="${pageContext.request.contextPath}/standard/another.jsp">another</a>
		</article>
<jsp:include page="/standard/footer.jsp"></jsp:include>
