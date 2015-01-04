//jQuery
$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       return results[1] || 0;
    }
};

// Main initialization
$(document).ready(function(){

	//Company Page - General Tab
	//-------------------------------------------------------------------
	if (window.location.pathname == '/companies/company') {
		addPhoneInput();
		addAddressInput();
	}

	//Company Page - Products Tab
	//-------------------------------------------------------------------
	if (window.location.pathname == '/companies/company/catalog') { 
		loadCategories();
		refreshCategoriesEvents();
		refreshProductsEvents();
	}

	//Company Page - Gallery Tab
	//-------------------------------------------------------------------
	if (window.location.pathname == '/companies/company/gallery') {
		refreshGalleryEvents();
	}
	
});


var Company={};

/* Functions for Companies List Page */
//-------------------------------------------------------------------
$(document).ready(function() {
	$("table.items input.checkall").click(function(){
		if ($(this).is(':checked')) {
			$('table.items tr input[type="checkbox"]').prop("checked", true);
			if ($(".main-form .actions").length!=0)
				$(".main-form .actions").removeClass("hidden");
		}
		else {
			$('.reviews-table table tr input[type="checkbox"]').prop("checked", false);
			if ($(".main-form .actions").length!=0)
				$(".main-form .actions").addClass("hidden");
		}
	});
});

Company.remove = function () {
	checked = $('table.items tr td input.check-company:checked').length;
	if (checked==0) return false;
	if (!confirm('Удалить выбранные организации?')) return false;
	$("form.main-form").submit();
	return false;
};
/*Company Page - Companies List Page ENDS*/
//-------------------------------------------------------------------

Company.save = function () {
	$('#company').submit();
};


/* Functions for Company Page - General Tab */
//-------------------------------------------------------------------
function addPhoneInput() {
	$(".company-phones .item .add").click(function(e){
		var new_item = '<div class="item"> <div class="inner"> <input type="text" name="company_phones[]"> <span class="add glyphicon glyphicon-plus"></span> </div> </div>';
		$(".company-phones").append(new_item);
		$(".company-phones .item .add").off("click");
		$(this).remove();
		addPhoneInput();
		e.preventDefault();
	});		
}

function addAddressInput() {
	//Modal popups
	$(".company-address .item .map").unbind("click");
	$(".company-address .item .map").click(function(){ 
		var mapId = $(this).parents(".item").data("map-id");
		var coords = $(this).parents(".item").find(".coords").val();
		popupGoogleMap(mapId,coords); 
	});
	
	$(".company-address .item .add").click(function(e){
		var addressItem = $(this).parent();
		if (addressItem.children('input[type="text"]').val()=="") { alert("Укажите адрес"); return; }
		if (addressItem.children('input.coords').val()=="") { alert("Укажите месторасположение на карте"); return; }
		maxMapId = $(".main-side .container .field .company-address .item").length;
		var new_item = '<div class="item" data-map-id="'+(maxMapId+1).toString()+'"> <div class="inner"> <input type="hidden" name="addresses[' + maxMapId + '].id" value="0"> <input type="text" name="addresses[' + maxMapId + '].address"> <input type="hidden" name="addresses[' + maxMapId + '].coords" value=""> <span class="map selected glyphicon glyphicon-map-marker"></span> <span class="add glyphicon glyphicon-plus"></span> </div> </div>';
		$(".company-address").append(new_item);
		$(".company-address .item .add").unbind("click");
		$(this).remove();
		$("#root").append('<div class="popup-form popup-company-map" id="popup-map-'+(maxMapId+1).toString()+'"><div class="popup-close"><a href="#" class="poput-close-btn"></a></div><div class="popup-content"><div class="google-map-layout" id="google-map-'+(maxMapId+1).toString()+'"></div></div></div>');
		addAddressInput();
		e.preventDefault();
	});		
}

function popupGoogleMap(mapId,coords) {
	var modal = $('<div class="b-modal"></div>');
	$("body").append(modal);
	modal.animate({opacity: 0.6},200,function(){
		$(this).click(function(){
			$(".popup-form").animate({
				opacity: 0
			},200,function(){$(this).css("display","none");});
			$(this).animate({
				opacity: 0
			},200,function(){
				//Setting lat, lng
				if (curCompanyPosition)
						$('.company-address .item[data-map-id="'+mapId+'"]').find("input.coords").val(curCompanyPosition.lat().toString()+','+curCompanyPosition.lng().toString());
				$(this).remove();
			});
		});
	});
	$("#popup-map-"+mapId).css("display","block").animate({opacity: 1},200);
	googleMapLoad(mapId,coords);
}

