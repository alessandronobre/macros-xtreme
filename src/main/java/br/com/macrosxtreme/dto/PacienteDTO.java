package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Paciente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PacienteDTO {
	
	private String nome;
	private String email;
	private String genero;
	private int idade;
	private int altura;
	private int peso;
	private String objetivo;
	private String nivelAtividadeFisica;
	
	public PacienteDTO(Paciente paciente) {
		this.nome = paciente.getNome();
		this.email = paciente.getEmail();
		this.genero = paciente.getGenero();
		this.idade = paciente.getIdade();
		this.altura = paciente.getAltura();
		this.peso = paciente.getPeso();
		this.objetivo = paciente.getObjetivo();
		this.nivelAtividadeFisica = paciente.getNivelAtividadeFisica();
	}

}