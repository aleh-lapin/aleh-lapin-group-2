package by.pochem.web.controllers.admin;

import java.util.LinkedHashMap;
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

import by.pochem.persistence.model.Filter;
import by.pochem.persistence.model.FilterGroup;
import by.pochem.persistence.model.Filter.FilterType;
import by.pochem.server.services.web.AdminService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.binders.FilterGroupBinder;
import by.pochem.web.constants.Pages;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminControllerFilters {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FilterGroupBinder filterGroupBinder;
	
	@InitBinder
	public void initBinderAll(WebDataBinder binder) {
		binder.registerCustomEditor(FilterGroup .class, filterGroupBinder);
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/groups", method = RequestMethod.GET)
	public String filtersGroups(Model model) {
		model.addAttribute("page", Pages.Admin.FILTERS);
		return "admin/admin-filters-groups";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters", method = RequestMethod.GET)
	public String filters(Model model) {
		model.addAttribute("page", Pages.Admin.FILTERS);
		return "admin/admin-filters-filters";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/filter", method = RequestMethod.GET)
	public String filter(Model model, @RequestParam(required = false) Long id) {
		Filter filter = new Filter();
		if (id != null) {
			filter = adminService.getFilter(id);
		}
		model.addAttribute("filter", filter);
		model.addAttribute("page", Pages.Admin.FILTERS);
		return "admin/admin-filters-filter";
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/filter", method = RequestMethod.POST)
	public String filterUpdate(Model model, @ModelAttribute("filter") Filter filter) {
		adminService.updateFilter(filter);
		return "redirect:/filters/filter?id=" + filter.getId();
	}
	
	@ModelAttribute("filtersTypes")
	public Map<String, String> filtersTypesMap() {
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (FilterType filterType : FilterType.values()) {
			result.put(filterType.toString(), filterType.getName());
		}
		return result;
	}
	
	@ModelAttribute("filtersGroupsMap")
	public Map<String, String> filtersGroupsMap() {
		return adminService.getFiltersGroupsMap();
	}
	
}
