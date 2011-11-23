<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK REL=StyleSheet HREF="style.css" TYPE="text/css">
<title>Insert title here</title>
</head>
<body>
	<div class="inner">
		<c:import url="/WEB-INF/view/Header.jsp"></c:import>
		<center>
			<form action="addUser.html" method="post">
				User name : <input type="text" name="name" /><br /> User login : <input
					type="text" name="login" /><br /> User pass : <input
					type="password" name="password" /><br /> User group : <select
					name="group_id">
					<c:forEach var="group" items="${groups}">
						<option value="${group.id }">${group.name }</option>
					</c:forEach>
				</select> <input type="submit" value="Submit">
			</form>
		</center>
	</div>
</body>
</html>