var mapMarkers = [];
var curCompanyPosition = "";
function googleMapLoad(mapId,coords) {
	//finding current position
	var lat = 54.001589;
	var lng = 28.1370883;
	var zoom = 7;
	var alreadyPicked = false;

	
	// if company already picked at the map
	if (coords!="") {
		coords = coords.split(',');
		lat = coords[0];
		lng = coords[1];
		zoom = 15;
		alreadyPicked = true;
	}
    var positioning = new google.maps.LatLng(lat,lng);//(долгота, широта)
    var mapOptions = {
        zoom: zoom,
        center: positioning,
        mapTypeId: google.maps.MapTypeId.ROADMAP//задаем тип карты
    };    
    map = new google.maps.Map(document.getElementById("google-map-"+mapId), mapOptions);//инициализация карты
	mapMarkers = [];
	curCompanyPosition = "";

	if (alreadyPicked) {
		var markerPos = new google.maps.LatLng(lat,lng);
		var image = new google.maps.MarkerImage('/resources/images/map-marker.png',
				new google.maps.Size(30, 50),
	      		new google.maps.Point(0, 0),
	      		new google.maps.Point(5, 35)); //изображение маркера
		
	    var marker = new google.maps.Marker({
			position: markerPos,
	       	map: map,
	       	icon: image
	    });
		mapMarkers.push(marker);
		curCompanyPosition = marker.getPosition();
	}
    google.maps.event.addListener(map, 'click', function (event) {
        setMarker(event.latLng);
    });
}


function setMarker(location) {
	var image = new google.maps.MarkerImage('/resources/images/map-marker.png',
	  new google.maps.Size(30, 50),
      new google.maps.Point(0, 0),
      new google.maps.Point(5, 35)); //изображение маркера

	for (var i = 0; i < mapMarkers.length; i++) {
    	mapMarkers[i].setMap(null);
	}
	mapMarkers = [];
	var marker = new google.maps.Marker({
        position: location,
        map: map,
        icon: image,
        title: "",
        zIndex: 999
    });
	mapMarkers.push(marker);
	curCompanyPosition = marker.getPosition();
}


/* Functions for Company Page - Products Tab */
//-------------------------------------------------------------------
var categoriesMapIdName = []; // [id][name] pairs

