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

// Filters Page
$(document).ready(function(){

	/* Groups Tab Starts ---------------------------------------- */
	if (window.location.pathname == '/filters/groups') {
		loadGroups();
		refreshGroupsEvents();
		//Add new group
		$(".filters-groups>.column.groups .add-group .add").click(function(e) {
			addNewGroup();
			e.preventDefault();
		});
	}
	/* Groups Tab Ends ---------------------------------------- */
	
	
	/* Groups Tab Starts ---------------------------------------- */
	if (window.location.pathname == '/filters/filter') {
		if ($.urlParam('id')) {
			loadAttributes();
			refreshAttributesEvents();
			
			//Add new attribute
			$(".filter-attributes>.column.attributes .add-attribute .add").click(function(e) {
				addNewAttribute();
				e.preventDefault();
			});
		}
	}
	/* Groups Tab Ends ---------------------------------------- */
	
});




/* Functions for Filters Page - Groups Tab */
//-------------------------------------------------------------------
function ajaxLoadFilters() {
	var ajaxData = [];
	$.ajax({
		url: '/arest/filters',
		async: false,
		type: 'GET',
		success: function(data){
			data = jQuery.parseJSON(data);
			for(var i in data.filters) {
				var filter = data.filters[i];
				var res = {};
				res['check'] = '<input type="checkbox" name="cid[]" value="' + filter.id + '" />';
				res['name'] = '<a href="/filters/filter?id=' + filter.id +'" class="item-link">' + filter.name + '</a>';
				res['group'] = filter.groupName;
				res['state'] = '<div class="'+filter.state+'"></div>';
				res['id'] = filter.id;
				ajaxData.push(res);
			}
		}
	});
	return ajaxData;
};

var groupsMapIdName = []; // [id][name] pairs

//Column Categories
function refreshGroupsEvents() {
	
	//Remove group
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .remove").unbind("click");
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .remove").click(function(){
		var group = $(this).parents(".group-item");
		var groupId = group.data("group-id");
		var groupName = group.find(".col-1 input").val();
		if (!confirm('Удалить группу фильтров '+groupName+'?' )) return false;
		
		var removeResult = removeGroup(groupId,groupName);
		if (removeResult[0]) {alert(removeResult[1]);loadGroups();}
		else alert(removeResult[1]);
	});
	
	//Editing group
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .edit").unbind("click");
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .edit").click(function(){
		var group = $(this).parents(".group-item");
		var groupId = group.data("group-id");
		group.addClass("edit");
		var groupName = group.find(".col-1 input").removeAttr("disabled").focus();
		group.find(".col-3").html('<span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a>');
		
		groupsMapIdName[groupId] = groupName.val();
		
		refreshGroupsEvents();
	});
	
	//Add and Save group changes
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .save").unbind("click");
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .save").click(function(e){
		var group = $(this).parents(".group-item");
		if (group.hasClass("new")) {
			var groupName = group.find(".col-1 input").val();
			if (groupName!="") {
				var addGroupResult = addGroup(groupName);
				if (addGroupResult[0]) {alert(groupName+" added");loadGroups();}
				else alert(addGroupResult[1]);
			}
		}
		else if (group.hasClass("edit")) {
			var groupId = group.data("group-id");
			var groupName = group.find(".col-1 input").val();
			if (groupName!="") {
				var saveResult = saveGroup(groupId,groupName);
				if (saveResult[0]) {alert(groupName+" saved");loadGroups();}
				else alert(saveResult[1]);
			}
		}
		e.preventDefault();
	});
	
	//Cancel group changes
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .cancel").unbind("click");
	$(".filters-groups>.column.groups .groups-list .group-item .col-3 .cancel").click(function(){
		var group = $(this).parents(".group-item");
		if (group.hasClass("new")) {
			group.remove();
		}
		else {
			var groupId = group.data("group-id");
			group.removeClass("edit");
			var groupName = group.find(".col-1 input").attr("disabled",true);
			group.find(".col-3").html('<span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span>');
		
			if (groupsMapIdName[groupId]) {
				group.find(".col-1 input").val(groupsMapIdName[groupId]);
			}
		}
		
		refreshGroupsEvents();
	});
	

}

//Add category
function addGroup(groupName) {
	//ajax actions to adding new category into general list
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/filters/groups/group',
		data : JSON.stringify({
			'id' : 0, 
			'name' : groupName
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Группа " + groupName + " успешно добавлена";
	} else {
		msg = "Невозможно добавить группу фильтров";
	}
	return [result, msg];
	//if error return [false,"невозможно добавить категорию"];
}

//Remove category
function removeGroup(groupId,groupName) {
	//ajax request to remove group #.categoryId
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/filters/groups/group/delete',
		data : { id : groupId },
		async: false,
		type: 'POST',
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Группа "+groupName+" успешно удалена!";
	} else {
		msg = '<div class="warning">Невозможно удалить непустую категорию!</div>';
	}
	return [result,msg];
}

