package by.pochem.web.controllers;

import static by.pochem.web.subdomain.SubdomainMapping.ROOT;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.pochem.web.subdomain.SubdomainMapping;

@Controller
public class RootController {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@SubdomainMapping(value = ROOT, tld = "pochem.by")
	public String index() {
		return "index";
	}
	
}
