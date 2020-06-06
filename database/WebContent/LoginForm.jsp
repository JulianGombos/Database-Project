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
	<center>
		<h1>User Login</h1>
        <a href="AddUser.jsp">Create User</a>
	</center>
	<div align="center">
		<form action="login" method="post">
	        <table>
	        	<tr>
	        		<th>Username:</th>
	        		<td>
		        	<input type="text" name="username" size="45" value="<c:out value='${user.username}' />"/>
		        	</td>
	        	</tr>
	        	<tr>
	        		<th>Password:</th>
	        		<td>
		        	<input type="text" name="password" size="45" value="<c:out value='${user.password}' />"/>
		        	</td>
	        	</tr>
	        	<th></th>
	        	<td>
                    <input type="submit" value="Login" />
                    <!--<a href="insert">Create New User</a>  -->
                </td>
	        </table>
        </form>
	</div>
</body>
</html>