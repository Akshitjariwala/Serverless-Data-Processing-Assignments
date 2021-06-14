
<HTML xmlns:th="http://www.thymeleaf.org">
<head>
<title>User Registration Page</title>
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
        <li><h4 style="color: white;">Registration Form</h4></li>
      </ul>
    </div>
  </div>
</nav>

<% 
if(session.getAttribute("status") != null){ 
int result = (Integer)session.getAttribute("status");%>
<% if(result == 1){ %>
<div class="alert alert-success">
  <strong>Success!</strong> User Registered Successfully.
</div>
<% }else{ %>
<div class="alert alert-danger">
    <strong>Failure!</strong> User Registration failed. Please Register again.
 </div>
<%} %>
<% }else{ %>
<p></p>
<%} %>

  <form class="form-horizontal" action="register" method="post" modelAttribute="user">
    <div class="form-group">
      <label class="control-label col-sm-2" for="userName">Name:</label>
      <div class="col-sm-10">
        <input type="type" class="form-control" id="userName" placeholder="Enter name" name="userName">
      </div>
    </div>
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

		<label class="control-label col-sm-2" for="userTopic">Select Topic :</label>
		<div class="col-sm-10">
		<select class="form-control" id="userTopic" name="userTopic" style="width:945px;">
			<option value="A">A</option>
			<option value="B">B</option>
			<option value="C">C</option>
			<option value="D">D</option>
		</select>
		</div>
	</div>
	<div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">Register</button>
      </div>
    </div>
  </form>
</div>
</BODY>
</HTML>