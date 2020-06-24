<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
		
	<title>Home Page</title>
	
	<style type="text/css">
		<%@include file="stylesheets/userhomepage.css" %>
    </style>

</head>
	<body>
		<% if(session == null){ response.sendRedirect("LoginForm.jsp");}%>
		<div class="topnavbar">
			<div class="container">
				<div class="login-button">
					<a href="LoginForm.jsp" class="btn btn-light" role="button" aria-pressed="true">Sign Out</a>
				</div>
				<form action="search" method="get">
					<div class="search-bar">
						<div class="input-group flex-nowrap">
							<input type="text" style="width: 500px" class="form-control" name="search" placeholder="Search by comedian name or tags" aria-label="Username" aria-describedby="addon-wrapping" required>
						</div>
					</div>
					<div class="search-button">
						<button class="btn btn-secondary btn-sm active" type="submit">Search</button>
					</div>
				</form>
			</div>
		</div>
		<div style="padding-top: 20px; padding-left: 10px">
			<form action="favoritelist" method="get">
				<button class="btn btn-primary btn-lg active" type="submit">Display Favorite Comedians</button>
			</form>
		</div>
	</body>
</html>