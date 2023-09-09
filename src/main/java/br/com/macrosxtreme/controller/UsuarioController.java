package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final EmailController emailController;

	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}


	@GetMapping("/logout")
	public RedirectView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return new RedirectView("/api/usuario/autenticacao");
	}

	@GetMapping("/autenticacao")
	public ModelAndView autenticacao(String msg) {
		ModelAndView modelAndView = new ModelAndView("usuario/login");
		modelAndView.addObject("usuario", new UsuarioDTO());
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}

	@PostMapping("/autenticacao")
	public ModelAndView autenticacao(HttpServletRequest request, @ModelAttribute UsuarioDTO usuario) {
		Boolean autenticacao = usuarioService.autenticacao(usuario);
		if (autenticacao == false) {
			String msg = "error";
			return autenticacao(msg);
		}
		request.getSession().setAttribute("user", usuario);
		return home();
	}

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(String msg) {
		ModelAndView modelAndView = new ModelAndView("usuario/nova_conta");
		modelAndView.addObject("usuario", new UsuarioDTO());
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrar(UsuarioDTO usuario) {
		String msg = usuarioService.validaUsuario(usuario);
		if (msg != null) {
			return cadastrar(msg);
		}
		usuarioService.salvar(usuario);
		return autenticacao(null);
	}

	@GetMapping("/recupera/senha")
	public ModelAndView recuperarSenha(String msg) {
		ModelAndView modelAndView = new ModelAndView("usuario/recupera_senha");
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}

	@PostMapping("/recupera/senha")
	public ModelAndView recuperaSenha(String email) {
		String msg = usuarioService.validaEmail(email);
		if (msg == null) {
			return recuperarSenha("emailNull");
		}
		HttpStatus resp = emailController.enviarEmail(usuarioService.gerarEmailRecuperarSenha(email));
		if (resp == HttpStatus.OK) {
			msg = "sucesso";
		} else {
			msg = "erro";
		}
		return recuperarSenha(msg);
	}
}
