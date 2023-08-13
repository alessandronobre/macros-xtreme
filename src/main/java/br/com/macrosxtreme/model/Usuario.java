package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_usuario")
	private Long id;

	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(nullable = false, length = 40, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "usuario")
	private List<HistoricoEmail> historicoEmail ;

	public Usuario(UsuarioDTO usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.password = usuario.getPassword();
	}

}
