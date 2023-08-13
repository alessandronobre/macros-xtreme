package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.PacienteDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_paciente")
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String genero;
	
	@Column(nullable = false)
	private int idade;
	
	@Column(nullable = false)
	private int altura;
	
	@Column(nullable = false)
	private int peso;
	
	@Column(nullable = false)
	private String objetivo;
	
	@Column(nullable = false)
	private String nivelAtividadeFisica;
	
	@OneToMany(mappedBy = "paciente")
	private List<Macros> historicoMacros;
	
	public Paciente(PacienteDTO paciente) {
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
