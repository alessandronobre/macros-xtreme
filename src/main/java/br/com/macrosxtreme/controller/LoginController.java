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
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;

//	@PostMapping("/teste")
//	public String a(@RequestBody LoginDTO loginDTO) {
//		System.out.println(loginDTO.getEmail());
//		System.out.println(loginDTO.getPassword());
//		System.out.println("asddddddddddddddddddddddddddddddddddddddasdasd");
//		
//
//		return "redirect:/loginasd";
//	}

	@GetMapping("/login")
	public ModelAndView showLoginPage() {
		ModelAndView modelAndView = new ModelAndView("login/login");

		return modelAndView;
	}

	@PostMapping("/login")
	public ModelAndView doLogin(HttpServletRequest request, @ModelAttribute LoginDTO loginDTO) {
		ModelAndView modelAndView = new ModelAndView("login/login");

		Boolean userLogin = loginService.login(loginDTO);
		if (userLogin == false) {
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

	@GetMapping("/logued")
	public ModelAndView logued() {
		ModelAndView modelAndView = new ModelAndView("home");

		return modelAndView;
	}

	@GetMapping("/popup")
	public ModelAndView popup(String popup) {
		ModelAndView modelAndView = new ModelAndView("login/popup");
		modelAndView.addObject("popup", popup);

		return modelAndView;
	}

	@GetMapping("/create")
	public ModelAndView newAccount() {
		ModelAndView modelAndView = new ModelAndView("login/new_account");
		modelAndView.addObject("userDTO", new UserDTO());

		return modelAndView;
	}

	@PostMapping("/create")
	public ModelAndView createAccount(UserDTO userDTO) {
		ModelAndView modelAndView = new ModelAndView("login/new_account");
		
		if (loginService.validEmail(userDTO.getEmail())) {
			modelAndView.addObject("invalidEmail", "Email ja cadastrado");

			return modelAndView;
		}
		String popup = "Parabéns, sua conta foi criada com sucesso !";
		loginService.save(userDTO);
		return popup(popup);
	}

	@GetMapping("/forgot")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView("login/forgot _password");

		return modelAndView;

	}

	@PostMapping("/forgot")
	public ModelAndView forgotPasswords(String email) throws MessagingException, IOException {
		ModelAndView modelAndView = new ModelAndView("login/forgot _password");

		if (loginService.validEmail(email) == false) {
			modelAndView.addObject("invalidEmail", "Não existe conta para esse email");

			return modelAndView;

		}
		String popup = "Senha enviada com sucesso, verifique sua caixa de email.";
		loginService.sendMailForgotPassword(email);
		return popup(popup);

	}

}
