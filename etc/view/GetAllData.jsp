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
 	<div style="width: 75%; background-color: silver; margin-left: auto; margin-right: auto;">
    <c:import url="/WEB-INF/view/Header.jsp"></c:import>
    <center>
    <h3>Users</h3>
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
	</center>
	
    <center>
    <h3>Groups</h3>
	<table border="1" >
		<tr>
			<th>ID</th><th>Name</th><th>Description</th>
		</tr>
		<c:forEach var="group" items="${groups}">
		<tr>
			<td>${group.id }</td><td>${group.name}</td><td>${group.description}</td>
		</tr>
		</c:forEach>
	</table>
	</center>
		
    <center>
    <h3>Roles</h3>
	<table border="1" >
		<tr>
			<th>ID</th><th>Name</th><th>Description</th>
		</tr>
		<c:forEach var="role" items="${roles}">
		<tr>
			<td>${role.id }</td><td>${role.name}</td><td>${role.description}</td>
		</tr>
		</c:forEach>
	</table>
	</center>
	</div>
</body>
</html>