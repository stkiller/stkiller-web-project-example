<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <c:import url="/WEB-INF/view/Header.jsp"></c:import>
	<form action="addUser.html" method="post">
		User login : <input type="text" name="login"/><br/>
		User pass : <input type="password" name="pass"/><br/>
		User group : 	<select name="group_id">
							<c:forEach var="group" items="${groups}">
								<option value="${group.id }">${group.name }</option>
							</c:forEach>
						</select>
	 <input type="submit" value="Submit">
	</form>
</body>
</html>