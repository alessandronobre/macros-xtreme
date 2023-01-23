package br.com.macrosxtreme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UserDTO;
import br.com.macrosxtreme.services.LoginService;
 
@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	

//	Access Aplication
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login/login");
		return modelAndView;

	}

	@PostMapping("/login")
	public ModelAndView login(LoginDTO loginDTO) {
		ModelAndView modelAndView = new ModelAndView("login/login");
		LoginDTO userLogin = loginService.login(loginDTO);

		if (userLogin == null) {
			modelAndView.addObject("erro", "Usuario ou senha invalido");
			return modelAndView;
		}
//		session.setAttribute("usuarioLogado", userLogin);
		return logued();

	}

	@GetMapping("/logued")
	public ModelAndView logued() {
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;

	}

//	Create Account
	@GetMapping("/create")
	public ModelAndView newAccount() {
		ModelAndView modelAndView = new ModelAndView("login/new_account");
		modelAndView.addObject("userDTO", new UserDTO());

		return modelAndView;

	}

	@PostMapping("/create")
	public String createAccount(UserDTO userDTO) {
		loginService.save(userDTO);

		return "redirect:/login";

	}

//	Forgot Password
	@GetMapping("/forgot")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView("login/forgot _password");

		return modelAndView;

	}

	@PostMapping("/forgot")
	public String forgotPasswords() {
		ModelAndView modelAndView = new ModelAndView("/home");

		return "redirect:/login";

	}

}
