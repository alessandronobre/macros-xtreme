package br.com.macrosxtreme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.model.User;
import br.com.macrosxtreme.repositories.UserRepository;
import br.com.macrosxtreme.services.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginService loginService;
	
//	Access Aplication
	@GetMapping("/access")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login/login");
		return modelAndView;
		
	}
	
	@PostMapping("/access")
	public String login(User user) {
		ModelAndView modelAndView = new ModelAndView("/home");
		
			if (loginService.login(user) == true){
				System.out.println("Usuario Logado");
				return "redirect:/home";
			}
			modelAndView.addObject("erro", "Usuario ou senha invalido");
		return "redirect:/login/access";
		
	}
	
//	Create Account
	@GetMapping("/create")
	public ModelAndView newAccount() {
		ModelAndView modelAndView = new ModelAndView("login/new_account");
		modelAndView.addObject("user", new User());
		
		return modelAndView;
		
	}
	
	@PostMapping("/create")
	public String createAccount(User user) {
		userRepository.save(user);
		
		return "redirect:/login";
		
	}
	
//	Forgot Password
	@GetMapping("/forgot")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView("login/forgot _password");
		
		return modelAndView;
		
	}
	
	@PostMapping("/forgot")
	public String forgotPassword(User user) {
		ModelAndView modelAndView = new ModelAndView("/home");
		
		return "redirect:/login";
		
	}

}
