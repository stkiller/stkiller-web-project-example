<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK REL=StyleSheet HREF="style.css" TYPE="text/css">
<title>You can add a group here</title>
</head>
<body>
	<div class="inner">
		<c:import url="/WEB-INF/view/Header.jsp"></c:import>
		<center>
			<c:if test="${validation != null && validation != \"\" }">
				<div style="color: red;">${validation}</div>
			</c:if>
			<form action="index.html?action=add_group" method="post">
				<input type="hidden" value="${group.id}" name="id"/>
				Group name : <input type="text" name="name" value="${group.name }"/><br /> 
				Group description : <input type="text" name="description" value="${group.description }"/><br /> 
				<div>
				Group roles : 
				<c:forEach var="role" items="${roles}">
					<input type="checkbox" name="roles_id" value="${role.id}" />${role.name}
				</c:forEach>
				</div>
				<input type="submit" value="Submit">
			</form>
		</center>
	</div>
</body>
</html>