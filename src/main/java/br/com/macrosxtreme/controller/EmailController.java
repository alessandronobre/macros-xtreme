package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.service.EmailService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
