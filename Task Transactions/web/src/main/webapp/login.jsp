<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form method="post" action="./j_security_check">
		<label>User </label><input name="j_username" type="text">
		<label>Password </label><input name="j_password" type="password">
		<input type="submit" value="Login">
	</form>
</body>
</html>