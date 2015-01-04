package by.pochem.web.controllers.company;

import static by.pochem.web.subdomain.SubdomainMapping.WILDCARD_COMPANY;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.pochem.web.subdomain.SubdomainMapping;

@Controller
public class CompanyController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@SubdomainMapping(value = WILDCARD_COMPANY, tld = "pochem.by")
	public String index() {
		return "index";
	}
	
}
