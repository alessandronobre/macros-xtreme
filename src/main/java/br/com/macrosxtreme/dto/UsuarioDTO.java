package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
	
	private String nome;
	private String email;
	private String password;
	
	public UsuarioDTO(Usuario user) {
		nome = user.getNome();
		email = user.getEmail();
		password = user.getPassword();
	}
	
	@Override
	public String toString() {
		return "UserDTO [name=" + nome + ", email=" + email + "]";
	}
	
}