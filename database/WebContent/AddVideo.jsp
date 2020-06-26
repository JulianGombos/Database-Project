<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
	
<title>Upload Video</title>

</head>
<body>

<h1>Upload Video</h1>
<h2><a href="UserHomePage.jsp">Back to User Home Page</a></h2>

	<form action="upload" method="POST">
		<div align="center">
			<label>URL</label>
			<input type="text" name="URL" placeholder="YouTube link" required>
		</div>
		
		<br>
		<div align="center">
			<label>Title</label>
			<input type="text" name="Title" required>
		</div>
		
		<br>
		<div align="center">
			<label>Description</label>
			<input type="text" name="Description" required>
		</div>
		
		<br>
		<div align="center">
			<label>Tags</label>
			<input type="text" name="Tags" required>
		</div>
		
		<br>
		<div align="center">
			<label>Comedian Name</label>
			<input type="text" name="comedianName" required>
		</div>
		<div align="center">
		<input type="submit" name="submit" value="Submit">
		</div>
		
	</form>
</body>
</html>