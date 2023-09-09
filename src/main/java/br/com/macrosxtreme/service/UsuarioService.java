package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.utils.SenhaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final TemplateEngine templateEngine;

	private static final String EMAIL_CADASTRADO = "emailCadastrado";
	private static final String USUARIO_CADASTRADO = "usuarioCadastrado";

	public EmailDTO gerarEmailRecuperarSenha(String email) {
		Usuario user = usuarioRepository.buscarUsuarioPorEmail(email);
		String nomeUsuario = null;
		if (user.getNome().contains(" ")) {
			int indice = user.getNome().indexOf(' ');
			nomeUsuario = user.getNome().substring(0, indice);
		} else {
			nomeUsuario = user.getNome();
		}

		Context context = new Context();
		context.setVariable("usuario", nomeUsuario);
		String html = templateEngine.process("usuario/template_recuperar_senha_email.html", context);

		EmailDTO mail = new EmailDTO();
		mail.setDestinatario(user.getEmail());
		mail.setTitulo("Recuperação de senha");
		mail.setConteudo(html);
		return mail;
	}

	public Boolean autenticacao(UsuarioDTO usuario) {
		Usuario user = usuarioRepository.buscarUsuario(usuario.getUsuario());
		if (user != null) {
			Boolean validaSenha = SenhaUtils.passwordEncoder().matches(usuario.getSenha(), user.getSenha());
			if (validaSenha) {
				return true;
			}
		}
		return false;
	}

	public String validaUsuario(UsuarioDTO usuario) {
		Usuario user = usuarioRepository.buscarUsuario(usuario.getUsuario());
		String mail = validaEmail(usuario.getEmail());
		if (user != null) {
			return USUARIO_CADASTRADO;
		} else if (mail != null) {
			return mail;
		}
		return null;
	}

	public String validaEmail(String email) {
		String mail = usuarioRepository.buscarEmailUsuario(email);
		if (mail != null) {
			return EMAIL_CADASTRADO;
		}
		return null;
	}

	public void salvar(UsuarioDTO usuario) {
		String senhaEncoder = SenhaUtils.passwordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		Usuario user = new Usuario(usuario);
		usuarioRepository.save(user);
	}
}
