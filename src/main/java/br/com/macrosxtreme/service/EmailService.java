package br.com.macrosxtreme.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
  
@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;

	
	public void sendMail(String to, String object, Map<String, Object> props) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

		Context context = new Context();
		context.setVariables(props);
		
		String html = templateEngine.process("/login/email_password.html", context);

		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(object);
		mimeMessageHelper.setText(html, true);

		mailSender.send(mimeMessage);

	}

}
