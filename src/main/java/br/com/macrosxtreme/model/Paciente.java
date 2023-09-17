package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.enums.Genero;
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
	@Enumerated(EnumType.STRING)
	private Genero genero;

	@Column(nullable = false)
	private Integer idade;

	@Column(nullable = false)
	private Integer altura;

	@Column(nullable = false)
	private Integer peso;
	
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Macros> historicoMacros;

	@ManyToOne
	@JoinColumn(name="cod_usuario_fk", nullable = false)
	private Usuario usuario;

	public Paciente(PacienteDTO paciente) {
		this.id = paciente.getId();
		this.nome = paciente.getNome();
		this.email = paciente.getEmail();
		this.genero = paciente.getGenero();
		this.idade = paciente.getIdade();
		this.altura = paciente.getAltura();
		this.peso = paciente.getPeso();
	}
}
