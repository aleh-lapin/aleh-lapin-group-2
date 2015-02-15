<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buy tickets</title>
</head>
<body>
<label>Balance: ${balance}</label>
<br />
<label>Buy tickets</label>
<table>
<thead>
<tr>
<th>Name</th>
<th>Price</th>
<th>Buy</th>
</tr>
</thead>
<tbody>
<c:forEach var="ticket" items="${tickets}">
<tr>
<td>${ticket.name}</td>
<td>${ticket.price}</td>
<td>
<c:choose>
	<c:when test="${ticket.ordered}">Ordered</c:when>
	<c:otherwise><a href="/jboss/order?ticketId=${ticket.id}">Buy</a>
	</c:otherwise>
</c:choose>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>