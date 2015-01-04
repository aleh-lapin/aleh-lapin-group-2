package by.pochem.web.controllers.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.pochem.server.services.web.AdminService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.constants.Pages;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminControllerCities {

	@Autowired
	private AdminService adminService;
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public String categories(Model model) {
		model.addAttribute("page", Pages.Admin.CITIES);
		return "admin/admin-cities";
	}
	
}