//Column Categories
function refreshCategoriesEvents() {
	
	// Children category inputs resizing
	var inputNameWidth = function(){ 
		var parentLevel = $(this).parent().children(".parent").length;
		var offsetWidth = $(this).parent().children(".parent").outerWidth();
		$(this).css("width","80%");
		return $(this).width()-parentLevel*offsetWidth;
	};
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-1 .parent+input").width(inputNameWidth);
	//$(window).resize(function(){$(".catalog-columns>.column.categories .cat-list .cat-item .col-1 .parent+input").width(inputNameWidth);});
	
	//Selecting category
	$(".cat-list .cat-item .col-1 input").parent().unbind("click");
	$(".cat-list .cat-item .col-1 input:disabled").parent().click(function(){
		$(".cat-list .cat-item").removeClass("selected");
		var category = $(this).parents(".cat-item").addClass("selected");
		var categoryId = category.data("category-id");
		loadProductsByCategoryId(categoryId);
	});
	
	//Removing category
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .remove").unbind("click");
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .remove").click(function(){
		var category = $(this).parents(".cat-item");
		var categoryId = category.data("category-id");
		var categoryName = category.find(".col-1 input").val();
		if (!confirm('Удалить категорию '+categoryName+'?' )) return false;
		
		var removeResult = removeCategory(categoryId,categoryName);
		if (removeResult[0]) {alert(removeResult[1]);loadCategories();}
		else alert(removeResult[1]);
	});
	
	//Editing category
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .edit").unbind("click");
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .edit").click(function(){
		var category = $(this).parents(".cat-item");
		var categoryId = category.data("category-id");
		category.addClass("edit");
		var categoryName = category.find(".col-1 input").removeAttr("disabled").focus();
		var parentCategory = category.find(".col-2 select").removeAttr("disabled");
		category.find(".col-3").html('<span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a>');
		
		categoriesMapIdName[categoryId] = [categoryName.val(), parentCategory.val()];
		
		refreshCategoriesEvents();
	});
	
	//Add and Save category changes
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .save").unbind("click");
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .save").click(function(e){
		var category = $(this).parents(".cat-item");
		if (category.hasClass("new")) {
			var categoryName = category.find(".col-1 input").val();
			if (categoryName!="") {
				var parentCategoryId = category.find(".col-2 select").val();
				var addCategoryResult = addCategory(categoryName,parentCategoryId);
				if (addCategoryResult[0]) {alert(categoryName+" added");loadCategories();}
				else alert(addCategoryResult[1]);
			}
		}
		else if (category.hasClass("edit")) {
			var categoryId = category.data("category-id");
			var categoryName = category.find(".col-1 input").val();
			var parentCategoryId = category.find(".col-2 select").val();
			if (categoryName!="") {
				var saveResult = saveCategory(categoryId,categoryName,parentCategoryId);
				if (saveResult[0]) {alert(categoryName+" saved");loadCategories();}
				else alert(saveResult[1]);
			}
		}
		e.preventDefault();
	});
	
	//Canceling category changes
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .cancel").unbind("click");
	$(".catalog-columns>.column.categories .cat-list .cat-item .col-3 .cancel").click(function(){
		var category = $(this).parents(".cat-item");
		if (category.hasClass("new")) {
			category.remove();
		}
		else {
			var categoryId = category.data("category-id");
			category.removeClass("edit");
			category.find(".col-1 input").attr("disabled",true);
			category.find(".col-2 select").attr("disabled",true);
			category.find(".col-3").html('<span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span>');
		
			if (categoriesMapIdName[categoryId]) {
				category.find(".col-1 input").val(categoriesMapIdName[categoryId][0]);
				category.find(".col-2 select").val(categoriesMapIdName[categoryId][1]);
			}
		}
		
		refreshCategoriesEvents();
	});
	
	//Add category
	$(".catalog-columns>.column.categories .add-category .add").unbind("click");
	$(".catalog-columns>.column.categories .add-category .add").click(function(e) {
		addNewCategory();
		e.preventDefault();
	});
}

