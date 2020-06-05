<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
	<body>
		<center>
		<h1>Create User</h1>
		</center>
		<form action="insert" method="post">
		<div align="center">
			<table border="1" cellpadding="5">
            <caption>
                <h2>
                        Add a New People now
                </h2>
            </caption>         
            <tr>
                <th>Username: </th>
                <td>
                    <input type="text" name="username" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Password: </th>
                <td>
                    <input type="text" name="password" size="45"/>
                </td>
            </tr>
            <tr>
                <th>First Name: </th>
                <td>
                    <input type="text" name="firstname" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Last Name: </th>
                <td>
                    <input type="text" name="lastname" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Age: </th>
                <td>
                    <input type="text" name="age" size="5"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
       	 </table>
       	 </form>
		</div>
	</body>
</html>