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
	</center>
	<div align="center">
		<form action="login" method="post">
	        <table>
	        	<th>Username:</th>
	        	<tr>
		        	<input type="text" name="username" size="45" value="<c:out value='${user.username}' />"/>
	        	</tr>
	        	<th>Password:</th>
	        	<tr>
		        	<input type="text" name="password" size="45" value="<c:out value='${user.password}' />"/>
	        	</tr>
	        	<td align="center">
                    <input type="submit" value="Save" />
                </td>
	        </table>
        </form>
	</div>



</body>
</html>