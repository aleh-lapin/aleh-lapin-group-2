<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://pochem.by/taglibs" prefix="pcm"%>
<pcm:useConstants className="by.pochem.web.constants.Pages" />
<div class="left-side">
	<div class="logo">
		<a href="#">Админка</a>
	</div>
	<ul class="menu-nav">
		<c:forEach var="adminPage" items="${applicationScope['Pages.Admin.PAGES']}">
			<c:set var="selected" value=""/>
			<c:if test="${page eq adminPage.name}">
				<c:set var="selected" value="class=\"selected\""/>
			</c:if>
			<li ${selected}><a href="${adminPage.url}">${adminPage.label}</a></li>
		</c:forEach>
	</ul>
	<!-- menu-nav -->
</div>