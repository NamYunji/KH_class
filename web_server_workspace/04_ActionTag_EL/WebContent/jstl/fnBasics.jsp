<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>fn basics</title>
</head>
<body>
	<h1>fn basics</h1>
	<c:set var="str1" value="Hello world, Hello JSTL"/>
	<c:set var="str2" value="Hello EL"/>
	<p>${fn:toUpperCase(str1)}</p> <!-- HELLO WORLD, HELLO JSTL -->
	<p>${fn:toLowerCase(str2)}</p> <!-- hello el -->
	<p>${fn:length(str2)}</p>
	<p>${str1.length()}</p> <!-- 23 -->
	<p>${fn:contains(str1, "world")}</p> <!-- true -->
	<c:if test="${fn:contains(str1, 'world')}">
		world 있다.
	</c:if>
	<c:if test="${not fn:contains(str1, 'world')}">
		world 없다.
	</c:if>
	<!-- world 있다. -->
	<p>${fn:indexOf(str1, 'world')}</p> 
	<p>${fn:indexOf(str1, 'water')}</p>
	<c:if test="${fn:indexOf(str1, 'world') > -1}">
		world 있다.
	</c:if>
	<c:if test="${not (fn:indexOf(str1, 'world') == -1)}">
		world 없다.
	</c:if>
	
	<p>${fn:replace(str1, 'Hello', 'Goodbye')}</p>
	
	<c:set var="badScript"><script>document.body.style.color = "red";</script></c:set>
	<%-- ${badScript} --%>
	${fn:escapeXml(badScript)}
</body>
</html>