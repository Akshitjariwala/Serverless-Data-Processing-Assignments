<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	Home Page
</head>
<body>
<h1>You are successfully logged in.</h1>

<h1>List of Online users.</h1>
	<table>
		 <c:forEach items="${list}" var="user">
		 <tr>
                <td>${user}</td>
         </tr>
         </c:forEach>
	<form method="post" action="logout">
	<tr><input type="submit" value="Log Out"></tr>
	</form>
</table>
</body>
</html>