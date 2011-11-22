<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	<div style="width: 75%; background-color: silver; margin-left: auto; margin-right: auto;">
    <c:import url="/WEB-INF/view/Header.jsp"></c:import>
    <center><h2>There you can see our users!</h2></center>
	<table border="1" >
		<tr>
			<th>ID</th><th>Name</th><th>Login</th><th>Password</th><th>Group ID</th>
		</tr>
		<c:forEach var="user" items="${users}">
		<tr>
			<td>${user.id }</td><td>${user.name}</td><td>${user.login}</td><td>${user.password}</td><td>${user.groupID}</td>
		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>