package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

	private final EmailService emailService;

	@GetMapping("/macros")
	public HttpStatus enviarEmail(EmailDTO email) {
		try {
			emailService.enviarEmail(email);
			return HttpStatus.OK;
		} catch (MessagingException e) {
			log.error("Erro ao enviar email: " + e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception e) {
			log.error("Houve algum erro durante o processo de envio: " + e.getMessage());
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}