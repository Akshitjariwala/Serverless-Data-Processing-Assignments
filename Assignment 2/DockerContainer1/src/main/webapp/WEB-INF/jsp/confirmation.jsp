
<HTML xmlns:th="http://www.thymeleaf.org">
<HEAD>
	User Registration
</HEAD>
<BODY>
	<table>
	<tr>Name : ${user.userName}</tr>
	<tr>Email : ${user.userEmail}</tr>
	<tr>Password : ${user.userPassword}</tr>
	<tr>Topic : ${user.userTopic}</tr>
	</table>
</BODY>
</HTML>