package br.com.macrosxtreme.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.service.LoginService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	LoginService loginService;

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login/login");

		return modelAndView;
	}

	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, @ModelAttribute LoginDTO login) {
		ModelAndView modelAndView = new ModelAndView("login/login");

		Boolean userLogin = loginService.login(login);
		if (userLogin == false) {
			modelAndView.addObject("erro", "Usuario ou senha invalido");

			return modelAndView;
		}

		request.getSession().setAttribute("user", login);
		return home();
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");

		return "redirect:/login";
	}

	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("home");

		return modelAndView;
	}

	@GetMapping("/popup")
	public ModelAndView popup(String popup) {
		ModelAndView modelAndView = new ModelAndView("login/popup");
		modelAndView.addObject("popup", popup);

		return modelAndView;
	}

	@GetMapping("/criar")
	public ModelAndView criarConta() {
		ModelAndView modelAndView = new ModelAndView("login/criar_conta");
		modelAndView.addObject("user", new UsuarioDTO());

		return modelAndView;
	}

	@PostMapping("/criar")
	public ModelAndView criarConta(UsuarioDTO usuario) {
		ModelAndView modelAndView = new ModelAndView("login/criar_conta");
		
		if (loginService.validaEmail(usuario.getEmail())) {
			modelAndView.addObject("invalidEmail", "Email ja cadastrado");

			return modelAndView;
		}
		String popup = "Parabéns, sua conta foi criada com sucesso !";
		loginService.save(usuario);
		return popup(popup);
	}

	@GetMapping("/recupera/senha")
	public ModelAndView recuperarSenha() {
		ModelAndView modelAndView = new ModelAndView("login/recupera_senha");

		return modelAndView;

	}

	@PostMapping("/recupera/senha")
	public ModelAndView recuperaSenha(String email) throws MessagingException, IOException {
		ModelAndView modelAndView = new ModelAndView("login/recupera_senha");

		if (loginService.validaEmail(email) == false) {
			modelAndView.addObject("invalidEmail", "Não existe conta para esse email");

			return modelAndView;

		}
		String popup = "Senha enviada com sucesso, verifique sua caixa de email.";
		loginService.sendMailForgotPassword(email);
		return popup(popup);

	}

}