//Add category
function addCategory(categoryName,parenCategoryId) {
	//ajax actions to adding new category into general list
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/catalog/node',
		data : JSON.stringify({
			'id' : 0, 
			'name' : categoryName, 
			'parentId' : parenCategoryId, 
			'companyId' : $.urlParam("id")
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Категория "+categoryName+" успешно добавлена";
	} else {
		msg = "Невозможно добавить категорию";
	}
	return [result, msg];
}

//Remove category
function removeCategory(categoryId,categoryName) {
	//ajax request to remove category #.categoryId
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest//catalog/node',
		data : JSON.stringify({
			id : categoryId, 
			name : categoryName, 
			parentId : 0, 
			companyId : $.urlParam("id")
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Категория "+categoryName+" успешно удалена!";
	} else {
		msg = '<div class="warning">Невозможно удалить непустую категорию!</div>';
	}
	return [true, msg];
	//if doesn't possible retrieve message "<div class="warning">Невозможно удалить непустую категорию!</div>"
}

//Save category
function saveCategory(categoryId,categoryName,parentCategoryId) {
	//ajax request to saving changes category
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/catalog/node',
		data : JSON.stringify({
			id : categoryId, 
			name : categoryName, 
			parentId : parentCategoryId, 
			companyId : $.urlParam("id")
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "";
	} else {
		msg = '<div class="warning">Сбой на сервере!</div>';
	}
	return [true, msg];
	//if doesn't possible retrieve message "<div class="warning">Сбой на сервере!</div>"
	//if ok msg = ""
}

function addNewCategory() {
	//var newCategoryHtml = '<div class="cat-item new" data-category-id="0"> <div class="inner row"><div class="col-1"> <input type="text" name="category_name[]" value=""> </div> <div class="col-2"><select name="category_id[]"><option value="0" selected="">Родительская</option><option value="1">Электроинструмент</option><option value="2"> - Дрели</option><option value="3"> - Вибраторы</option><option value="4">Десерты</option></select></div><div class="col-3"><span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a></div><!-- col-3 --></div><!-- inner --></div>';
	//$(".catalog-columns>.column.categories .cat-list").append(newCategoryHtml);
	
	var categories = getCategoriesAjax();
	var parentCategory = '<div class="col-2"><select name="category_id[]"><option value="0" selected>Родительская</option>'+buildParentCategoriesTree(categories,null,0)+'</select></div>';
	var newCategoryHtml = '<div class="cat-item new" data-category-id="0"> <div class="inner row"><div class="col-1"> <input type="text" name="category_name[]" value=""> </div>'+parentCategory+'<div class="col-3"><span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a></div><!-- col-3 --></div><!-- inner --></div>';
	$(".catalog-columns>.column.categories .cat-list").append(newCategoryHtml);
	
	refreshCategoriesEvents();
}


function loadCategories() {
	var loader = $(".catalog-columns>.column.categories .layout-loader").fadeIn();
	var categoriesData = getCategoriesList();
	var categoriesList = $(".catalog-columns>.column.categories .cat-list");
	var listHeaders='<div class="headers"><div class="inner row"><div class="col-1">Название</div><div class="col-2">Родительская категория</div><div class="col-3">Действия</div></div></div>';
	categoriesList.html(listHeaders+categoriesData);
	refreshCategoriesEvents();
	loader.fadeOut();
}

function getCategoriesList() {
	var categories = getCategoriesAjax();
	var resultHtml = buildCategoriesList(categories,0,categories);
	return resultHtml;
}

function getCategoriesAjax() {
	var categoriesAjaxData = '{"categories": []}';
	$.ajax({
		url: '/arest/catalog/node/tree?id=' + $.urlParam("id"),
		async: false,
		type: 'GET',
		success: function(data) {
			categoriesAjaxData = data;
		}
	});
	var obj = jQuery.parseJSON(categoriesAjaxData);
	var categories = [];
	for(var category in obj.categories) {
		category = obj.categories[category];
		categories.push(category);
	}
	
	return categories;
}

function buildCategoriesList(categories,level,rootCategories) {
	var resHtml = '';
	for(var category in categories) {
		category = categories[category];
		var childrenOffsetSeparator='';
		for(var i=0; i<level; i++) {childrenOffsetSeparator+='<span class="parent">|—</span>';}
		resHtml+='<div class="cat-item" data-category-id="'+category.id+'">';
			resHtml+='<div class="inner row">';
            	resHtml+='<div class="col-1">'+childrenOffsetSeparator+'<input disabled type="text" name="category_name[]" value="'+category.name+'"> </div>';
				
				var selectTag='<select disabled name="category_id[]"><option value="0" '+(category.parent_id==0?"selected":"")+'>Родительская</option>';
				selectTag+=buildParentCategoriesTree(rootCategories,category,0);
				selectTag+='</select>';
				
                resHtml+='<div class="col-2">'+selectTag+'</div>';
                resHtml+='<div class="col-3"><span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span></div><!-- col-3 -->';
			resHtml+='</div><!-- inner -->';
		resHtml+='</div><!-- cat-item -->';
		
		if (category.childs)  {
			resHtml+=buildCategoriesList(category.childs,level+1,rootCategories);
		}
	}
	
	return resHtml;	
}

function buildParentCategoriesTree(rootCategories,curCat,level) {
	var resCats = '';
	for(var category in rootCategories) {
		category = rootCategories[category];
		
		childOffset = '';
		for(var i=0;i<level;i++) childOffset+=' - ';
		
		if (curCat!=null && category.id!=curCat.id) {
			resCats+='<option value="'+category.id+'"'+(curCat.parent_id==category.id?"selected":"")+'>'+childOffset+category.name+'</option>';
			
			if (category.childs)  {
				resCats+=buildParentCategoriesTree(category.childs,curCat,level+1);
			}
		}
		else if (curCat==null) {
			resCats+='<option value="'+category.id+'">'+childOffset+category.name+'</option>';
			
			if (category.childs)  {
				resCats+=buildParentCategoriesTree(category.childs,null,level+1);
			}
		}
	}
	
	return resCats;
}

function loadProductsByCategoryId(categoryId) {
	var loader = $(".catalog-columns>.column.products .layout-loader").fadeIn();
	var productsData = getProductsAjax(categoryId);
	var productsList = $(".catalog-columns>.column.products .products-list");
	$(".catalog-columns>.column.products .add-product").removeClass("hidden");
	productsList.data("category-id",categoryId);
	productsList.attr("data-category-id",categoryId);
	productsList.html(productsData);
	refreshProductsEvents();
	$(".chosen-select").chosen({max_selected_options: 1, width: "70%"});
	loader.fadeOut();
}

function getProductsAjax(categoryId) {
	var productsAjax = '{"products": [], "categories": []}';
	$.ajax({
		url: '/arest/catalog/node/items?id=' + $.urlParam("id") + '&nodeId=' + categoryId,
		async: false,
		type: 'GET',
		success: function(data) {
			productsAjax = data;
		}
	});
	var obj = jQuery.parseJSON(productsAjax);
	var products = [];
	var categories = [];
	for(var product in obj.products) {
		product = obj.products[product];
		products.push(product);
	}
	for(var category in obj.categories) {
		category = obj.categories[category];
		categories.push(category);
	}

	var resultHtml = '';
	for(var product in products) {
		product = products[product];
		resultHtml+='<div class="product-item row" data-product-id="'+product.id+'">';
			resultHtml+='<div class="display">';
				resultHtml+='<div class="col-1"><input disabled type="text" name="product_name[]" value="'+product.name+'"> </div>';
				resultHtml+='<div class="col-2"><span class="price">'+product.price+'</span> </div>';
				resultHtml+='<div class="col-3"><span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span></div>';
			resultHtml+='</div><!-- display -->';
			resultHtml+='<div class="editing hidden">';
				resultHtml+='<div class="row">';
					resultHtml+='<input type="text" name="product_name" class="name" value="'+product.name+'" placeholder="Название">';
					resultHtml+='<input type="text" name="product_price" class="price" value="'+product.price+'" placeholder="Цена">';
				resultHtml+='</div>';
				
				resultHtml+='<div class="row">';
				resultHtml+='<select name="tag_id[]" data-placeholder="Выберите тег..." class="chosen-select" multiple>';
				for(var tag in product.tags) {
					tag = product.tags[tag];
					resultHtml+=(typeof product.tag!='undefined' && product.tag==tag )?'<option selected>'+tag+'</option>':'<option>'+tag+'</option>';
				}
				resultHtml+='</select>';
				resultHtml+='</div>';

				var selectTag='<select name="category_id[]">';
				selectTag+=productCategoriesRecursive(categories,product.category_id,0);
				selectTag+='</select>';
				resultHtml+='<div class="row">'+selectTag+'</div>';
				
				resultHtml+='<div class="row"><textarea name="product_descr" class="descr" placeholder="Название">'+product.descr+'</textarea></div>';
				resultHtml+='<div class="row">';
					resultHtml+='<ul class="images row">';
						if (product.images) {
							for(var image in product.images) {
								image = product.images[image];
								resultHtml+='<li class="loaded '+(image.id==product.mainImageId?"main":"")+'" data-image-id="'+image.id+'">';
								resultHtml+='<span class="main-text">Главная</span> <a href="#" class="image"><img src="'+image.url+'"> </a>';
								resultHtml+='<a herf="#" class="delete">Удалить</a>';
								resultHtml+='</li>';
							}
						}
						resultHtml+='<li class="new"><a href="#" class="image"><span class="glyphicon glyphicon-plus"></span> </a></li>';
					resultHtml+='</ul>';
				resultHtml+='</div>';
				resultHtml+='<div class="actions"><a href="#" class="save btn-standard btn-green">Сохранить</a> <a href="#" class="cancel btn-standard">Отменить</a></div>';
			resultHtml+='</div><!-- editing -->';
		resultHtml+='</div><!-- product-item -->';
	}
	return resultHtml;
}

function productCategoriesRecursive(rootCategories,categoryId,level) {
	var resCats = '';
	for(var category in rootCategories) {
		category = rootCategories[category];
		
		childOffset = '';
		for(var i=0; i<level; i++) childOffset+=' - ';
		
		resCats+='<option value="'+category.id+'"'+(categoryId==category.id?"selected":"")+'>'+childOffset+category.name+'</option>';
		
		if (category.childs)  {
			resCats+=productCategoriesRecursive(category.childs,categoryId,level+1);
		}
	}
	
	return resCats;
}



// Column Products
var productsMap = []; // [id][name,price,category,descr]
function refreshProductsEvents() {
	//Removing product
	$(".catalog-columns>.column.products .products-list .product-item .col-3 .remove").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .col-3 .remove").click(function(){
		var product = $(this).parents(".product-item");
		var productId = product.data("product-id");
		var productName = product.find(".col-1 input").val();
		if (!confirm('Удалить '+productName+'?' )) return false;
		var removeResult = removeProduct(productId);
		if (removeResult[0]) product.remove();
		else alert(removeResult[1]);
	});	
	
	//Editing product
	$(".catalog-columns>.column.products .products-list .product-item .col-3 .edit").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .col-3 .edit").click(function(){
		var product = $(this).parents(".product-item");
		var productId = product.data("product-id");
		product.find(".display").addClass("hidden");
		var productFull = product.find(".editing").removeClass("hidden");
		productsMap[productId] = [
			productFull.find('input[name="product_name"]').val(),
			productFull.find('input[name="product_price"]').val(),
			productFull.find('select[name="category_id"]').val(),
			productFull.find('textarea[name="product_descr"]').val()
		];
	});
	
	//Canceling product changes
	$(".catalog-columns>.column.products .products-list .product-item .editing .actions .cancel").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .editing .actions .cancel").click(function(e){
		var product = $(this).parents(".product-item");
		if (product.hasClass("new"))
			product.remove();
		else {
			product.children(".editing").addClass("hidden");
			product.children(".display").removeClass("hidden");
			var productId = product.data("product-id");
			if (productsMap[productId]) {
				var productFull = product.find(".editing");
				productFull.find('input[name="product_name"]').val(productsMap[productId][0]);
				productFull.find('input[name="product_price"]').val(productsMap[productId][1]);
				productFull.find('select[name="category_id"]').val(productsMap[productId][2]);
				productFull.find('textarea[name="product_descr"]').val(productsMap[productId][3]);
			}
		}
		e.preventDefault();
	});
	
	//Selecting main image
	$(".catalog-columns>.column.products .products-list .product-item .editing ul.images li.loaded a.image").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .editing ul.images li.loaded a.image").click(function(e){
		$(this).parents(".images").children("li").removeClass("main");
		$(this).parent().addClass("main");
		e.preventDefault();
	});
	
	//Add image
	$(".catalog-columns>.column.products .products-list .product-item .editing ul.images li.new a.image").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .editing ul.images li.new a.image").click(function(e){
		var li = $(this).parent();
		li.children('input[type="file"]').remove();
		li.append('<input type="file" name="product_images[]" style="display: none;" />');
		li.children('input[type="file"]').click();	
		
		li.children('input[type="file"]').change(function(e){
			var ext = $(this).val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			    alert('Разрешенные форматы фотографий gif, png, jpg');
				$(this).remove();
				return;
			}
			var localUrl = URL.createObjectURL(e.target.files[0]);
			
			var imageHtml = '<li class="loaded" data-image-id="0"><span class="main-text">Главная</span><a href="#" class="image"><img src="'+localUrl+'"> </a><a herf="#" class="delete">Удалить</a></li>';
			li.before(imageHtml);
			li.prev().append($(this)); // Inserting <input file />
			
			refreshProductsEvents();
		});
		

		e.preventDefault();
	});
	
	//Remove image
	$(".catalog-columns>.column.products .products-list .product-item .editing ul.images li.loaded a.delete").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .editing ul.images li.loaded a.delete").click(function(e){
		var li = $(this).parents("li.loaded");
		var imageId = li.data("image-id");
		if (confirm('Удалить фото')) {
			if (imageId!=0) {
				$.ajax({
					url: '/files/images/delete',
					data: { id : imageId },
					type: 'POST',
					success: function(data) {
						alert('Ok!');
						li.remove();
					}
				});	
			} else {
				li.remove();
			}
		}
		e.preventDefault();
	});
	
	//Save product changes
	$(".catalog-columns>.column.products .products-list .product-item .editing .actions .save").unbind("click");
	$(".catalog-columns>.column.products .products-list .product-item .editing .actions .save").click(function(e){
		e.preventDefault();
		var product = $(this).parents(".product-item");
		var categoryId = product.parents(".products-list").data("category-id");
		if (product.hasClass("new"))
			addProduct(product, categoryId);
		else
			saveProduct(product);
	});
	
	//Add product
	$(".catalog-columns>.column.products .add-product .add").unbind("click");
	$(".catalog-columns>.column.products .add-product .add").click(function(e) {
		addNewProduct();
		e.preventDefault();
	});
		
}

