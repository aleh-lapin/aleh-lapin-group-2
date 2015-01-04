package by.pochem.web.controllers.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import by.pochem.common.utils.JsonUtils;
import by.pochem.common.web.client.json.CatalogNode;
import by.pochem.common.web.client.json.Company;
import by.pochem.common.web.client.json.JCitiesList.JCity;
import by.pochem.common.web.client.json.JFilterAttributesList.JFilterAttribute;
import by.pochem.common.web.client.json.JFilterGroupList.JFilterGroup;
import by.pochem.common.web.forms.CatalogItemForm;
import by.pochem.persistence.model.Category;
import by.pochem.persistence.model.User.Role;
import by.pochem.server.services.web.AdminService;
import by.pochem.server.services.web.CatalogService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.extractors.Extractors;
import by.pochem.web.subdomain.SubdomainMapping;

@Controller
@RequestMapping(value = "/arest")
@SubdomainMapping(value = "admin", tld = ".pochem.by")
public class AdminRestController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CatalogService catalogService;
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/companies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Company> companies() {
		return adminService.getCompaniesList();
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Category> categories() {
		return adminService.getCategoryTree(0);
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/catalog/node/tree", method = RequestMethod.GET)
	public @ResponseBody String catalogNodeTree(@RequestParam("id") Long id) {
		return JsonUtils.wrap(catalogService.getCatalogNodeTree(id));
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/catalog/node", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean catalogNodeAdd(@RequestBody CatalogNode catalogNode) {
		catalogService.updateCatalogNode(catalogNode);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/catalog/node", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean catalogNodeDelete(@RequestBody CatalogNode catalogNode) {
		catalogService.deleteCatalogNode(catalogNode.getId());
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/catalog/node/items", method = RequestMethod.GET)
	public @ResponseBody String catalogItems(@RequestParam("id") Long id, @RequestParam("nodeId") Long nodeid) {
		return JsonUtils.wrap(catalogService.getCatalogItems(nodeid), 
				catalogService.getCatalogNodeTree(id));
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/catalog/node/items/item", method = RequestMethod.POST)
	public @ResponseBody boolean catalogItemUpdate(MultipartHttpServletRequest mpr) {
		CatalogItemForm catalogItemForm = (CatalogItemForm) Extractors.PRODUCT_EXT.extract(mpr);
		catalogService.updateCatalogItem(catalogItemForm);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/catalog/node/items/item/delete", method = RequestMethod.POST)
	public @ResponseBody boolean catalogItemDelete(@RequestParam("id") Long id) {
		catalogService.deleteCatalogItem(id);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/groups", method = RequestMethod.GET)
	public @ResponseBody String filtersGroups() {
		return JsonUtils.wrap(adminService.getFiltersGroups());
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/groups/group", method = RequestMethod.POST)
	public @ResponseBody boolean filtersGroupUpdate(@RequestBody JFilterGroup filterGroup) {
		adminService.updateFilterGroup(filterGroup);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/groups/group/delete", method = RequestMethod.POST)
	public @ResponseBody boolean filtersGroupDelete(@RequestParam("id") Long id) {
		adminService.deleteFilterGroup(id);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters", method = RequestMethod.GET)
	public @ResponseBody String filters() {
		return JsonUtils.wrap(adminService.getFilters());
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/filter/attribute", method = RequestMethod.POST)
	public @ResponseBody boolean filterAttributeUpdate(@RequestBody JFilterAttribute attribute) {
		adminService.updateFilterAttribute(attribute);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/filter/attribute/delete", method = RequestMethod.POST)
	public @ResponseBody boolean filterAttributeDelete(@RequestParam("id") Long id) {
		adminService.deleteFilterAttribute(id);
		return true;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/filters/filter/attributes", method = RequestMethod.GET)
	public @ResponseBody String filterAttributes(@RequestParam("filterId") Long filterId) {
		return JsonUtils.wrap(adminService.getFilterAttributes(filterId));
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public @ResponseBody String citites() {
		return JsonUtils.wrap(adminService.getCities());
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/cities/city", method = RequestMethod.POST)
	public @ResponseBody boolean cityUpdate(@RequestBody JCity city) {
		adminService.updateCity(city);
		return true;
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/cities/city/delete", method = RequestMethod.POST)
	public @ResponseBody boolean cityDelete(@RequestParam("id") Long id) {
		adminService.deleteCity(id);
		return true;
	}
	
	@AuthorizeRequest("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody String users(@RequestParam("role") Role role) {
		return JsonUtils.wrap(adminService.getJUsersByRole(role));
	}
	
}
