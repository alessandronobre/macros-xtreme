package br.com.macrosxtreme.model;

import java.util.List;

import br.com.macrosxtreme.dto.PacienteDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
		this.genero = paciente.getGenero();
		this.idade = paciente.getIdade();
		this.altura = paciente.getAltura();
		this.peso = paciente.getPeso();
		this.objetivo = paciente.getObjetivo();
		this.nivelAtividadeFisica = paciente.getNivelAtividadeFisica();
	}
}