//Save category
function saveGroup(groupId,groupName) {
	//ajax request to saving changes category
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/filters/groups/group',
		data : JSON.stringify({
			'id' : groupId, 
			'name' : groupName
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Группа " + groupName + " успешно изменена";
	} else {
		msg = "Невозможно изменить группу фильтров";
	}
	//if doesn't possible retrieve message "<div class="warning">Сбой на сервере!</div>"
	//if ok msg = ""
	return [true,msg];
}

function addNewGroup() {
	var newGroupHtml = '<div class="group-item new" data-group-id="0"><div class="inner row"><div class="col-1"> <input type="text" name="group_name[]" value="" /> </div><div class="col-2"> </div><div class="col-3"><span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a></div><!-- col-3 --></div><!-- inner --></div>'
	$(".filters-groups>.column.groups .groups-list").append(newGroupHtml);
	
	refreshGroupsEvents();
}


function loadGroups() {
	var loader = $(".filters-groups>.column.groups .layout-loader").fadeIn();
	var groupsData = getGroupsAjax();
	var groupsList = $(".filters-groups>.column.groups .groups-list");
	var listHeaders='<div class="headers"><div class="inner row"><div class="col-1">Название</div><div class="col-2">Фильтров</div><div class="col-3">Действия</div></div></div>';
	groupsList.html(listHeaders+groupsData);
	refreshGroupsEvents();
	loader.fadeOut();
}

function getGroupsAjax() {
	var groupsAjaxData = '{"groups": []}';
	$.ajax({
		url: '/arest/filters/groups',
		async: false,
		type: 'GET',
		success: function(data) {
			groupsAjaxData = data;
		}
	});
	var obj = jQuery.parseJSON(groupsAjaxData);
	var groups = [];
	for(var group in obj.groups) {
		group = obj.groups[group];
		groups.push(group);
	}
	
	var resultHtml = '';
	for(var group in groups) {
		group = groups[group];
		resultHtml+='<div class="group-item" data-group-id="'+group.id+'">';
			resultHtml+='<div class="inner row">';
            	resultHtml+='<div class="col-1"><input disabled type="text" name="group_name[]" value="'+group.name+'"> </div>';
                resultHtml+='<div class="col-2">'+group.filters+'</div>';
                resultHtml+='<div class="col-3"><span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span></div><!-- col-3 -->';
			resultHtml+='</div><!-- inner -->';
		resultHtml+='</div><!-- group-item -->';
	}
	
	return resultHtml;
}
/* Functions for Filters Page - Groups Tab ENDS */
//-------------------------------------------------------------------





/* Functions for Filter Page  */
//-------------------------------------------------------------------
var Filter = {};
Filter.save = function() {
	$("#filter").submit();
}

var attributesMapIdName = []; // [id][name] pairs

//Column Attributes
function refreshAttributesEvents() {
	
	//Remove attribute
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .remove").unbind("click");
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .remove").click(function(){
		var attribute = $(this).parents(".attribute-item");
		var attributeId = attribute.data("attribute-id");
		var attributeName = attribute.find(".col-1 input").val();
		if (!confirm('Удалить группу фильтров '+attributeName+'?' )) return false;
		
		var removeResult = removeAttribute(attributeId,attributeName);
		if (removeResult[0]) {alert(removeResult[1]);loadAttributes();}
		else alert(removeResult[1]);
	});
	
	//Editing attribute
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .edit").unbind("click");
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .edit").click(function(){
		var attribute = $(this).parents(".attribute-item");
		var attributeId = attribute.data("attribute-id");
		attribute.addClass("edit");
		var attributeName = attribute.find(".col-1 input").removeAttr("disabled").focus();
		attribute.find(".col-3").html('<span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a>');
		
		attributesMapIdName[attributeId] = attributeName.val();
		
		refreshAttributesEvents();
	});
	
	//Add and Save attribute changes
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .save").unbind("click");
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .save").click(function(e){
		var attribute = $(this).parents(".attribute-item");
		if (attribute.hasClass("new")) {
			var attributeName = attribute.find(".col-1 input").val();
			if (attributeName!="") {
				var addAttributeResult = addAttribute(attributeName);
				if (addAttributeResult[0]) {alert(attributeName+" added");loadAttributes();}
				else alert(addAttributeResult[1]);
			}
		}
		else if (attribute.hasClass("edit")) {
			var attributeId = attribute.data("attribute-id");
			var attributeName = attribute.find(".col-1 input").val();
			if (attributeName!="") {
				var saveResult = saveAttribute(attributeId,attributeName);
				if (saveResult[0]) {alert(attributeName+" saved");loadAttributes();}
				else alert(saveResult[1]);
			}
		}
		e.preventDefault();
	});
	
	//Cancel attribute changes
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .cancel").unbind("click");
	$(".filter-attributes>.column.attributes .attributes-list .attribute-item .col-3 .cancel").click(function(){
		var attribute = $(this).parents(".attribute-item");
		if (attribute.hasClass("new")) {
			attribute.remove();
		}
		else {
			var attributeId = attribute.data("attribute-id");
			attribute.removeClass("edit");
			var attributeName = attribute.find(".col-1 input").attr("disabled",true);
			attribute.find(".col-3").html('<span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span>');
		
			if (attributesMapIdName[attributeId]) {
				attribute.find(".col-1 input").val(attributesMapIdName[attributeId]);
			}
		}
		
		refreshAttributesEvents();
	});
	

}

