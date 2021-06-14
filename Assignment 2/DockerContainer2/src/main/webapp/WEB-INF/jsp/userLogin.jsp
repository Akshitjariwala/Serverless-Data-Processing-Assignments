<HTML xmlns:th="http://www.thymeleaf.org">
<head>
<title>User Login Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<BODY>

<div class="container">
  <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><h4 style="color: white;">Login Form</h4></li>
      </ul>
    </div>
  </div>
</nav>
  <form class="form-horizontal" method="post" action="validate" modelAttribute="LoginUser">
    <div class="form-group">
      <label class="control-label col-sm-2" for="userEmail">Email:</label>
      <div class="col-sm-10">
        <input type="email" class="form-control" id="userEmail" placeholder="Enter email" name="userEmail">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="userPassword">Password:</label>
      <div class="col-sm-10">
        <input type="password" class="form-control" id="userPassword" placeholder="Enter password" name="userPassword">
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">Login</button>
      </div>
    </div>
  </form>
</div>
</BODY>
</HTML>