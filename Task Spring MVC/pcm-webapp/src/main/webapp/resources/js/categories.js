/* Functions for Categories List Page */
//-------------------------------------------------------------------

function ajaxLoadCategories() {
	var ajaxData = [];
	$.ajax({
		url: '/arest/categories',
		async: false,
		type: 'GET',
		success: function(data){
			for(var i in data) {
				var category = data[i];
				var res = {};
				res['check'] = '<input type="checkbox" name="cid[]" value="'+category.id+'" />';
				var padding = '';
				for (var j = 1; j < category.level; j++) {
				 padding += '<span class="parent">| - </span>';
				}
				res['name'] = padding + '<a href="/categories/category?id='+category.id+'" class="item-link">'+category.name+'</a>';
				res['state'] = '<div class="state-icon ' + (category.active ? 'published' : 'unpublished') + '"></div>';
				res['id'] = category.id;
				ajaxData.push(res);
			}
		}
	});
	return ajaxData;
};

/* Functions for Categories List Page ENDS */
//-------------------------------------------------------------------