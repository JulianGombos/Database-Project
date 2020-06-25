<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Home Page</title>
</head>
<body>
	<% if(session == null){ response.sendRedirect("LoginForm.jsp");}%>
	<h1><a href="LoginForm.jsp">Back to Login</a></h1>
	
	<div>
		<h1>HOME PAGE</h1>
	</div>
	
	<div align="center">
		<a href = "AddVideo.jsp">Upload Video </a>
	</div>
	
	<br>
	<div align="center"> 
		<a href = "VideoPage.jsp">Go to Video Page </a> <!-- this will be removed once merged -->
	</div>
	
</body>
</html>