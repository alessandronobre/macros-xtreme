package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_usuario")
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 40, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;

	public Usuario(LoginDTO login) {
		email = login.getEmail();
		password = login.getPassword();
	}

	public Usuario(UsuarioDTO usuario) {
		name = usuario.getName();
		email = usuario.getEmail();
		password = usuario.getPassword();
	}

}