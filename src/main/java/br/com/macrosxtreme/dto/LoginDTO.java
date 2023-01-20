package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
	
	private String email;
	private String password;
	
	public LoginDTO(User user) {
		email = user.getEmail();
		password = user.getPassword();
	}
	
}