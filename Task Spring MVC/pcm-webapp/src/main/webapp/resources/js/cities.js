// Cities Page
$(document).ready(function(){

	loadCities();
	refreshCitiesEvents();
	
	//Add new city
	$(".cities>.column .add-city .add").click(function(e) {
		addNewCity();
		e.preventDefault();
	});

});



/* Functions for Cities Page  */
//-------------------------------------------------------------------
var citiesMapIdName = []; // [id][{name, regionId}] pairs

//Column Cities
function refreshCitiesEvents() {
	
	//Remove city
	$(".cities>.column .cities-list .city-item .col-3 .remove").unbind("click");
	$(".cities>.column .cities-list .city-item .col-3 .remove").click(function(){
		var city = $(this).parents(".city-item");
		var cityId = city.data("city-id");
		var cityName = city.find(".col-1 input").val();
		if (!confirm('Удалить город '+cityName+'?' )) return false;
		
		var removeResult = removeCity(cityId,cityName);
		if (removeResult[0]) {alert(removeResult[1]);loadCities();}
		else alert(removeResult[1]);
	});
	
	//Editing city
	$(".cities>.column .cities-list .city-item .col-3 .edit").unbind("click");
	$(".cities>.column .cities-list .city-item .col-3 .edit").click(function(){
		var city = $(this).parents(".city-item");
		var cityId = city.data("city-id");
		city.addClass("edit");
		var cityName = city.find(".col-1 input").removeAttr("disabled").focus();
		var region = city.find(".col-2 select").removeAttr("disabled");
		city.find(".col-3").html('<span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a>');
		citiesMapIdName[cityId] = {};
		citiesMapIdName[cityId].name = cityName.val();
		citiesMapIdName[cityId].region = region.val();
		refreshCitiesEvents();
	});
	
	//Add and Save city changes
	$(".cities>.column .cities-list .city-item .col-3 .save").unbind("click");
	$(".cities>.column .cities-list .city-item .col-3 .save").click(function(e){
		var city = $(this).parents(".city-item");
		if (city.hasClass("new")) {
			var cityName = city.find(".col-1 input").val();
			var regionId = city.find(".col-2 select").val();
			if (cityName!="") {
				var addCityResult = addCity(cityName, regionId);
				if (addCityResult[0]) {alert(cityName+" added");loadCities();}
				else alert(addCityResult[1]);
			}
		}
		else if (city.hasClass("edit")) {
			var cityId = city.data("city-id");
			var cityName = city.find(".col-1 input").val();
			var regionId = city.find(".col-2 select").val();
			if (cityName!="") {
				var saveResult = saveCity(cityId, cityName, regionId);
				if (saveResult[0]) {alert(cityName+" saved");loadCities();}
				else alert(saveResult[1]);
			}
		}
		e.preventDefault();
	});
	
	//Cancel city changes
	$(".cities>.column .cities-list .city-item .col-3 .cancel").unbind("click");
	$(".cities>.column .cities-list .city-item .col-3 .cancel").click(function(){
		var city = $(this).parents(".city-item");
		if (city.hasClass("new")) {
			city.remove();
		}
		else {
			var cityId = city.data("city-id");
			city.removeClass("edit");
			var cityName = city.find(".col-1 input").attr("disabled",true);
			city.find(".col-2 select").attr("disabled", true);
			city.find(".col-3").html('<span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span>');
		
			if (citiesMapIdName[cityId]) {
				city.find(".col-1 input").val(citiesMapIdName[cityId].name);
				city.find(".col-2 select").val(citiesMapIdName[cityId].region);
			}
		}
		
		refreshCitiesEvents();
	});
	

}

//Add category
function addCity(cityName, regionId) {
	//ajax actions to adding new category into general list
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/cities/city',
		data : JSON.stringify({
			'id' : 0, 
			'name' : cityName,
			'regionId' : regionId
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = 'Город ' + cityName + ' успешно добавлен';
	} else {
		msg = 'Невозможно добавить город';
	}
	return [result,msg];
	//if error return [false,"невозможно добавить категорию"];
}

//Remove category
function removeCity(cityId,cityName) {
	//ajax request to remove city #.categoryId
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/cities/city/delete',
		data : { id : cityId },
		async: false,
		type: 'POST',
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Город " + cityName + " успешно удален!";
	} else {
		msg = '<div class="warning">Невозможно удалить город!</div>';
	}
	return [result,msg];
}

//Save category
function saveCity(cityId,cityName,regionId) {
	//ajax request to saving changes category
	//if doesn't possible retrieve message "<div class="warning">Сбой на сервере!</div>"
	//if ok msg = ""
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/cities/city',
		data : JSON.stringify({
			'id' : cityId, 
			'name' : cityName,
			'regionId' : regionId
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = 'Город ' + cityName + ' успешно сохранен';
	} else {
		msg = 'Невозможно сохранить город';
	}
	return [result,msg];
}

function addNewCity() {
	var newCityHtml = '<div class="city-item new" data-city-id="0"><div class="inner row"><div class="col-1"> <input type="text" name="city_name[]" value="" /> </div><div class="col-2"> ' + getRegionsSelect() + '</div><div class="col-3"><span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a></div><!-- col-3 --></div><!-- inner --></div>'
	$(".cities>.column .cities-list").append(newCityHtml);
	
	refreshCitiesEvents();
}


function loadCities() {
	var loader = $(".cities>.column .layout-loader").fadeIn();
	var citiesData = getCitiesAjax();
	var citiesList = $(".cities>.column .cities-list");
	var listHeaders='<div class="headers"><div class="inner row"><div class="col-1">Название</div><div class="col-2">Регионы</div><div class="col-3">Действия</div></div></div>';
	citiesList.html(listHeaders+citiesData);
	refreshCitiesEvents();
	loader.fadeOut();
}

var regions = {
		"-1" : "Минская область",
		"-2" : "Гомельская область", 
		"-3" : "Бресткая область",
		"-4" : "Могилевская область", 
		"-5" : "Витебская область",
		"-6" : "Гродненская область"
};

function getRegionsSelect(regionId) {
	var result = '<select ' + (regionId?'disabled':'') + ' name="city_region[]">';
	for (var id in regions) {
		var regionName = regions[id];
		result += '<option value="' + id + '"' + ((regionId + '' === id)?'selected="true">':'>') + regionName + '</option>';
	}
	result += '</select>';
	return result;
}

function getCitiesAjax() {
	var citiesAjaxData = '{"cities": []}';
	$.ajax({
		url: '/arest/cities',
		async: false,
		type: 'GET',
		success: function(data) {
			citiesAjaxData = data;
		}
	});
	var obj = jQuery.parseJSON(citiesAjaxData);
	var cities = [];
	for(var city in obj.cities) {
		city = obj.cities[city];
		cities.push(city);
	}
	
	var resultHtml = '';
	for(var city in cities) {
		city = cities[city];
		resultHtml+='<div class="city-item" data-city-id="'+city.id+'">';
			resultHtml+='<div class="inner row">'
            	resultHtml+='<div class="col-1"><input disabled type="text" name="city_name[]" value="'+city.name+'"> </div>'
                resultHtml+='<div class="col-2">' + getRegionsSelect(city.regionId) + '</div>';
                resultHtml+='<div class="col-3"><span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span></div><!-- col-3 -->';
			resultHtml+='</div><!-- inner -->';
		resultHtml+='</div><!-- city-item -->';
	}
	
	return resultHtml;
}
/* Functions for Filter Page  ENDS */
//-------------------------------------------------------------------