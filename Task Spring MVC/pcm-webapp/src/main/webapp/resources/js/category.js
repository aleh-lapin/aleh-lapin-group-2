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
	refreshCategoryPageEvents();
});

/* Functions for Category Page */
//-------------------------------------------------------------------
var Category={};

Category.save = function() {
	$('#category').submit();
};

function refreshCategoryPageEvents() {
	if ( $(".main-side .container ul.banners.main .loaded").length>0 ) {
		$(".main-side .container ul.banners.main li.new").hide();
	}
	
	//Add image
	$(".main-side .container ul.banners li.new a.image").unbind("click");
	$(".main-side .container ul.banners li.new a.image").click(function(e){
		var li = $(this).parent();
		li.children('input[type="file"]').remove();
		li.append('<input type="file" name="category_banners[]" style="display: none;" />');
		li.children('input[type="file"]').click();	
		
		li.children('input[type="file"]').change(function(e){
			var ext = $(this).val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			    alert('Разрешенные форматы фотографий gif, png, jpg');
				$(this).remove();
				return;
			}
			var mediaType = "CATEGORY_BANNER";
		    if ($(this).parents("ul.banners").hasClass("main")) mediaType = "CATEGORY_LOGO";
			uploadCategoryBanner(this, mediaType, function (data) {
				var imageUrl = '/files/' + data.imageType + '/' + $.urlParam('id') + '/' + data.fileUUID + '.' + data.extension;
				var imageHtml = '<li class="loaded" data-banner-id="' + data.id + '"><a href="' + imageUrl + '" class="image"><img src="'+imageUrl+'"> </a><a herf="#" class="delete">Удалить</a></li>';
				li.before(imageHtml);
				li.prev().append($(this)); // Inserting <input file />
				refreshCategoryPageEvents();
			});
		});
		e.preventDefault();
	});
	
	//Remove image
	$(".main-side .container ul.banners li.loaded a.delete").unbind("click");
	$(".main-side .container ul.banners li.loaded a.delete").click(function(e){
		var li = $(this).parents("li.loaded");
		var bannerId = li.data("banner-id");
		if (confirm('Удалить фото')) {
			if (bannerId!=0) {
				$.ajax({
					url: '/files/images/delete',
					data: { id : bannerId },
					type: 'POST',
					success: function(success){
						if (success) {
							if ( $(this).parents("ul.banners").hasClass("main") ) {
								$(".main-side .container ul.banners.main li.new").show();	
							}
							li.remove();	
						}
					}
				});
			}
		}
		e.preventDefault();
	});
}
		
function uploadCategoryBanner(hiddenInput, mediaType, uploadCallback){
	var formData = new FormData();
	var file = hiddenInput.files[0];
	
	if (!file.type.match('image.*')) {
		exit;
	}
	
	formData.append('entityId', $.urlParam("id"));
	formData.append('fileType', mediaType);
	formData.append('category_banner', file, file.name);
	
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