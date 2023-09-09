package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_usuario")
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
	private String usuario;
	@Column(nullable = false, length = 40, unique = true)
	private String email;
	@Column(nullable = false)
	private String senha;

	public Usuario(UsuarioDTO usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.usuario = usuario.getUsuario();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
	}

}
