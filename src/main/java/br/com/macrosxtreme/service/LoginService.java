package br.com.macrosxtreme.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.utils.SenhaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

	private final UsuarioRepository usuarioRepository;

	private final MsEmailClient msEmailClient;
	
	private final DataMapper dataMapper;

	public Boolean login(UsuarioDTO login) {

		Usuario usuario = usuarioRepository.findByUser(login.getEmail());
		Boolean validaPassword = SenhaUtils.passwordEncoder().matches(login.getPassword(), usuario.getPassword());
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

	@Transactional
	public void salvar(UsuarioDTO usuario) {
		String SenhaEncoder = SenhaUtils.passwordEncoder().encode(usuario.getPassword());
		usuario.setPassword(SenhaEncoder);
		Usuario user = new Usuario(usuario);

		EmailDTO email = new EmailDTO();
		email.setUsuario(user);
		email.setTituloEmail("Bem vindo " + usuario.getNome());
		email.setConteudo("Conta criada com sucesso ! ;)");
		email.setDataEnvio(dataMapper.formatador());
		email.setDestinatario(usuario.getEmail());

		usuarioRepository.save(user);
		
		try {
		    msEmailClient.enviar(email);
		    
		} catch (Exception e) {
			log.error("Erro ao tentar se comunicar com o serviço de email");
			throw new EmailException();
		} 
	}

}
