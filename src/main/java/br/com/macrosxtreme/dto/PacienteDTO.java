package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.enums.Genero;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PacienteDTO {

	private Long id;
	private String nome;
	private String email;
	private Genero genero;
	private Integer idade;
	private Integer altura;
	private Integer peso;
	private List<Macros> historicoMacros;
	private Usuario usuario;
	
	public PacienteDTO(Paciente paciente) {
		this.id = paciente.getId();
		this.nome = paciente.getNome();
		this.email = paciente.getEmail();
		this.genero = paciente.getGenero();
		this.idade = paciente.getIdade();
		this.altura = paciente.getAltura();
		this.peso = paciente.getPeso();
	}
}