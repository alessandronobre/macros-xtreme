package br.com.macrosxtreme.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login/login");

		return modelAndView;
	}

	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, @ModelAttribute UsuarioDTO login) {
		ModelAndView modelAndView = new ModelAndView("login/login");

		Boolean userLogin = loginService.login(login);
		if (userLogin == false) {
			modelAndView.addObject("erro", "Usuario ou senha invalido");

			return modelAndView;
		}

		request.getSession().setAttribute("user", login);
		return home();
	}

	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("inicio/index");

		return modelAndView;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");

		return login();
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
		loginService.salvar(usuario);
		return login();
	}

	@GetMapping("/recupera/senha")
	public ModelAndView recuperarSenha() {
		ModelAndView modelAndView = new ModelAndView("login/recupera_senha");

		return modelAndView;

	}

	@PostMapping("/recupera/senha")
	public ModelAndView recuperaSenha(String email) throws IOException {
		ModelAndView modelAndView = new ModelAndView("login/recupera_senha");

		if (loginService.validaEmail(email) == false) {
			modelAndView.addObject("invalidEmail", "Não existe conta para esse email");

			return modelAndView;

		}
		return modelAndView;

	}

}