//Add category
function addAttribute(attributeName) {
	//ajax actions to adding new category into general list
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/filters/filter/attribute',
		data : JSON.stringify({
			'id' : 0, 
			'name' : attributeName,
			'filterId' : $.urlParam('id')
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = 'Атрибут ' +attributeName + ' успешно добавлена';
	} else {
		msg = 'Невозможно добавить атрибут';
	}
	return [result,msg];
}

//Remove category
function removeAttribute(attributeId,attributeName) {
	//ajax request to remove attribute #.categoryId
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/filters/filter/attribute/delete',
		data : { id : attributeId },
		async: false,
		type: 'POST',
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = "Атрибут ' + attributeName + ' успешно удален!";
	} else {
		msg = '<div class="warning">Невозможно удалить атрибут!</div>';
	}
	return [result,msg];
}

//Save category
function saveAttribute(attributeId,attributeName) {
	//ajax request to saving changes category
	var result = false;
	var msg = "";
	$.ajax({
		url: '/arest/filters/filter/attribute',
		data : JSON.stringify({
			'id' : attributeId, 
			'name' : attributeName,
			'filterId' : $.urlParam('id')
			}),
		async: false,
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			result = data;
		}
	});
	if (result) {
		msg = 'Атрибут ' + attributeName + ' успешно сохранен';
	} else {
		msg = 'Невозможно сохранить атрибут';
	}
	return [result,msg];
}

function addNewAttribute() {
	var newAttributeHtml = '<div class="attribute-item new" data-attribute-id="0"><div class="inner row"><div class="col-1"> <input type="text" name="attribute_name[]" value="" /> </div><div class="col-2"> </div><div class="col-3"><span class="cancel glyphicon glyphicon-remove"></span><a href="#" class="save btn-standard btn-green">OK</a></div><!-- col-3 --></div><!-- inner --></div>'
	$(".filter-attributes>.column.attributes .attributes-list").append(newAttributeHtml);
	
	refreshAttributesEvents();
}


function loadAttributes() {
	var loader = $(".filter-attributes>.column.attributes .layout-loader").fadeIn();
	var attributesData = getAttributesAjax();
	var attributesList = $(".filter-attributes>.column.attributes .attributes-list");
	var listHeaders='<div class="headers"><div class="inner row"><div class="col-1">Название</div><div class="col-2"> </div><div class="col-3">Действия</div></div></div>';
	attributesList.html(listHeaders+attributesData);
	refreshAttributesEvents();
	loader.fadeOut();
}

function getAttributesAjax() {
	var attributesAjaxData = '{"attributes": []}';
	$.ajax({
		url: '/arest/filters/filter/attributes?filterId=' + $.urlParam('id'),
		async: false,
		type: 'GET',
		success: function(data) {
			attributesAjaxData = data;
		}
	});
	var obj = jQuery.parseJSON(attributesAjaxData);
	var attributes = [];
	for(var attribute in obj.attributes) {
		attribute = obj.attributes[attribute];
		attributes.push(attribute);
	}
	
	var resultHtml = '';
	for(var attribute in attributes) {
		attribute = attributes[attribute];
		resultHtml+='<div class="attribute-item" data-attribute-id="'+attribute.id+'">';
			resultHtml+='<div class="inner row">'
            	resultHtml+='<div class="col-1"><input disabled type="text" name="attribute_name[]" value="'+attribute.name+'"> </div>'
                resultHtml+='<div class="col-2"> </div>';
                resultHtml+='<div class="col-3"><span class="edit glyphicon glyphicon-pencil"></span><span class="remove glyphicon glyphicon-trash"></span></div><!-- col-3 -->';
			resultHtml+='</div><!-- inner -->';
		resultHtml+='</div><!-- attribute-item -->';
	}
	
	return resultHtml;
}
/* Functions for Filter Page  ENDS */
//-------------------------------------------------------------------