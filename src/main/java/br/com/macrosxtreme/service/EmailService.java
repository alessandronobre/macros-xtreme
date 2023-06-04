package br.com.macrosxtreme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailService {
	
	private final EmailRepository emailRepository;
	
	public List<EmailDTO> findEmailUsuario(String usuario) {
		List<HistoricoEmail> histMacros = emailRepository.findEmailUsuario(usuario);
		List<EmailDTO> historico = new ArrayList<>();

		if (!histMacros.isEmpty()) {
			for (HistoricoEmail histMacro : histMacros) {
				EmailDTO hist = new EmailDTO(histMacro);
				historico.add(hist);
			}
		} else {
			log.info("Voce ainda não possui historico de emails ");
		}

		return historico;
	}
	
	public void salvarHistoricoEmail(EmailDTO email) {
		HistoricoEmail historico = new HistoricoEmail(email);
		emailRepository.save(historico);
	}

}
