package br.com.macrosxtreme.service;

import java.util.ArrayList;
import java.util.List;

import br.com.macrosxtreme.exception.EmailException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.repository.EmailRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    private final MsEmailClient msEmailClient;

    public void enviarEmail(EmailDTO email) {
        Gson gson = new Gson();
        String json = gson.toJson(email);

        try {
            msEmailClient.enviar(json);
        } catch (Exception e) {
            throw new EmailException("Erro ao enviar email: ", e.getMessage());
        }
    }

    public List<EmailDTO> findEmailUsuario(Long codUsuario) {
        List<HistoricoEmail> histEmail = emailRepository.findEmailUsuario(codUsuario);
        List<EmailDTO> historico = new ArrayList<>();

        if (!histEmail.isEmpty()) {
            histEmail.forEach(histMacro -> historico.add(new EmailDTO(histMacro)));

        }
        return historico;
    }

    public void salvarHistoricoEmail(EmailDTO email) {
        HistoricoEmail historico = new HistoricoEmail(email);
        emailRepository.save(historico);
    }

}
