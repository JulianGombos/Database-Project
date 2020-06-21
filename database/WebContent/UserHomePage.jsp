<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% if(session == null){ response.sendRedirect("LoginForm.jsp");}%>
	<h1><a href="LoginForm.jsp">Back to Login</a></h1>
	<div>
		<h1>HOME PAGE</h1>
	</div>
</body>
</html>