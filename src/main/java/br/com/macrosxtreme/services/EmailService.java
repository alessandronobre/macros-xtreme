package br.com.macrosxtreme.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.macrosxtreme.utils.FreemarkerUtils;
import freemarker.template.TemplateException;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ResourceLoader resourceLoader;

	private static final String TEXT_HTML = "text/html";

	public void sendMail(String remetente, String destino, String assunto, Map<String, Object> conteudo,
			String template) throws AddressException, MessagingException, TemplateException, IOException {

		MimeMessage mensagem = mailSender.createMimeMessage();
		MimeBodyPart body = new MimeBodyPart();
		mensagem.setFrom(new InternetAddress(remetente));

		Address[] para = InternetAddress.parse(destino);

		mensagem.setRecipients(Message.RecipientType.TO, para);
		mensagem.setSubject(assunto, "UTF-8");
		String texto = FreemarkerUtils.parseTemplate(conteudo, template);

		body.setContent(texto, TEXT_HTML);

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(body);

		mensagem.setContent(multipart, TEXT_HTML);

		mailSender.send(mensagem);

	}

//	EMAIL COM ANEXO
	public void sendMailAttachment(String to, String object, String body, String attachment) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		SimpleMailMessage message = new SimpleMailMessage();

		mimeMessageHelper.setFrom("remetente@gmail.com");
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(object);
		mimeMessageHelper.setText(body);
		Resource resource = resourceLoader.getResource(attachment);

		mimeMessageHelper.addAttachment(resource.getFilename(), resource);

		mailSender.send(mimeMessage);

	}
}
