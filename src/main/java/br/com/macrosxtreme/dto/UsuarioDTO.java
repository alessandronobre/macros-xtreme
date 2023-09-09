package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private Long id;
	private String nome;
	private String usuario;
	private String email;
	private String senha;
	
	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.usuario = usuario.getUsuario();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
	}
}