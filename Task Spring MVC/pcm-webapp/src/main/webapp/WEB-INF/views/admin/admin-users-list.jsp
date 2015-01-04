<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/style-admin.css" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/font-awesome.css" />
    <link media="all" type="text/css" rel="stylesheet" href="${path}/resources/css/jquery.dataTables.min.css" />
	
	<script src="${path}/resources/js/jquery.min.js"></script>
    <script src="${path}/resources/js/users.js"></script>
    <script src="${path}/resources/js/jquery.dataTables.min.js"></script>

<script>					
var data = ajaxLoadUsers("${companiesCount}", "${role}");

$(document).ready(function(){
    $('table.items').DataTable({
		data: data,
    	columns: [
			{ data: 'check', width: "20px" },
       	 	{ data: 'name' },
        	<c:if test="${companiesCount}">
        		{ data: 'companies', width: "100px" },
        	</c:if>
       	 	{ data: 'email', width: "200px" },
			{ data: 'state', width: "100px", "sClass": "center"},
			{ data: 'id', "sClass": "right", width: "30px" }
    	],
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
		"columnDefs": [ 
			{'targets': 0, 'orderable': false }
		],
		"order": [[ ("true" === "${companiesCount}") ? 5 : 4, "desc" ]],
		'dom': 			'<"top"f>rt<"bottom"lp>'
	});
	$('table.items thead th:eq(0)').css("padding","0px");
});
</script>

</head>

<body>
	<div id="root">
    	<jsp:include page="includes/admin-menu.jsp"/><!-- left-side -->
        
        <div class="main-side">
            <div class="headline row">
            	<h1>
            	<c:choose>
            		<c:when test="${role eq 'COMPANY'}">
            			Организации
            		</c:when>
            		<c:when test="${role eq 'MANAGER'}">
            			Менеджеры
            		</c:when>
            		<c:when test="${role eq 'USER'}">
            			Пользователи
            		</c:when>
            	</c:choose>
            	</h1>
                <div class="controls">
            		<ul>
            			<li><a href="/users/user?role=${role}" class="delete btn-standard">Добавить</a></li>
                		<li><a href="#" class="delete btn-standard">Удалить</a></li>
                	</ul>
                </div>
            </div><!-- headline -->
            
            <form method="post" class="main-form">
            
            	<div class="container row">
                	
                    <div class="head row">
                    	<div class="submenu">
                    		<ul>
                    			<c:if test="${role eq 'COMPANY'}">
                    				<c:set var="selectedCompany" value="class=\"selected\""/>
                    			</c:if>
                        		<li ${selectedCompany}><a href="/users?role=COMPANY">Организации</a></li>
                            	<c:if test="${role eq 'MANAGER'}">
                    				<c:set var="selectedManager" value="class=\"selected\""/>
                    			</c:if>
                            	<li ${selectedManager}><a href="/users?role=MANAGER">Менеджеры</a></li>
                            	<c:if test="${role eq 'USER'}">
                    				<c:set var="selectedUser" value="class=\"selected\""/>
                    			</c:if>
                        		<li ${selectedUser}><a href="/users?role=USER">Пользователи</a></li>
                        	</ul>
                    	</div><!-- submenu -->
                
                		<div class="filter-bar row">
                			<div class="filter-select">
                    		</div><!--filter-select -->
            			</div><!-- filter-bar -->
                    </div><!-- head -->
                
            		<table class="items">
                		<thead>
                    		<tr>
                            	<th class="dt-head-left"> <input type="checkbox" class="checkall" /> </th>
                    			<th class="dt-head-left">Имя</th>
                                <c:if test="${companiesCount}">
                                	<th class="dt-head-left">Организаций</th>
                                </c:if>
                                <th class="dt-head-left">E-mail</th>
                                <th class="dt-head-center">Состояние</th>
                                <th class="dt-head-right">id</th>
                    		</tr>
                    	</thead>
                	</table>
            	</div><!-- container -->
			</form><!-- main-form -->
            
        </div><!-- main-side-->
        
    </div><!-- root -->
</body>
</html>