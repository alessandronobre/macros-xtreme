package br.com.macrosxtreme.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

	private final EmailService emailService;
	
	private final MsEmailClient msEmailClient;
	
	private final UsuarioRepository usuarioRepository;
	
	@PostMapping("/enviar")
	public void enviar(@RequestBody EmailDTO email) {

		try {
			msEmailClient.enviar(email);
			
		} catch (Exception e) {
			log.error("Erro ao tentar se comunicar com o serviço de email");
			throw new EmailException();
		}

	}
	
	@PostMapping("/historico")
	public void salvarHistoricoEmail(@RequestBody EmailDTO email) {
		Usuario user = usuarioRepository.findByUser("teste@gmail.com");
		email.setUsuario(user);
		emailService.salvarHistoricoEmail(email);

	}
	
	@GetMapping("/historico")
	public ModelAndView findByHistoricoEmail() {
		ModelAndView modelAndView = new ModelAndView("email/historico_email");

		Usuario user = usuarioRepository.findByUser("teste@gmail.com");
		List<EmailDTO> lista = emailService.findEmailUsuario(user.getId());

		if(lista != null) {
			modelAndView.addObject("lista", lista);
			
			return modelAndView;
		}
		return modelAndView;

	}
	
}
