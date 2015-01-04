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
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/glyphicons.css" />  
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/chosen.css" /> 
    	
	<script src="${path}/resources/js/jquery.min.js"></script>
    <script src="${path}/resources/js/script.js"></script>
    <script src="${path}/resources/js/users.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        
        <div class="main-side">
            <div class="headline row">
            	<h1>Пользователь: Миша</h1>
                <div class="controls">
            		<ul>
                		<li><a href="#" onClick="User.save()" class="save btn-standard btn-green">Сохранить</a></li>
                		<li><a href="/users?role=${role}" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form:form action="/users/user" method="post" commandName="userForm" class="main-form">
            	<form:input type="hidden" path="id"/>
            	<form:input type="hidden" path="role"/>
            	<div class="container row">
                
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                        		<li><a href="#">Организации</a></li>
                            	<li><a href="#">Менеджеры</a></li>
                            	<li class="selected"><a href="#">Пользователи</a></li>
                        	</ul>
                    	</div><!-- submenu -->
                    </div><!-- head -->
                    
            		<fieldset>
                		<div class="field">
                    		<label>Имя</label>
                        	<form:input type="text" path="name"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Город</label>
                    		<form:select path="cityId" data-placeholder="Город..." class="chosen-select">
                            	<form:options items="${citiesMap}" />
                            </form:select>
                        </div><!-- field -->
                		<div class="field">
                    		<label>E-mail</label>
                        	<form:input type="text" path="email"/>
                    	</div><!-- field -->
                		<div class="field password">
                    		<label>Пароль</label>
                        	<form:input type="password" path="password" placeholder="Пароль..." />
                            <div class="confirm">
                            <input type="password" name="user_password2" placeholder="Подтвердите пароль..." />
                            <a href="#" class="save btn-standard btn-green">Сохранить</a>
                            <a href="#" class="cancel btn-standard btn-white">Отмена</a>
                            </div>
                    	</div><!-- field -->
                        
                		<div class="field">
                    		<label>Телефон</label>
                        	<form:input type="text" path="phone"/>
                    	</div><!-- field -->
                        
                        
                        <div class="companies-layout">
                        	<h2>Избранные организации пользователя</h2>
                          	<div class="slider-inner row">
                               	<div class="items">
                               		<c:forEach var="info" items="${userForm.infos}">
	                               		<div class="slide">
	                               			<a href="/companies?filter=yes&company_id=${info.id}" class="link-company">
	                           					<div class="image"> <img src="${info.image}" alt="bla"> </div>
	                                   			<h3>${info.name}</h3>
	                                   		</a>
	                               		</div>
                               		</c:forEach>
                               	</div><!-- items-->
                            </div><!-- slider-inner -->
                        </div>
                        <input type="submit" style="display: none;" />
                                       
                	</fieldset>
            	</div><!-- container -->
            </form:form><!-- main-form -->
            
        </div><!-- main-side-->
        
<script src="${path}/resources/js/plugins/chosen.jquery.js"></script>
<script>
	$('.chosen-select').chosen('{}');
</script>        

    </div><!-- root -->
</body>
</html>