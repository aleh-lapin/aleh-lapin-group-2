<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false" ></script> 
    <script src="${path}/resources/js/company.js"></script>
    <script src="${path}/resources/js/plugins/chosen.jquery.js"></script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        <div class="main-side">
            <div class="headline row">
            	<h1>
					<c:if test="${empty company.name}">
						Новая Компания
					</c:if>
					<c:if test="${not empty company.name}">
    					${company.name}
					</c:if>
				</h1>
                <div class="controls">
            		<ul>
                		<li><a href="#" onClick="Company.save()" class="save btn-standard btn-green">Сохранить</a></li>
                		<li><a href="/companies" class="exit btn-standard">Выйти</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form:form action = "/companies/company" method="post" commandName="company" class="main-form">
            	<form:input type="hidden" path="id"/>
            	<form:input type="hidden" path="pageInfo.id"/>
           		<div class="container row">
                
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                    			<c:if test="${empty company.name}">
                    				<li class="selected"><a href="/companies/company">Общее</a></li>
                    			</c:if>
                    			<c:if test="${not empty company.name}">
                    				<li class="selected"><a href="/companies/company?id=${copany.id}">Общее</a></li>
	                            	<li><a href="/companies/company/catalog?id=${company.id}">Товары и услуги</a></li>
	                            	<li><a href="/companies/company/gallery?id=${company.id}">Фотогалерея</a></li>
	                                <li><a href="/companies/company/comments?id=${company.id}">Отзывы</a></li>
                        		</c:if>
                    		</ul>
                    	</div><!-- submenu -->
                    	<c:if test="${not empty company.name}">
    						<div class="company-site"><a href="${company.subDomain}">${company.name}</a></div>
						</c:if>
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
                    		<form:input type="text" path="subDomain"/>
                    	</div><!-- field -->
                        
                		<div class="field">
                    		<label>Категория</label>
                    		<form:select path="category" data-placeholder="Выберите категорию..." class="chosen-select">
                            	<form:option value="" label="Нет родителя" />
                                <c:forEach var="category" items="${categoriesTree}">
									<c:set var="rightPadding" value="" />
									<c:forEach var="i" begin="1" end="${category.level - 1}">
										<c:set var="rightPadding" value="${rightPadding}-" />	
								    </c:forEach>	
									<form:option value="${category.id}" label="${rightPadding}${category.name}"/>
								</c:forEach>
                            </form:select>
                    	</div><!-- field -->
                        
                		<div class="field">
                    		<label>Описание</label>
                        	<form:textarea path="description"/>
                    	</div><!-- field --> 
                        
                		<div class="field">
                    		<label>Телефоны</label>
                            <div class="company-phones">
                        		<div class="item">
                        			<c:choose>
	                        			<c:when test="${not empty company.phones}">
		                                	<c:forEach var="phone" items="${company.phones}" varStatus="i">
		                                		<div class="inner">
		                                			<form:input type="text" path="phones" value="${phone}"/>
		                                			<c:if test="${i.index + 1 eq fn:length(company.phones)}">
		                                				<span class="add glyphicon glyphicon-plus"></span>
		                                 			</c:if>
		                                    	</div>
		                                	</c:forEach>
	                                	</c:when>
	                                	<c:otherwise>
	                                		<div class="inner">
		                                		<form:input type="text" path="phones"/>
		                                    	<span class="add glyphicon glyphicon-plus"></span>
		                                    </div>
	                                	</c:otherwise>
                                	</c:choose>
                                </div>
                            </div>
                    	</div><!-- field --> 
                        
                		<div class="field">
                    		<label>Регион</label>
                            <select name="city_id[]" data-placeholder="Выберите город..." class="chosen-select" multiple>
                            	<option value="0">Все регионы</option>
                            	<option value="1">Минск</option>
                                <option value="2">Гомель</option>
                                <option value="3">Брест</option>
                            </select>
                    	</div><!-- field --> 
                         
                         <div class="field">
                    		<label>Адрес</label>
                            <div class="company-address">
                            	<c:choose>
                            		<c:when test="${not empty company.addresses}">
                            			<c:forEach items="${company.addresses}" var="address" varStatus="i" begin="0" >
					                        <div class="item" data-map-id="${i.index + 1}">
			                                	<div class="inner">
			                                		<form:input type="hidden" path="addresses[${i.index}].id"/>
			                                		<form:input type="text" path="addresses[${i.index}].address"/>
			                                    	<form:input type="hidden" path="addresses[${i.index}].coords" class="coords"/>
			                                    	<span class="map glyphicon glyphicon-map-marker"></span>
			                                    	<span class="add glyphicon glyphicon-plus"></span>
			                                    </div>
			                                </div>
					                    </c:forEach>	
                            		</c:when>
                            		<c:otherwise>
                            			<div class="item" data-map-id="1">
			                            	<div class="inner">
		                            			<input type="hidden" name="addresses[0].id" value="0"/>
					                            <input type="text" name="addresses[0].address"/>
					                            <input type="hidden" name="addresses[0].coords" class="coords"/>
					                            <span class="map glyphicon glyphicon-map-marker"></span>
					                            <span class="add glyphicon glyphicon-plus"></span>
				                            </div>
			                            </div>
			                      </c:otherwise>
			                  </c:choose>
                            </div>
                    	</div><!-- field --> 
                         
                		<div class="field">
                    		<label>Владелец</label>
                            <c:if test="${empty company.owner}">
								<c:set var="selected_owner" value = "selected" />
							</c:if>
                            <form:select path="owner" data-placeholder="Владелец..." class="chosen-select">
                            	<option ${selected_owner} value=""> - Владелец отсутствует - </option>
                            	<form:options items="${companiesOwners}" />
                            </form:select>
                    	</div><!-- field -->      

                		<div class="field">
                    		<label>Менеджер</label>
                    		<c:if test="${empty company.manager}">
								<c:set var="selected_manager" value = "selected" />
							</c:if>
                            <form:select path="manager" data-placeholder="Менеджер..." class="chosen-select">
                            	<option ${selected_manager} value=""> - Менеджер отсутствует - </option>
                                <form:options items="${managers}" />
                            </form:select>
                    	</div><!-- field -->        

                		<div class="field">
                    		<label>Статус</label>
                            <form:select path="status" class="chosen-select">
                            	 <form:options items="${statuses}" />
                            </form:select>
                    	</div><!-- field -->      
                        
                        <input type="submit" style="display: none;" />
                                 
                	</fieldset>
            	</div><!-- container -->
            </form:form><!-- main-form -->
            
        </div><!-- main-side-->
        <c:choose>
        	<c:when test="${not empty company.addresses}">
        		<c:forEach items="${company.addresses}" var="address" varStatus="i">
			   		<div class="popup-form popup-company-map" id="popup-map-${i.index + 1}">
				     	<div class="popup-close"><a href="#" class="poput-close-btn"></a></div>
				     	<div class="popup-content">
				         	<div class="google-map-layout" id="google-map-${i.index + 1}"></div>
				        </div>
			   		</div><!-- popup-company-map -->
			   	</c:forEach>
			 </c:when>
			 <c:otherwise>
			 	<div class="popup-form popup-company-map" id="popup-map-1">
				   	<div class="popup-close"><a href="#" class="poput-close-btn"></a></div>
				   	<div class="popup-content">
				       	<div class="google-map-layout" id="google-map-1"></div>
				    </div>
			   	</div><!-- popup-company-map -->
			 </c:otherwise>
		</c:choose>
		<script>
			$('.chosen-select').chosen('{}');
		</script>        
	</div><!-- root -->
</body>
</html>