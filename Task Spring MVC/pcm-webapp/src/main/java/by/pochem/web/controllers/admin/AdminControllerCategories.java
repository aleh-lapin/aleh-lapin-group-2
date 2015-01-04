package by.pochem.web.controllers.admin;

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

import by.pochem.persistence.model.Category;
import by.pochem.persistence.model.Filter;
import by.pochem.persistence.model.Image;
import by.pochem.persistence.web.model.PageInfo;
import by.pochem.server.services.web.AdminService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.binders.CategoryBinder;
import by.pochem.web.binders.FilterMapBinder;
import by.pochem.web.constants.Pages;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminControllerCategories {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CategoryBinder categoryBinder;
	
	@Autowired
	private FilterMapBinder filterMapBinder;
	
	@InitBinder
	public void initBinderAll(WebDataBinder binder) {
		binder.registerCustomEditor(Category.class, categoryBinder);
		binder.registerCustomEditor(Filter.class, filterMapBinder);
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String categories(Model model) {
		model.addAttribute("page", Pages.Admin.CATEGORIES);
		return "admin/admin-categories-list";
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/categories/category", method = RequestMethod.GET)
	public String category(Model model, @RequestParam(required = false) Long id) {
		Category category = new Category();
		category.setPageInfo(new PageInfo());
		if (id != null) {
			category = adminService.getCategory(id);
		}
		model.addAttribute("category", category);
		model.addAttribute("page", Pages.Admin.CATEGORIES);
		return "admin/admin-category";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/categories/category", method = RequestMethod.POST)
	public String categoryUpdate(
			@ModelAttribute("category") Category cacategory, Model model) {
		adminService.updateCategory(cacategory);
		return "redirect:/categories/category?id=" + cacategory.getId();
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/categories/media", method = RequestMethod.GET)
	public String categoryMedia(Model model, @RequestParam Long id) {
		Image categoryLogo = adminService.getCategoryLogo(id);
		List<Image> categoryBanners = adminService.getCategoryBanners(id);
		model.addAttribute("categoryLogo", categoryLogo);
		model.addAttribute("categoryBanners", categoryBanners);
		model.addAttribute("page", Pages.Admin.CATEGORIES);
		return "admin/admin-category-media";
	}
	
	@ModelAttribute("categoriesTree")
	public List<Category> categoriesTree(@RequestParam(required = false) Long id) {
		return adminService.getCategoryTree(id != null ? id : 0);
	}

	@ModelAttribute("filtersMap")
	public Map<String, Map<String, String>> filtersMap() {
		return adminService.getFiltersMap();
	}
	
}
