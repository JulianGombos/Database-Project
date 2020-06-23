<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
</head>
<body>
	<h1><a href="UserHomePage.jsp">Back to Home</a></h1>
	<div align="center">
		<table border="1" cellpadding="5">
            <caption><h2>Search Results</h2></caption>
            <tr>
                <th>Video Title</th>
                <th>Post User</th>
                <th>Video URL</th>
            </tr>
            <c:forEach var="result" items="${searchResults}">
                <tr>
                    <td><c:out value="${result.title}" /></td>
                    <td><c:out value="${result.postUser}" /></td>
                    <td><c:out value="${result.url}" /></td>
                </tr>
            </c:forEach>
        </table>
	</div>
</body>
</html>