function removeProduct(productId) {
	var success = false;
	var msg = 'невозможно удалить товар';
	$.ajax({
		url: '/arest/catalog/node/items/item/delete',
		async: false,
		data: { id : productId },
		type: 'POST',
		success: function(data) {
			success = true;
			msg = 'Товар успешно удален!';
		}
	});
	return [success,msg];
}

function addProduct(productItem, categoryId) {
	var productName = productItem.find('input[name="product_name"]').val();
	var productPrice = productItem.find('input[name="product_price"]').val();
	if (productName!="" && productPrice!="") {
		var productDescr = productItem.find('textarea[name="product_descr"]').val();
		
		var formData = new FormData();
		formData.append('product_id', 0);
		formData.append('product_name', productName);
		formData.append('product_price', productPrice);
		formData.append('category_id', categoryId);
		formData.append('product_descr', productDescr);
		formData.append('fileType', 'PRODUCT_IMAGE');
		
		var images = productItem.find('ul.images li.loaded input[type="file"]');
		if (images.length!=0) 
		{
			var mainImage = productItem.find('ul.images li.loaded.main');
			if (mainImage.length!=0) {
				mainImageFile = mainImage.find('input[type="file"]')[0];
				mainImageFile = mainImageFile.files[0];
				alert("Установлено главное фото "+mainImageFile.name);
				formData.append('main_image', mainImageFile, mainImageFile.name);
			}
			
			for(var i=0; i<images.length; i++) {
				var file = images[i].files[0];
				
  				if (!file.type.match('image.*')) {
					continue;
				}
				
				alert("Добавлена новая фотка "+file.name);
				if (mainImage.length!=0) {
					if (mainImage.find('input[type="file"]')[0].files[0]!=file) 
  						formData.append('product_images[]', file, file.name);
				}
				else 
					formData.append('product_images[]', file, file.name);
			}
		}
		$.ajax({
			url: '/arest/catalog/node/items/item',
			async: false,
			data: formData,
			processData: false,
			contentType: false,
			type: 'POST',
			success: function(data) {
				alert('Ok!');
			}
		});
			
		loadProductsByCategoryId(categoryId);
	}
	else alert("Заполните имя и цену товара");
}

