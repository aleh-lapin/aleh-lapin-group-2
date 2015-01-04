var User = {};
User.save = function () {
	$('form#userForm').submit();
}

function ajaxLoadUsers(isCount, role) {
	var ajaxData = [];
	$.ajax({
		url: '/arest/users?role=' + role,
		async: false,
		type: 'GET',
		success: function(data){
			data = jQuery.parseJSON(data);
			for(var i in data.users) {
				var user = data.users[i];
				var res = {};
				res['check'] = '<input type="checkbox" class="check-company" name="cid[]" value="'+user.id+'" />';
				res['name'] = '<img src="' + user.profileBanner + '" class="profile-image" /> <a href="/users/user?id=' + user.id + '" class="item-link">' + user.name + '</a>';
				if ("true" === isCount) {
					res['companies'] = '<a href="/companies?filter=yes&user_id=' + user.id + '" class="item-link">' + user.companiesCount + '</a>';
				}
				res['email'] = user.email;
				res['state'] = '<div class="'+ user.state + '"></div>';
				res['id'] = user.id;
				ajaxData.push(res);
			}
		}
	});
	return ajaxData;
}

// Organization's user, managers and users pages
$(document).ready(function(){

	/* Organization's user ---------------------------------------- */
	processPassword();
	
});




/* Functions for User Organization Page */
//-------------------------------------------------------------------

function processPassword() {
	$('.field.password input[name="user_password"]').keyup(function() {
		var confirmDiv = $(this).next();
		if ($(this).val().length>3) {
			confirmDiv.css("display","inline-block");
		}
		//$(this).parent().children(".btn-standard").show();
	});
	$('.field.password .confirm .cancel').click(function(e) {
		var field = $(this).parents(".field");
		field.find("input").val("");
		field.children(".confirm").hide();
		e.preventDefault();
	});
	$('.field.password .confirm .save').click(function(e) {
		var password = $(this).parents(".field").find("input");
		if (password[0].value!=password[1].value) {alert("Пароли не совпадают"); return; }
		if (!(password[0].value.length>7 && password[1].value.length>7)) {alert("Длина пароля должна быть больше 7 символов"); return; }

		$('.field.password .confirm .cancel').click();
		field.find("input").val(password[0].value);
		
		e.preventDefault();
	});
}

/* Functions for User Organization Page ENDS */
//-------------------------------------------------------------------