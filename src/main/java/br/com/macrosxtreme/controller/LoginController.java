package br.com.macrosxtreme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.model.User;

@Controller
@RequestMapping("/login")
public class LoginController {
	
//	Access Aplication
	@GetMapping
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login/login");
		return modelAndView;
		
	}
	
	@PostMapping("/access")
	public ModelAndView login(User user) {
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
		
	}
	
//	Create Account
	@GetMapping("/create")
	public ModelAndView newAccount() {
		ModelAndView modelAndView = new ModelAndView("login/new_account");
		return modelAndView;
		
	}
	
	@PostMapping("/create")
	public ModelAndView createAccount(User user) {
		ModelAndView modelAndView = new ModelAndView("home");
		User userr = new User();
		return modelAndView;
		
	}
	
//	Forgot Password
	@GetMapping("/forgot")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView("login/forgot_password");
		return modelAndView;
		
	}
	
	@PostMapping("/forgot")
	public ModelAndView forgotPassword(User user) {
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
		
	}

}
