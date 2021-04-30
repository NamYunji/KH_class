<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/standard/header.jsp">
	<jsp:param value="ANOTHER" name="title"></jsp:param>
</jsp:include>
		<article>
		<h2>another페이지 another페이지 another페이지</h2>
		<!-- 서버에서 실행되는 코드는 contextPath 필요 없음. 클라이언트에서 실행되는 건 톰캣에게 요청 보내는것. 톰캣한테 이 주소로 연결해줘! 하는거라 contextPath 필요	 -->
		<a href="${pageContext.request.contextPath}/standard/include.jsp">include</a>
		</article>
<jsp:include page="/standard/footer.jsp"></jsp:include>
</html>