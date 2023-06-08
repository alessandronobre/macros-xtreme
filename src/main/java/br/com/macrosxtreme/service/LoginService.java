package br.com.macrosxtreme.service;

import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.utils.SenhaUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {

	private final UsuarioRepository usuarioRepository;

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

	public void save(UsuarioDTO usuario) {
		String encoder = SenhaUtils.passwordEncoder().encode(usuario.getPassword());
		usuario.setPassword(encoder);
		Usuario user = new Usuario(usuario);
		usuarioRepository.save(user);

	}

}
