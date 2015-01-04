package by.pochem.web.controllers.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.pochem.common.web.forms.UserForm;
import by.pochem.persistence.model.User;
import by.pochem.persistence.model.User.Role;
import by.pochem.server.services.web.AdminService;
import by.pochem.server.web.addapters.Adapter;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.constants.Pages;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminControllerUsers {
	
	@Autowired
	private AdminService adminService;
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String users(@RequestParam("role") Role role, Model model) {
		model.addAttribute("companiesCount", Role.COMPANY.equals(role) || Role.MANAGER.equals(role));
		model.addAttribute("role", role);
		model.addAttribute("page", Pages.Admin.USERS);
		return "admin/admin-users-list";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/user", method = RequestMethod.GET)
	public String user(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "role", required = false) Role role, Model model) {
		UserForm userForm = new UserForm();
		if (id != null) {
			userForm = adminService.getUserForm(id);
			role = Role.valueOf(userForm.getRole());
		} else {
			userForm.setRole(role.toString());
		}
		model.addAttribute("userForm", userForm);
		model.addAttribute("role", role);
		model.addAttribute("page", Pages.Admin.USERS);
		return "admin/admin-user";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/user", method = RequestMethod.POST)
	public String userUpdate(@ModelAttribute("userForm") UserForm userForm, Model model) {
		User user = (User) Adapter.USER2USERFORM.convertReverse(userForm);
		adminService.updateUser(user);
		return "redirect:/users/user?id=" + user.getId();
	}
	
	@ModelAttribute("citiesMap")
	public Map<String, String> citiesMap() {
		return adminService.getCitiesMap();
	}

}
