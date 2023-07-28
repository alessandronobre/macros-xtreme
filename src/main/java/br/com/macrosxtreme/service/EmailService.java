package br.com.macrosxtreme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.repository.EmailRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {

	private final EmailRepository emailRepository;
	private final MsEmailClient msEmailClient;

	public void enviarEmail(EmailDTO email) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(email);

		msEmailClient.enviar(json);

	}

	public List<EmailDTO> findEmailUsuario(Long codUsuario) {
		List<HistoricoEmail> histMacros = emailRepository.findEmailUsuario(codUsuario);
		List<EmailDTO> historico = new ArrayList<>();

		if (!histMacros.isEmpty()) {
			histMacros.forEach(histMacro -> historico.add(new EmailDTO(histMacro)));

		}
		return historico;
	}

	public void salvarHistoricoEmail(EmailDTO email) {
		HistoricoEmail historico = new HistoricoEmail(email);
		emailRepository.save(historico);
	}

}