function saveProduct(productItem) {
	var productName = productItem.find('.editing input[name="product_name"]').val();
	var productPrice = productItem.find('.editing input[name="product_price"]').val();
	var productId = productItem.data("product-id");
	if (productName!="" && productPrice!="") {
		var categoryId = productItem.find('.editing select[name="category_id[]"]').val();
		var productDescr = productItem.find('textarea[name="product_descr"]').val();
		
		var formData = new FormData();
		formData.append('product_id', productId);
		formData.append('product_name', productName);
		formData.append('product_price', productPrice);
		formData.append('category_id', categoryId);
		formData.append('product_descr', productDescr);
		formData.append('fileType', 'PRODUCT_IMAGE');
		
		var mainImage = productItem.find('ul.images li.loaded.main');
		if (mainImage.length!=0) 
			if (mainImage.data("image-id")!=0) {
				alert("Установлено главное фото из имеющегося, id: "+mainImage.data("image-id"));
				formData.append('main_image_id', mainImage.data("image-id"));
			}
			else {
				mainImageFile = mainImage.find('input[type="file"]')[0];
				mainImageFile = mainImageFile.files[0];
				alert("Установлено новое фото "+mainImageFile.name);
				formData.append('main_image', mainImageFile, mainImageFile.name);
			}
			
		var images = productItem.find('ul.images li.loaded input[type="file"]');
		if (images.length!=0) 
		{
			
			for(var i=0; i<images.length; i++) {
				var file = images[i].files[0];
				
  				if (!file.type.match('image.*')) {
					continue;
				}
				alert("Добавлена новая фотка "+file.name);
				
				if (mainImage.length!=0 && mainImage.find('input[type="file"]').length>0) {
					if (mainImage.find('input[type="file"]')[0].files[0]!=file) 
  						formData.append('product_images[]', file, file.name);
				}
				else 
					formData.append('product_images[]', file, file.name);

			}
		}
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'saveProductController', true);
		$.ajax({
			url: '/arest/catalog/node/items/item',
			async: false,
			data: formData,
			processData: false,
			contentType: false,
			type: 'POST',
			success: function(data) {
				alert('Ok!');
			}
		});
			
		loadProductsByCategoryId(categoryId);
	}
	else alert("Заполните имя и цену товара");
}

