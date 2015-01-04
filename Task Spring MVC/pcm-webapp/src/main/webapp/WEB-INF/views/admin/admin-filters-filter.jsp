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
    <script src="${path}/resources/js/filters-groups.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        
        <div class="main-side">
            <div class="headline row">
            	<c:if test="${empty filter.name}">
						Новый Фильтр
					</c:if>
					<c:if test="${not empty filter.name}">
    					${filter.name}
					</c:if>
                <div class="controls">
            		<ul>
                		<li><a href="#" onClick="Filter.save()" class="save btn-standard btn-green">Сохранить</a></li>
                		<li><a href="/filters/groups" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form:form action = "/filters/filter" method="post" commandName="filter" class="main-form">
           		<form:input type="hidden" path="id"/>
           		<div class="container row">
                
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                        		<li><a href="/filters/groups">Группы</a></li>
                        		<li class="selected"><a href="/filters">Фильтры</a></li>
                        	</ul>
                    	</div><!-- submenu -->
                    </div><!-- head -->
                    
                    <fieldset>
                    	 <div class="field">
                    	 	<label>Название</label>
                         	<form:input type="text" path="name"/>
                    	 </div><!-- field -->
                         <div class="field">
                    		<label>Тип фильтра</label>
                            <form:select path="filterType" data-placeholder="Тип фильтра..." class="chosen-select">
                            	<form:options items="${filtersTypesMap}" />
                            </form:select>
                    	</div><!-- field -->
                    	<div class="field">
                    		<label>Группа</label>
                            <form:select path="filterGroup" data-placeholder="Группа..." class="chosen-select">
                            	<form:options items="${filtersGroupsMap}" />
                            </form:select>
                    	</div><!-- field -->
                    	<div class="field">
                    		<label>Статус</label>
                            <form:select path="status" class="chosen-select">
                            	 <form:options items="${statuses}" />
                            </form:select>
                    	</div><!-- field -->
                    </fieldset>
                    
                    <c:if test="${not empty filter.name}">                   
	                    <div class="filter-attributes row">
	                    	<div class="column attributes">
	                            
	                            <div class="layout-loader"></div>
	                            
	                        	<div class="attributes-list">
	                            
	                            	<div class="headers">
	                                <div class="inner row">
	                                	<div class="col-1">Название</div>
	                                	<div class="col-2"></div>
	                                	<div class="col-3">Действия</div>
	                                </div><!-- inner -->
	                                </div><!-- headers -->
	                                
	                            </div><!-- groups-list-->
	                            
	                            <div class="add-attribute">
	                            	<a href="#" class="add btn-standard btn-green">Добавить атрибут</a>
	                            </div>
	                            
	                        </div><!-- column -->
	
	                    </div><!-- columns -->
                    </c:if>
            	</div><!-- container -->
            </form:form><!-- main-form -->
            
        </div><!-- main-side-->
        
    </div><!-- root -->
</body>
</html>