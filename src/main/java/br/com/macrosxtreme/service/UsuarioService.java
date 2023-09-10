package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.mapper.FormatacaoStringMapper;
import br.com.macrosxtreme.mapper.GerarItensEmailMapper;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.utils.SenhaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final GerarItensEmailMapper gerarItensEmailMapper;
	private final FormatacaoStringMapper formatacaoStringMapper;

	private static final String EMAIL_CADASTRADO = "emailCadastrado";
	private static final String USUARIO_CADASTRADO = "usuarioCadastrado";

	public EmailDTO gerarEmailRecuperarSenha(String email) {
		Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email);

		return gerarItensEmailMapper.instanciaEmailDtoComConteudoHtml(usuario.getEmail(),
				"Recuperação de senha",
				formatacaoStringMapper.obterPrimeiraPalavra(usuario.getNome()),
				"usuario/template_recuperar_senha_email.html");
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
