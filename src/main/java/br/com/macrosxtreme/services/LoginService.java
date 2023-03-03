package br.com.macrosxtreme.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UserDTO;
import br.com.macrosxtreme.model.User;
import br.com.macrosxtreme.repositories.UserRepository;
import jakarta.mail.MessagingException;


@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Boolean login(LoginDTO loginDTO) {

		User user = userRepository.findByUser(loginDTO.getEmail());
		Boolean validPassword = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
		if (user != null) {
			if (validPassword) {
				return true;
			}
			
		}
		return false;

	}
	
	public Boolean validEmail(String email) {
		String emails = userRepository.findByEmail(email);
		if (emails != null) {
			return true;
		}
		return false;

	}

	public void save(UserDTO userDTO) {
		String encoder = passwordEncoder.encode(userDTO.getPassword());
		userDTO.setPassword(encoder);
		User user = new User(userDTO);
		userRepository.save(user);

	}
	
	public void sendMailForgotPassword(String email) throws MessagingException, IOException {

		User user = userRepository.findByUser(email);
		String assunto = "Ola, " + user.getName();
		Map<String, Object> conteudo = new HashMap<>();
		conteudo.put("sucesso", "Sua senha: " + user.getPassword());
		emailService.sendMail(user.getEmail(), assunto, conteudo);

	}

}
