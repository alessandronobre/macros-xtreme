package br.com.macrosxtreme.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import jakarta.mail.MessagingException;


@Service
public class LoginService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Boolean login(LoginDTO login) {

		Usuario usuario = usuarioRepository.findByUser(login.getEmail());
		Boolean validaPassword = passwordEncoder.matches(login.getPassword(), usuario.getPassword());
		if (usuario != null) {
			if (validaPassword) {
				return true;
			}
			
		}
		return false;

	}
	
	public Boolean validaEmail(String email) {
		String emails = usuarioRepository.findByEmail(email);
		if (emails != null) {
			return true;
		}
		return false;

	}

	public void save(UsuarioDTO usuario) {
		String encoder = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(encoder);
		Usuario user = new Usuario(usuario);
		usuarioRepository.save(user);

	}
	
	public void sendMailForgotPassword(String email) throws MessagingException, IOException {

		Usuario usuario = usuarioRepository.findByUser(email);
		String assunto = "Ola, " + usuario.getName();
		Map<String, Object> conteudo = new HashMap<>();
		conteudo.put("sucesso", "Sua senha: " + usuario.getPassword());
		emailService.sendMail(usuario.getEmail(), assunto, conteudo);

	}

}
