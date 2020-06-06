<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Good Login</title>
</head>
<body>
	<h1><a href="LoginForm.jsp">Back to Login</a></h1>
	<div align="center">
		<h2>User Info</h2>
		<table>
			<tr>
				<td>Username: </td>
				<td><c:out value="${user.username}" /></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td><c:out value="${user.password}" /></td>
			</tr>
			<tr>
				<td>First Name: </td>
				<td><c:out value="${user.firstName}" /></td>
			</tr>	
			<tr>
				<td>Last Name: </td>
				<td><c:out value="${user.lastName}" /></td>
			</tr>
			<tr>
				<td>Age: </td>
				<td><c:out value="${user.age}" /></td>
			</tr>
		</table>
	</div>
</body>
</html>