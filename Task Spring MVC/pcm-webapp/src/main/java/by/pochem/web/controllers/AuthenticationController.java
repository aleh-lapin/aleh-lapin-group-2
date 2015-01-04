package by.pochem.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.pochem.common.web.forms.JoinForm;
import by.pochem.server.services.auth.AuthenticationService;

@Controller
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinPage(Model model) {
		JoinForm joinForm = new JoinForm();
		model.addAttribute("joinForm", joinForm);
		return "join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinAdd(@ModelAttribute("joinForm") JoinForm joinForm, Model model) {
		authenticationService.joinUser(joinForm);
		return "redirect:/login";
	}
	
}
