<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/style-admin.css" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/font-awesome.css" />    
	
	<script src="${path}/resources/js/jquery.min.js"></script>
    <script src="${path}/resources/js/home.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        <div class="main-side">
            <div class="headline row">
            	<h1>Главная</h1>
                <div class="controls">
            		<ul>
                		<li><a href="#" onClick="Home.save()" class="save btn-standard btn-green">Сохранить</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            <form:form action = "/home/update" method="post" commandName="mainPage" class="main-form">
            	<form:input type="hidden" path="id"/>
            	<form:input type="hidden" path="mainPage"/>
            	<div class="container row">
            		<fieldset>
                		<h2>Мета теги страницы</h2>
                		<div class="field">
                    		<label>Title</label>
                        	<form:input type="text" path="title"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Keywords</label>
                    		<form:input type="text" path="keywords"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Description</label>
                    		<form:input type="text" path="description"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Текст на главной</label>
                    		<form:textarea path="staticText"/>
                    	</div><!-- field -->                
                	</fieldset>
            	</div><!-- container -->
            </form:form>
        </div><!-- main-side-->
    </div><!-- root -->
</body>
</html>