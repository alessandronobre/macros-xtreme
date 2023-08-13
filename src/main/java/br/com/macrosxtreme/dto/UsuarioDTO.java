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
	private String email;
	private String password;
	
	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.password = usuario.getPassword();
	}
	
	@Override
	public String toString() {
		return "UserDTO [name=" + nome + ", email=" + email + "]";
	}
	
}