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
    	
	<script src="${path}/resources/js/jquery.min.js"></script>
	<script src="${path}/resources/js/category.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        <div class="main-side">
            <div class="headline row">
            	<h1>
					<c:if test="${empty category.name}">
						Новая Категория
					</c:if>
					<c:if test="${not empty category.name}">
    					${category.name}
					</c:if>
				</h1>
                <div class="controls">
            		<ul>
                		<li><a href="#" onclick="Category.save()" class="save btn-standard btn-green">Сохранить</a></li>
                		<li><a href="/categories" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            <form:form action = "/categories/category" method="post" commandName="category" class="main-form">
            	<form:input type="hidden" path="id"/>
            	<form:input type="hidden" path="pageInfo.id"/>
            	<div class="container row">
            		<div class="head row">
                    	<div class="submenu">
                    		<ul>
                        		<li class="selected"><a href="/categories/category?id=${category.id}">Общее</a></li>
                            	<c:if test="${not empty category.name}">
			    					<li><a href="/categories/media?id=${category.id}">Медиа</a></li>
								</c:if>
                        	</ul>
                    	</div><!-- submenu -->
                    </div><!-- head -->
            		
            		<fieldset>
                		<div class="field">
                    		<label>Title (meta)</label>
                        	<form:input type="text" path="pageInfo.title"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Keywords (meta)</label>
                        	<form:input type="text" path="pageInfo.keywords"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Description (meta)</label>
                        	<form:input type="text" path="pageInfo.description"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Название*</label>
                    		<form:input type="text" path="name"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>URL-алиас*</label>
                    		<form:input type="text" path="URL"/>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Родительская категория</label>
                    		<form:select path="parentCategory">
                            	<form:option value="" label="Нет родителя" />
                                <c:forEach var="category" items="${categoriesTree}">
									<c:set var="rightPadding" value="" />
									<c:forEach var="i" begin="1" end="${category.level - 1}">
										<c:set var="rightPadding" value="${rightPadding}-" />	
								    </c:forEach>
								    <c:choose>
									    <c:when test="${parentCategory.id eq categori.id}">
								    		<form:option selected="true" value="${category.id}" label="${rightPadding}${category.name}"/>
									    </c:when>
									    <c:otherwise>
									    	<form:option value="${category.id}" label="${rightPadding}${category.name}"/>
									    </c:otherwise>
								    </c:choose>
								</c:forEach>
                            </form:select>
                    	</div><!-- field -->
                		<div class="field">
                    		<label>Текст на главной</label>
                        	<form:textarea path="pageInfo.staticText"/>
                    	</div><!-- field --> 
                		<div class="field">
                    		<label>Фильтры</label>
	                    	<form:select path="appliedFilters" multiple="true">
							   <form:option value="" label="Нет фильтров"/>
								<c:forEach var="entry" items="${filtersMap}">
								  <optgroup label="${entry.key}">
									<form:options items="${entry.value}" />
								  </optgroup>
								</c:forEach>
							</form:select>
                    	</div><!-- field -->
                	</fieldset>
            	</div><!-- container -->
            </form:form><!-- main-form -->
        </div><!-- main-side-->
    </div><!-- root -->
</body>
</html>