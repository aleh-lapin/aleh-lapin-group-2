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
    <script src="${path}/resources/js/plugins/chosen.jquery.js"></script>
    <script src="${path}/resources/js/company.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        
        <div class="main-side">
            <div class="headline row">
            	<h1>Категория "Магазины"</h1>
                <div class="controls">
            		<ul>
                		<li><a href="#" onClick="Company.save()" class="save btn-standard btn-green">Сохранить</a></li>
                		<li><a href="#" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form method="post" class="main-form">
            	<div class="container row">
                
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                        		<li><a href="/companies/company?id=${id}">Общее</a></li>
                            	<li class="selected"><a href="/companies/company/catalog?id=${id}">Товары и услуги</a></li>
                            	<li><a href="/companies/company/gallery?id=${id}">Фотогалерея</a></li>
                                <li><a href="/companies/company/comments?id=${id}">Отзывы</a></li>
                        	</ul>
                    	</div><!-- submenu -->
                    </div><!-- head -->
                    
                    <div class="import-text">
                    	Воспользуйтесь <a href="#" class="btn-standard btn-blue">экспортом<span class="glyphicon glyphicon-cloud-download"></span></a> или
                        <a href="#" class="btn-standard btn-blue">импортом<span class="glyphicon glyphicon-cloud-upload"></span></a> товаров и услуг!
                    </div><!-- import-text -->
                    
                    <div class="catalog-columns row">
                    	<div class="column categories">
                        	<h2>Мои категории</h2>
                            
                            <div class="layout-loader"></div>
                            
                        	<div class="cat-list">
                            
                            	<div class="headers">
                                <div class="inner row">
                                	<div class="col-1">Название</div>
                                	<div class="col-2">Родительская категория</div>
                                	<div class="col-3">Действия</div>
                                </div><!-- inner -->
                                </div><!-- headers -->
                                
                            	<!--
                            	<div class="cat-item new" data-category-id="0">
                                <div class="inner row">
                                	<div class="col-1"> <input type="text" name="category_name[]" value="" /> </div>
                                    <div class="col-2">
                                    	<select name="category-id[]">
                                        	<option value="0" selected>Родительская</option>
                                            <option value="1">Электроинструмент</option>
                                            <option value="2"> - Дрели</option>
                                            <option value="3"> - Вибраторы</option>
                                            <option value="4">Десерты</option>
                                        </select>
                                    </div>
                                    <div class="col-3">
                                    	<span class="cancel glyphicon glyphicon-remove"></span>
                                        <a href="#" class="save btn-standard btn-green">OK</a>
                                    </div>
                                </div>
                                </div> -->
                                
                            </div><!-- cat-list-->
                            
                            <div class="add-category">
                            	<a href="#" class="add btn-standard btn-green">Добавить категорию</a>
                            </div>
                            
                        </div><!-- column -->
                        
                    	<div class="column products">
                        	<h2>Мои товары и услуги</h2>
                            
                            <div class="layout-loader"></div>
                            
                            <div class="products-list" data-category-id="0">
                            	
                                <!--
                            	<div class="product-item new row" data-product-id="0">
                                    <div class="editing">
                                    	<div class="row">
                                        	<input type="text" name="product_name" class="name" placeholder="Название.." />
                                            <input type="text" name="product_price" class="price" placeholder="Цена..." />
                                        </div>
                                        <div class="row">
                                        	<select name="category_id">
                                            	<option value="0" selected>- Выберите категорию -</option>
                                            	<option value="1">Электроинструмент</option>
                                            	<option value="2"> - Дрели</option>
                                            	<option value="3"> - Вибраторы</option>
                                            	<option value="4">Десерты</option>
                                        	</select>
                                        </div>
                                        <div class="row"><textarea name="product_descr" class="descr" placeholder="Описание товара или услуги..."></textarea></div>
                                        <div class="row">
                                        	<ul class="images row">
                            					<li class="loaded main" data-image-id="1">
                                                	<span class="main-text">Главная</span>
                                					<a href="#" class="image"><img src="images/banner1.jpg"> </a>
                                				    <a herf="#" class="delete">Удалить</a>
                                				</li>
                            					<li class="loaded" data-image-id="0">
                                                	<span class="main-text">Главная</span>
                                					<a href="#" class="image"><img src="images/banner1.jpg"> </a>
                                				    <a herf="#" class="delete">Удалить</a>
                                				</li>
                            					<li class="new">
                                					<a href="#" class="image"><span class="glyphicon glyphicon-plus"></span> </a>
                                				</li>
                            				</ul>
                                        </div>
                                        <div class="actions">
                                        	<a href="#" class="save btn-standard btn-green">Сохранить</a>
                                        	<a href="#" class="cancel btn-standard">Отменить</a>
                                        </div>
                                    </div>
                                </div><!--prod-item -->
                                
                            </div><!-- products-list -->
                            
                            <div class="add-product hidden">
                            	<a href="#" class="add btn-standard btn-green">Добавить еще</a>
                            </div>
                            
                        </div><!-- column -->
                        

                    </div><!-- columns -->
            	</div><!-- container -->
            </form><!-- main-form -->
            
        </div><!-- main-side-->
        
    </div><!-- root -->
</body>
</html>