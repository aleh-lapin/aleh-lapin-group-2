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
    	
	<script src="${path}/resources/js/jquery.min.js"></script>
    <script src="${path}/resources/js/company.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        <div class="main-side">
            <div class="headline row">
            	<h1>Фотогалерея</h1>
                <div class="controls">
            		<ul>
                		<li><a href="/companies" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form method="post" class="main-form">
            	<div class="container row">
                
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                        		<li><a href="/companies/company?id=${id}">Общее</a></li>
                            	<li><a href="/companies/company/catalog?id=${id}">Товары и услуги</a></li>
                            	<li class="selected"><a href="/companies/company/gallery?id=${id}">Фотогалерея</a></li>
                                <li><a href="/companies/company/comments?id=${id}">Отзывы</a></li>
                        	</ul>
                    	</div><!-- submenu -->
                    </div><!-- head -->
                    
                    <div class="company-gallery">
                    	<h2>Фотогалерея</h2>
                    	<div class="gallery">
                        	<ul class="images">
                        		<c:forEach var="companyImage" items="${companyImages}">
                            		<li class="loaded" data-image-id="${companyImage.id}">
	                                	<c:set var="imageUrl" value="/files/COMPANY_IMAGE/${id}/${companyImage.fileUUID}.${companyImage.extension}" />
	                                	<a href="${imageUrl}" class="image"><img src="${imageUrl}" /> </a>
	                                    <a href="#" class="delete">Удалить</a>
	                                </li>
                            	</c:forEach>
                                <li class="new"><a href="#" class="image"><span class="glyphicon glyphicon-plus"></span></a></li>
                            </ul>
                        </div><!-- gallery -->
                    </div><!-- company-gallery-->
                    

            	</div><!-- container -->
            </form><!-- main-form -->
            
        </div><!-- main-side-->
        
    </div><!-- root -->
</body>
</html>