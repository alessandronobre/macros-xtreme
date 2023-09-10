package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmail(EmailDTO email) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email.getDestinatario());
        mimeMessageHelper.setSubject(email.getTitulo());
        mimeMessageHelper.setText(email.getConteudo(), true);
        if (email.getAnexo() != null) {
            mimeMessageHelper.addAttachment(email.getNomeAnexo(), new ByteArrayResource(email.getAnexo()));
        }
        mailSender.send(mimeMessage);
        log.info("Enviando email para: " + email.getDestinatario() + " ...");
    }
}