function addNewProduct() {
	var newProductHtml = '<div class="product-item new row" data-product-id="0"><div class="editing"><div class="row"><input type="text" name="product_name" class="name" placeholder="Название.."><input type="text" name="product_price" class="price" placeholder="Цена..."></div><div class="row"><textarea name="product_descr" class="descr" placeholder="Описание товара или услуги..."></textarea></div><div class="row"><ul class="images row"><li class="new"><a href="#" class="image"><span class="glyphicon glyphicon-plus"></span> </a></li></ul></div><div class="actions"><a href="#" class="save btn-standard btn-green">Сохранить</a> <a href="#" class="cancel btn-standard">Отменить</a></div></div></div>';
	$(".catalog-columns>.column.products .products-list").append(newProductHtml);
	
	refreshProductsEvents();
}



//Functions for Company Page - Galery Tab 
//-------------------------------------------------------------------
Company.saveGallery = function () {
	$(".main-side form.main-form").submit();
};

function refreshGalleryEvents() {
	//Add image
	$(".company-gallery ul.images li.new .image").unbind("click");
	$(".company-gallery ul.images li.new .image").click(function(e){
		var li = $(this).parent();
		li.children('input[type="file"]').remove();
		li.append('<input type="file" name="gallery_images[]" style="display: none;" />');
		li.children('input[type="file"]').click();	
		
		li.children('input[type="file"]').change(function(e){
			var ext = $(this).val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			    alert('Разрешенные форматы фотографий gif, png, jpg');
				$(this).remove();
				return;
			}
			uploadCompanyImage(this, function (data) {
				var imageUrl = '/files/' + data.imageType + '/' + $('.main-form').attr('data-company-id') + '/' + data.fileUUID + '.' + data.extension;
				var imageHtml = '<li class="loaded" data-image-id="' + data.id + '"><a href="' + imageUrl + '" class="image"><img src="'+imageUrl+'"> </a><a herf="#" class="delete">Удалить</a></li>';
				li.before(imageHtml);
				li.prev().append($(this)); // Inserting <input file />
				refreshGalleryEvents();
			});
		});
		
		e.preventDefault();
	});
	
	//Remove image
	$(".company-gallery ul.images li.loaded a.delete").unbind("click");
	$(".company-gallery ul.images li.loaded a.delete").click(function(e){
		var li = $(this).parents("li.loaded");
		var imageId = li.data("image-id");
		if (confirm('Удалить изображение?')) {
			if (imageId!=0) {
				$.ajax({
					url: '/files/images/delete',
					data: { id : imageId },
					type: 'POST',
					success: function(success){
						if (success) {
							li.remove();	
						}
					}
				});
			}
		}
		e.preventDefault();
	});

}

