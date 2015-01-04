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
    <script src="${path}/resources/js/cities.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
    	
        <div class="main-side">
            <div class="headline row">
            	<h1>Города</h1>
                <div class="controls">
            		<ul>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form method="post" class="main-form">
            	<div class="container row">
                
                    <div class="head row">
                    </div><!-- head -->
                    
                    <div class="cities row">
                    	<div class="column">
                            
                            <div class="layout-loader"></div>
                            
                        	<div class="cities-list">
                            
                            	<div class="headers">
	                                <div class="inner row">
	                                	<div class="col-1">Название</div>
	                                	<div class="col-2">Регион</div>
	                                	<div class="col-3">Действия</div>
	                                </div><!-- inner -->
                                </div><!-- headers -->                                
                                
                            </div><!-- groups-list-->
                            
                            <div class="add-city">
                            	<a href="#" class="add btn-standard btn-green">Добавить город</a>
                            </div>
                            
                        </div><!-- column -->

                    </div><!-- columns -->
            	</div><!-- container -->
            </form><!-- main-form -->
            
        </div><!-- main-side-->
        
    </div><!-- root -->
</body>
</html>