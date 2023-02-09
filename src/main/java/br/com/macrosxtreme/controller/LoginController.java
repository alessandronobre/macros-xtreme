package br.com.macrosxtreme.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UserDTO;
import br.com.macrosxtreme.services.LoginService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;


//	Access Aplication
	@GetMapping("/login")
	public ModelAndView showLoginPage() {
		ModelAndView modelAndView = new ModelAndView("login/login");

		return modelAndView;
	}

	@PostMapping("/login")
	public ModelAndView doLogin(HttpServletRequest request, @ModelAttribute LoginDTO loginDTO) {
		ModelAndView modelAndView = new ModelAndView("login/login");
		LoginDTO userLogin = loginService.login(loginDTO);
		if (userLogin == null) {
			modelAndView.addObject("erro", "Usuario ou senha invalido");
			return modelAndView;
		}
		request.getSession().setAttribute("user", loginDTO);
		return logued();
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:/login";
	}

//    <a href="/logout">Logout</a>

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
	public ModelAndView createAccount(UserDTO userDTO) {
		ModelAndView modelAndView = new ModelAndView("login/new_account");
		if (userDTO.getName().equals("") || userDTO.getEmail().equals("") || userDTO.getPassword().equals("")
				|| userDTO == null) {
			modelAndView.addObject("erro", "Preencha os campos por favor");
			return modelAndView;
		}
		loginService.save(userDTO);

		return showLoginPage();

	}

//	Forgot Password
	@GetMapping("/forgot")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView("login/forgot _password");

		return modelAndView;

	}

	@PostMapping("/forgot")
	public String forgotPasswords(UserDTO userDTO) throws MessagingException, TemplateException, IOException {
		loginService.sendMailForgotPassword(userDTO);
		
		return "redirect:/login";

	}

}
