<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage account</title>
</head>
<body>
	<label>${account.user}</label>
	<table>
		<thead>
			<tr>
				<th>Currency</th>
				<th>Amount</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${currencies[account.currency]}</td>
				<td>${account.currentCount}</td>
			</tr>
		</tbody>
	</table>
	<table>
		<thead>
			<tr>
				<th>To</th>
				<th>Rate</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${rate.toCurrencyRate}">
				<tr>
					<td>${currencies[entry.key]}</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="5"
							value="${entry.value}"></fmt:formatNumber></td>
					<td>
						<form method="post" action="./exchange">
							<input name="accountId" type="hidden" value="${account.id}">
							<input name="currencyTo" type="hidden" value="${entry.key}">
							<input type="submit" value="Exchange">
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
