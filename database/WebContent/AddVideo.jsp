<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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