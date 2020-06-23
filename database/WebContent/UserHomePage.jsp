<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
		
	<title>Insert title here</title>
	
	<style type="text/css">
		<%@include file="stylesheets/userhomepage.css" %>
    </style>

</head>
	<body>
		<% if(session == null){ response.sendRedirect("LoginForm.jsp");}%>
		
		<div class="topnavbar">
			<p><a href="LoginForm.jsp">Back to Login</a><p>
			<form action="search">
				<input style="width: 500px" type="text" name="search" placeholder="Search for comedian name or tags" required>
				<button class="btn btn-secondary btn-sm active" type="submit">Search</button>
			</form>
		</div>
	</body>
</html>