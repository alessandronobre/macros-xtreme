package br.com.macrosxtreme.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
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
	
	@PostMapping("/enviar")
	public void enviar(@RequestBody EmailDTO email) {

		try {
			msEmailClient.enviar(email);
			
		} catch (Exception e) {
			log.error("Erro: {}", e.getMessage());
		}

	}
	
	@PostMapping("/historico")
	public void salverHistoricoEmail(@RequestBody EmailDTO email) {
		String nome = "Marcos";
		email.getUsuario().setName(nome);
		emailService.salvarHistoricoEmail(email);

	}
	
	@GetMapping("/historico")
	public ModelAndView findByHistoricoEmail(@RequestParam String usuario) {
		ModelAndView modelAndView = new ModelAndView("email/historico_email");
		String nome = "Marcos";
		List<EmailDTO> lista = null;
		lista = emailService.findEmailUsuario(nome);

		List<EmailDTO> listaHistorico = lista.stream().collect(Collectors.toList());

		modelAndView.addObject("lista", listaHistorico);
		return modelAndView;

	}

}
