<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<c:set var="path" value="${pageContext.request.contextPath}/resources/"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/css/style.css" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/css/font-awesome.css" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/css/chosen.css" />
	
	<script src="${path}/js/jquery.min.js"></script>
    <script src="${path}/js/script.js"></script>

</head>

<body>
	<div id="root">
        <jsp:include page="includes/header.jsp"/><!-- Header -->
        
        <div class="main">

     		<div class="popup-on-page row">
            	<header>
                	<h1>Подключайтесь к Pochem.by</h1>
                </header>
                <section class="login-popup">
                         <form:form action = "/join" method="post" commandName="joinForm" class="login-form">
                        	<div class="field"><input type="text" name="phoneOrEmail" placeholder="Телефон или E-mail" /></div>
                            <div class="field"><input type="password" name="password" placeholder="Пароль" /></div>
                            <div class="field"><input type="submit" name="submit" class="btn-standard btn-green" value="Продолжить" /></div>
                            <div class="field social-connect row">
                            	<span>или</span>
                                Войдите через соцсеть:
                                <div class="socials-items">
                                	<a href="#" class="social-login-btn fb"></a>
                                	<a href="#" class="social-login-btn vk"></a>
                                	<a href="#" class="social-login-btn ok"></a>
                                </div>
                            </div>
                        </form:form>
                        <div class="popup-footer">
                        	<p>Уже зарегистрированы? - <a href="/join">Войдите</a> на сайт </p>
                        </div><!-- popup-footer -->
                </section><!-- login-popup-->
            </div><!-- popup-on-page -->
            
            <div class="pochem-main-info row">
            	<div class="wrapper container">
                	<p></p>
                </div><!-- wrapper -->
            </div><!-- pochem-main-text -->
            
        </div><!-- main-content -->
        
        <jsp:include page="includes/footer.jsp"/><!-- Footer -->
    </div><!-- root -->
</body>
</html>