function uploadCompanyImage(hiddenInput, uploadCallback){
	var formData = new FormData();
	var file = hiddenInput.files[0];
	
	if (!file.type.match('image.*')) {
		exit;
	}
	
	formData.append('entityId', $.urlParam('id'));
	formData.append('fileType', 'COMPANY_IMAGE');
	formData.append('company_image', file, file.name);
	
	$.ajax({
		url: '/files/images/upload',
		data: formData,
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			uploadCallback(data);
		}
	});
}

/* Functions for Company Page - Galery Tab ENDS */
//-------------------------------------------------------------------



/* Functions for Company Page - Reviews Tab */
//-------------------------------------------------------------------
$(document).ready(function(){
						   /*
	$(".reviews-table table input.checkall").click(function(){
		if ($(this).is(':checked')) {
			$('.reviews-table table tr input[type="checkbox"]').prop("checked", true);
			$(".reviews-table .actions").removeClass("hidden");
		}
		else {
			$('.reviews-table table tr input[type="checkbox"]').prop("checked", false);
			$(".reviews-table .actions").addClass("hidden");
		}
	});*/
	
	$(".reviews-table .actions select").change(function(){
		$("form.main-form").submit();
	});
	
});
/* Functions for Company Page - Reviews Tab ENDS */
//-------------------------------------------------------------------