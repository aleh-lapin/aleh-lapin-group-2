<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/style-admin.css" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/font-awesome.css" />  
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/glyphicons.css" />  
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/chosen.css" /> 
    	
	<script src="${path}/resources/js/jquery.min.js"></script>
    <script src="${path}/resources/js/category.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        
        <div class="main-side">
            <div class="headline row">
            	<h1>Медиа</h1>
                <div class="controls">
            		<ul>
                		<li><a href="/categories" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form method="post" class="main-form" data-category-id="${id}">
            	<div class="container row">
                
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                        		<li><a href="/categories/category?id=${param['id']}">Общее</a></li>
                            	<li class="selected"><a href="/categories/media?id=${param['id']}">Медиа</a></li>
                        	</ul>
                    	</div><!-- submenu -->
                    </div><!-- head -->
                    
            		<fieldset>

						<div class="field">
                    		<label>Фото категории</label>
                            <ul class="banners main row">
                            	<c:if test="${not empty categoryLogo}">
	                            	<li class="loaded" data-banner-id="${categoryLogo.id}">
	                                	<c:set var="logoUrl" value="/files/CATEGORY_LOGO/${param['id']}/${categoryLogo.fileUUID}.${categoryLogo.extension}"/>
	                                	<a href="${logoUrl}" class="image"><img src="${logoUrl}" /> </a>
	                                    <a href="#" class="delete">Удалить</a>
	                                </li>
                            	</c:if>
                            	<li class="new">
                                	<a href="#" class="image"><span class="glyphicon glyphicon-plus"></span> </a>
                                </li>
                            </ul>
                    	</div><!-- field --> 
                        
                		<div class="field">
                    		<label>Баннеры</label>
                            <ul class="banners row">
                            	<c:forEach var="categoryBanner" items="${categoryBanners}">
                            		<li class="loaded" data-banner-id="${categoryBanner.id}">
	                                	<c:set var="bannerUrl" value="/files/CATEGORY_BANNER/${param['id']}/${categoryBanner.fileUUID}.${categoryBanner.extension}" />
	                                	<a href="${bannerUrl}" class="image"><img src="${bannerUrl}" /> </a>
	                                    <a href="#" class="delete">Удалить</a>
	                                </li>
                            	</c:forEach>
                            	<li class="new">
                                	<a href="#" class="image"><span class="glyphicon glyphicon-plus"></span> </a>
                                </li>
                            </ul>
                    	</div><!-- field --> 
                    	                                       
                	</fieldset>
            	</div><!-- container -->
            </form><!-- main-form -->
            
        </div><!-- main-side-->

    </div><!-- root -->
</body>
</html>