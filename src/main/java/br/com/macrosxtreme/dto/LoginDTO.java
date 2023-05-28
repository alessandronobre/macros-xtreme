package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
	
	private String email;
	private String password;
	
	public LoginDTO(Usuario user) {
		email = user.getEmail();
		password = user.getPassword();
	}
	
}