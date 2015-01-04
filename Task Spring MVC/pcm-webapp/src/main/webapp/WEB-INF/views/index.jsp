<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <link media="all" type="text/css" rel="stylesheet" href="resources/css/style.css" />
	
	<script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/script.js"></script>

</head>

<body>
	<div id="root">
		<div class="wrapper">
  			<div class="header">
            	<a href="#" class="logo">Pochem.by</a>
                <div class="search">
                	<form>
                    	<input type="text" name="name" id="query" required placeholder="Поиск..." />
                        <input type="submit" class="submit" value="ок">
                        <a href="#" class="show-results show">Показать результаты</a>
                    </form>
                    <div class="results" style="display:none">
                    	<ul>
                        	<li>
                            	<div class="image"> <img src="#" width="50" height="50" />  </div>
                                <div class="info">
                                	<div class="title"> <a href="http://chaikoff.pochem.by/?q='пица и пиво'">Кофе Чайкофф </a></div>
                                    <div class="descr">Самое кафе из кафе из кафеиз кафеиз кафеиз кафеиз кафеиз кафе из кафе из кафе...</div>
                                    <div class="price">Стоимость: <span>99$</span> - <span>125$</span> </div>
                                    <!-- Артем, а если у нас будет сеть организаций, то как быть с поддоменом?
                                    у нас ведь на 1 огранизацию 1 поддомен. 
                                    Получится chaikoff1,chaikoff2... или там okna777, okna7771, okna7772 -->
                                </div>
                            </li>
                        	<li>
                            	<div class="image"> <img src="#" width="50" height="50" />  </div>
                                <div class="info">
                                	<div class="title"> <a href="http://chaikoff.pochem.by/?q='пица и пиво'">Автомойка Олежка </a></div>
                                    <div class="descr">Самое кафе из кафе из кафеиз кафеиз кафеиз кафеиз кафеиз кафе из кафе из кафе...</div>
                                    <div class="price">Стоимость: <span>59$</span> - <span>155$</span> </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- results -->
                </div>
                <!-- search -->
                
                <div class="login"> Вход / Регистрация </div>
            </div>
            <!-- header -->
            
        </div>
        <!-- wrapper -->
        
    </div>
</body>
</html>