package br.com.macrosxtreme.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UserDTO;
import br.com.macrosxtreme.model.User;
import br.com.macrosxtreme.repositories.UserRepository;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	public UserDTO findByUser(String email) {
		User user = userRepository.findByUser(email);
		UserDTO userdto = new UserDTO(user);

		return userdto;

	}

	public LoginDTO login(LoginDTO loginDTO) {

		User toCheck = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		if (toCheck == null) {
			return null;
		}
		LoginDTO userToCheck = new LoginDTO(toCheck);
		return userToCheck;

	}

	public void save(UserDTO userDTO) {
		User user = new User(userDTO);
		userRepository.save(user);

	}


	public void sendMailForgotPassword(UserDTO userDTO) throws MessagingException, TemplateException, IOException {

		UserDTO user = findByUser(userDTO.getEmail());
		Map<String, Object> conteudo = new HashMap<>();
		conteudo.put("sucesso", "TESTE SUCESSO");
		conteudo.put("falha", "TESTE FALHA");
		String assunto = "Ola, " + user.getName();
		emailService.sendMail("remetente@gmail.com", user.getEmail(), assunto, conteudo, "temp.ftl");

	}

}
