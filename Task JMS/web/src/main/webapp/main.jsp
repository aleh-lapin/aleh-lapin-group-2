<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage account</title>
</head>
<body>
	<div id="messages"></div>
	<div>
		<textarea id="message" rows="3" cols="50"></textarea>
	</div>
	<button id="btn">Send</button>
	<script>
	 	$('#btn').click(function () {
	 		var message_val = $('#message').val();
	 		$.ajax({
	 			type: 'POST',
	 			url : 'JMSClientServlet',
	 			cache: false,
	 			data : {message : message_val}
	 		}
	 		).done(function() {
	 			console.log('Send');
	 		});
	 	});
	 	document.pool = function() {
		 	$.ajax({
	 			type: 'GET',
	 			url : 'JMSClientServlet',
	 			cache: false,
	 		}
	 		).done(function(data) {
	 			if (data != 'null') {
	 				$('#messages').append('<p>' + data + '</p>');
	 				console.log('Received');
	 			}
	 			document.pool();
	 		});
	 	}
	 	document.pool();
	</script>
</body>
</html>
