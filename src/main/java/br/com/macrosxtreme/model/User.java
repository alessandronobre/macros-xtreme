package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class User extends Entityy {

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 40, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;

	public User(LoginDTO loginDTO) {
		email = loginDTO.getEmail();
		password = loginDTO.getPassword();
	}

	public User(UserDTO userDTO) {
		name = userDTO.getName();
		email = userDTO.getEmail();
		password = userDTO.getPassword();
	}

}
