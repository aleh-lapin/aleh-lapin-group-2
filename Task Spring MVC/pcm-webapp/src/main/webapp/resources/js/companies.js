// Filters Page
$(document).ready(function(){

	/* Companies list ---------------------------------------- */
	
});




/* Functions for Comapnies List Page */
//-------------------------------------------------------------------

function ajaxLoadCompanies() {
// /admin/companies/company?id=?
	
	var ajaxData = [];
	$.ajax({
		url: '/arest/companies',
		async: false,
		type: 'GET',
		success: function(data){
			for(var i in data) {
				var company = data[i];
				var res = {};
				res['check'] = '<input type="checkbox" class="check-company" name="cid[]" value="'+company.id+'" />';
				res['name'] = '<a href="/companies/company?id='+company.id+'" class="item-link">'+company.name+'</a> <a href="'+company.url+'" class="company-url" target="_blank">(link)</a>';
				res['category'] = company.categoryName;
				res['user'] = '<a href="/users?id='+company.userId+'" class="company-user">'+company.userNickname+'</a>';
				res['phone'] = company.userPhone;
				res['state'] = '<div class="'+company.state+'"></div>';
				res['package'] = company.packageType;
				res['pkg_term'] = company.packageTerm;
				res['id'] = company.id;
				ajaxData.push(res);
			}
		}
	});
	return ajaxData;
}

/* Functions for Comapnies List Page ENDS */
//-------------------------------------------------------------------