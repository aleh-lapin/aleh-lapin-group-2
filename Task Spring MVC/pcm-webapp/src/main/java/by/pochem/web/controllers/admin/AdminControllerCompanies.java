package by.pochem.web.controllers.admin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.pochem.persistence.constants.Status;
import by.pochem.persistence.model.Category;
import by.pochem.persistence.model.Company;
import by.pochem.persistence.model.Image;
import by.pochem.persistence.model.User;
import by.pochem.persistence.model.User.Role;
import by.pochem.persistence.web.model.PageInfo;
import by.pochem.server.services.web.AdminService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.binders.CategoryBinder;
import by.pochem.web.binders.UserBinder;
import by.pochem.web.constants.Pages;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminControllerCompanies {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CategoryBinder categoryBinder;
	
	@Autowired
	private UserBinder userBinder;
	
	@InitBinder
	public void initBinderAll(WebDataBinder binder) {
		binder.registerCustomEditor(Category.class, categoryBinder);
		binder.registerCustomEditor(User.class, userBinder);
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public String companies(Model model) {
		model.addAttribute("page", Pages.Admin.COMPANIES);
		return "admin/admin-companies-list";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/companies/company", method = RequestMethod.GET)
	public String company(Model model, @RequestParam(required = false) Long id) {
		Company company = new Company();
		company.setPageInfo(new PageInfo());
		if (id != null) {
			company = adminService.getCompany(id);
		}
		model.addAttribute("company", company);
		model.addAttribute("page", Pages.Admin.COMPANIES);
		return "admin/admin-company-general";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/companies/company", method = RequestMethod.POST)
	public String companyUpdate(@ModelAttribute("company") Company company, Model model) {
		adminService.updateCompany(company);
		return "redirect:/companies/company?id=" + company.getId();
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/companies/company/catalog", method = RequestMethod.GET)
	public String companyCatalog(@RequestParam("id") Long id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("page", Pages.Admin.COMPANIES);
		return "admin/admin-company-catalog";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/companies/company/gallery", method = RequestMethod.GET)
	public String companyGallery(@RequestParam("id") Long id, Model model) {
		List<Image> companyImages = adminService.getCompanyImages(id);
		model.addAttribute("companyImages", companyImages);
		model.addAttribute("id", id);
		model.addAttribute("page", Pages.Admin.COMPANIES);
		return "admin/admin-company-gallery";
	}
	
	@ModelAttribute("categoriesTree")
	public List<Category> companiesCategories(@RequestParam(required = false) Long id) {
		return adminService.getCategoryTree(id != null ? id : 0);
	}
	
	@ModelAttribute("companiesOwners")
	public Map<String, String> companiesOwnersMap() {
		return adminService.getUsersByRole(Role.COMPANY);
	}
	
	@ModelAttribute("managers")
	public Map<String, String> managersMap() {
		return adminService.getUsersByRole(Role.MANAGER);
	}
	
	@ModelAttribute("statuses")
	public Map<String, String> statusesMap() {
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (Status status : Status.values()) {
			result.put(status.toString(), status.getName());
		}
		return result;
	}
	
}
