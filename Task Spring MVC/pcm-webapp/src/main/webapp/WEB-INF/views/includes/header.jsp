<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}/resources/"/>
<header class="header-wrapper">
    		<div class="header">
            
               	<div class="line1 row">
                	<div class="wrapper">
               			<a href="#" class="logo">Pochem.by</a>
                   		<div class="search">
                   			<form>
                       			<input type="text" name="query" id="query-live-search" class="input">
                           		<input type="image" class="magnify" src="${path}/images/search-icon.png" alt="Go">
                           		<input type="image" class="loading" src="${path}/images/search-progress.gif">
                                <div class="live-search-results">
                                	<ul>
                                    	<li>
                                        	<a href="#" class="link-company">
                                            	<div class="image"><img src="${path}/images/news1.jpg" /></div>
                                                <div class="info">
                                                	<div class="title">Ресторан Прованс</div>
                                                    <div class="descr">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt</div>
                                                    <div class="price">19$ - 53$</div>
                                                </div>
                                            </a>
                                        </li>
                                    	<li>
                                        	<a href="#" class="link-company">
                                            	<div class="image"><img src="${path}/images/news1.jpg" /></div>
                                                <div class="info">
                                                	<div class="title">Ресторан Прованс</div>
                                                    <div class="descr">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt</div>
                                                    <div class="price">19$ - 53$</div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="load-more"> <a href="#" class="btn-green">Показать все</a></div>
                                </div>
                       		</form>
                   			<div class="select-region">
               	 				<a href="#">Минск</a>
                   			</div>
                   		</div>
                        <div class="main-nav nav-guest">
                   			<ul>
                       			<li><a href="#" class="open-popup-login login">Вход</a></li>
                       			<li><a href="#" class="open-popup-join btn-standard btn-green">Присоединиться</a></li>
                    		</ul>
                    	</div>        
               	  </div><!-- wrapper -->
              	</div><!-- line-->
               
               <div class="line2 row">
               	<div class="wrapper">
                  		<nav><ul class="nav-cat">
                      		<li>
                          		<a href="#">Еда</a>
                           	<div class="subcats">
                              		<ul>
                                  		<li><a href="#">Рестораны</a></li>
                                   	<li><a href="#">Кафе</a></li>
                                   	<li><a href="#">Бары и пабы</a></li>
                                   	<li><a href="#">Кофейни</a></li>
                                       <li><a href="#">Кофейни</a></li>
                               	</ul>
                               	<ul>
                                 		<li><a href="#">Пиццерии</a></li>
                                   	<li><a href="#">Суши-бары</a></li>
                                   	<li><a href="#">Доставка еды</a></li>
                                   	<li><a href="#">Банкетные залы</a></li>
                               	</ul>
                           	</div><!-- subcats -->
                       	</li>
                   		<li>
                       	   	<a href="#">Развлечения</a>
                       	</li>
                      		<li>
                       	   	<a href="#">Магазины</a>
                           	<div class="subcats">
                              		<ul>
                                  		<li><a href="#">Рестораны</a></li>
                                   	<li><a href="#">Кафе</a></li>
                                   	<li><a href="#">Бары и пабы</a></li>
                                   	<li><a href="#">Кофейни</a></li>
                                       <li><a href="#">Кофейни</a></li>
                               	</ul>
                               	<ul>
                                 		<li><a href="#">Пиццерии</a></li>
                                   	<li><a href="#">Суши-бары</a></li>
                                   	<li><a href="#">Доставка еды</a></li>
                                   	<li><a href="#">Банкетные залы</a></li>
                               	</ul>
                           	</div><!-- subcats -->
                      		</li>
                      		<li class="center">
                       	 	<a href="#">Авто</a>
                           	<div class="subcats">
                              		<ul>
                                  		<li><a href="#">Рестораны</a></li>
                                   	<li><a href="#">Кафе</a></li>
                                   	<li><a href="#">Бары и пабы</a></li>
                                   	<li><a href="#">Кофейни</a></li>
                                       <li><a href="#">Кофейни</a></li>
                               	</ul>
                               	<ul>
                                 		<li><a href="#">Пиццерии</a></li>
                                   	<li><a href="#">Суши-бары</a></li>
                                   	<li><a href="#">Доставка еды</a></li>
                                   	<li><a href="#">Банкетные залы</a></li>
                               	</ul>
                           	</div><!-- subcats -->
                       	</li>
                      		<li class="center">
                       	  	<a href="#">Обучение</a>
                           	<div class="subcats">
                              		<ul>
                                  		<li><a href="#">Рестораны</a></li>
                                   	<li><a href="#">Кафе</a></li>
                                   	<li><a href="#">Бары и пабы</a></li>
                                   	<li><a href="#">Кофейни</a></li>
                                       <li><a href="#">Кофейни</a></li>
                               	</ul>
                               	<ul>
                                 		<li><a href="#">Пиццерии</a></li>
                                   	<li><a href="#">Суши-бары</a></li>
                                   	<li><a href="#">Доставка еды</a></li>
                                   	<li><a href="#">Банкетные залы</a></li>
                               	</ul>
                           	</div><!-- subcats -->
                       	</li>
                      		<li>
                       	   	<a href="#">Отдых</a>
                       	</li>
                      		<li class="right">
                       		<a href="#">Бизнес</a>
                           	<div class="subcats">
                              		<ul>
                                  		<li><a href="#">Рестораны</a></li>
                                   	<li><a href="#">Кафе</a></li>
                                   	<li><a href="#">Бары и пабы</a></li>
                                   	<li><a href="#">Кофейни</a></li>
                                       <li><a href="#">Кофейни</a></li>
                               	</ul>
                               	<ul>
                                 		<li><a href="#">Пиццерии</a></li>
                                   	<li><a href="#">Суши-бары</a></li>
                                   	<li><a href="#">Доставка еды</a></li>
                                   	<li><a href="#">Банкетные залы</a></li>
                               	</ul>
                           	</div><!-- subcats -->
                       	</li>
                      		<li>
                       	   	<a href="#">Прочее</a>
                       	</li>
                   	</ul></nav>
               	</div><!-- wrapper -->
               </div><!-- line -->   
               
               <div class="slidedown-content">
               	<div class="login-reg module module-login">
                   	<div class="titles row">
                       	<div class="registration">Регистрация</div>
                           <div class="login">Вход</div>
                       </div>
                   	<form class="registration-form" method="post" action="/userRegistration">
                       	<div class="field"><input type="text" name="phone_email" placeholder="Телефон или E-mail" /></div>
                           <div class="field"><input type="password" name="password" placeholder="Пароль" /></div>
                           <div class="field"><input type="submit" name="submit" class="btn-standard btn-green" value="Продолжить" /></div>
                           <div class="field social-connect">
                           	<span>или</span>
                               Войдите через соцсеть:
                               <div class="socials-items">
                               	<a href="#" class="social-login-btn fb"></a>
                               	<a href="#" class="social-login-btn vk"></a>
                               	<a href="#" class="social-login-btn ok"></a>
                               </div>
                           </div>
                       </form>
                   	<form class="login-form" method="post" action="/userLogin">
                       	<div class="field"><input type="text" name="phone_email" placeholder="Телефон или E-mail" /></div>
                           <div class="field">
                           	<input type="password" name="password" placeholder="Пароль" />
                           	<a href="#" class="forgot-password">Напомнить</a>
                           </div>
                           <div class="field"><input type="submit" name="submit" class="btn-standard btn-green" value="Войти" /></div>
                           <div class="field social-connect">
                           	<span>или</span>
                               Войдите через соцсеть:
                               <div class="socials-items">
                               	<a href="#" class="social-login-btn fb"></a>
                               	<a href="#" class="social-login-btn vk"></a>
                               	<a href="#" class="social-login-btn ok"></a>
                               </div>
                           </div>
                       </form>
                   </div><!-- login-reg -->
               	<div class="forgot-pass module module-login">
                   	<div class="titles row">
                       	<div class="forgot">Восстановление пароля</div>
                       </div>
                   	<form class="forgot-form" method="post" action="/userLogin">
                       	<div class="step1">
                       		<div class="field">
                           		<input type="text" name="phone_email" placeholder="Телефон или E-mail" />
                               	<a href="#" class="getsms">Получить код</a>
                           	</div>
                           	<div class="field sms-code"><input type="text" name="sms_code" placeholder="Полученный код" /></div>
                           	<div class="field submit-code"><a href="#" class="btn-standard btn-green">Продолжить</a></div>
                           </div>
                       	<div class="step2" style="display: none;">
                       		<div class="field">
                           		<input type="password" name="password" placeholder="Новый пароль" />
                           	</div>
                           	<div class="field"><input type="submit" name="submit" class="btn-standard btn-green" value="Сохранить и войти" /></div>
                           </div>
                       </form>
                   </div><!-- login-reg -->

               	<div class="company-reg module module-login">
                   	<div class="titles row">
                       	<div class="company-reg">Регистрация компании</div>
                       </div>
                   	<form class="company-reg-form" method="post" action="/userRegistration">
                       	<div class="field">
                           	<input type="text" name="phone_email" placeholder="E-mail" />
                           </div>
                           <div class="field"><input type="password" name="password" placeholder="Пароль" /></div>
                           <div class="field"><input type="submit" name="submit" class="btn-standard btn-green" value="Продолжить" /></div>
                       </form>
                   </div><!-- company-reg -->

                   
               </div><!-- slidedown-content-->
       	</div><!-- header -->
       </header>