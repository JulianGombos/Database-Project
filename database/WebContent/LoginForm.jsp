<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>

<body>
	<div align="center">
		<h1>User Login</h1>
        <a href="AddUser.jsp">Create User</a>
	</div>
	<div align="center">
		<form action="login" method="post">
	        <table>
	        	<tr>
	        		<th>Username:</th>
	        		<td>
		        	<input type="text" name="username" size="45" value="<c:out value='${user.username}' />" required/>
		        	</td>
	        	</tr>
	        	<tr>
	        		<th>Password:</th>
	        		<td>
		        	<input type="text" name="password" size="45" value="<c:out value='${user.password}' />" required/>
		        	</td>
	        	</tr>
	        </table>
	        <input type="submit" value="Login" />
        </form>
	</div>
</body>
</html>