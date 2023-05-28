package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
	
	private String name;
	private String email;
	private String password;
	
	public UsuarioDTO(Usuario user) {
		name = user.getName();
		email = user.getEmail();
		password = user.getPassword();
	}
	
	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + "]";
	}
	
}