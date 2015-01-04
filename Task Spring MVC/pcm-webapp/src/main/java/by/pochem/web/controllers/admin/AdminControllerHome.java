package by.pochem.web.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.pochem.persistence.web.model.PageInfo;
import by.pochem.server.services.web.AdminService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.constants.Pages;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminControllerHome {

	@Autowired
	private AdminService adminService;
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@SubdomainMapping(value = "admin", tld = ".pochem.by")
	public String root(Model model) {
		return "redirect:/home";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		PageInfo mainPage = adminService.getMainPageInfo();
		model.addAttribute("mainPage", mainPage);
		model.addAttribute("page", Pages.Admin.HOME);
		return "admin/admin-home";
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/home/update", method = RequestMethod.POST)
	public String homeUpdate(@ModelAttribute("mainPage") PageInfo mainPage, Model model) {
		adminService.updateMainPageInfo(mainPage);
		return "redirect:/home";
	}
	
}
