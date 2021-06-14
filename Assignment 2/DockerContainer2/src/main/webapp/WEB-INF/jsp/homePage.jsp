<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <title>Home Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
 <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><h4 style="color: white;">Home Page</h4></li>
      </ul>
    </div>
  </div>
</nav>
  <h3>Hi ${userName}. You are logged in successfully!</h3>
  <p>Here is the list of online users.</p>     
  <form class="form-horizontal" method="post" action="logout">
  <table class="table table-striped">
    <tbody>
    <c:forEach var="obj" items="${lists}">
      <tr>
        <td>${obj}</td>
      </tr>
     </c:forEach>
     <tr><td><input type="submit" value="Log Out"></td></tr>
    </tbody>
  </table>
  </form>
</div>
</body>
</html>

