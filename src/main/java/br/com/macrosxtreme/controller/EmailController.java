package br.com.macrosxtreme.controller;

import java.util.List;

import br.com.macrosxtreme.dto.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.service.EmailService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

	private final EmailService emailService;
	private final UsuarioRepository usuarioRepository;
	
	@PostMapping("/historico")
	public void salvarHistoricoEmail(@RequestBody String json) {
		Gson gson = new Gson();
		EmailDTO email = gson.fromJson(json, EmailDTO.class);
		
		Usuario usuario = usuarioRepository.findByUser("teste@gmail.com");
		email.setUsuario(usuario);
		emailService.salvarHistoricoEmail(email);
	}
	
	@GetMapping("/historico")
	public ModelAndView findByHistoricoEmail(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("email/historico_email");
		UsuarioDTO usuarioLogado = (UsuarioDTO) request.getSession().getAttribute("user");
		List<EmailDTO> lista = emailService.findEmailUsuario(
				usuarioRepository.findByUser(usuarioLogado.getEmail()).getId());

		if(lista != null) {
			modelAndView.addObject("lista", lista);
			
			return modelAndView;
		}
		return modelAndView;
	}
}
