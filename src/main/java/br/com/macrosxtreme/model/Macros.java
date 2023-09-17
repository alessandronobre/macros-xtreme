package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.enums.NivelAtividadeFisica;
import br.com.macrosxtreme.enums.Objetivo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Macros {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_macros")
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Objetivo objetivo;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private NivelAtividadeFisica nivelAtividadeFisica;
	
	@Column(name="data_calculo", nullable = false)
	private String dataCalculo;
	
	@Column(nullable = false)
	private String imc;
	
	@Column(name="taxa_metabolica_basal", nullable = false)
	private Integer tmb;
	
	@Column(name="gasto_calorico_total", nullable = false)
	private Integer gastoCaloricoTotal;

	@Column(name="calorias_treino", nullable = false)
	private Integer caloriasTreino;
	
	@Column(name="proteina_treino", nullable = false)
	private Integer proteinaTreino;
	
	@Column(name="carboidrato_treino", nullable = false)
	private Integer carboidratoTreino;
	
	@Column(name="gordura_treino", nullable = false)
	private Integer gorduraTreino;
	
	@Column(name="fibra_treino", nullable = false)
	private Integer fibraTreino;
	
	@Column(name="calorias_descanso", nullable = false)
	private Integer caloriasDescanso;
	
	@Column(name="proteina_descanso", nullable = false)
	private Integer proteinaDescanso;
	
	@Column(name="carboidrato_descanso", nullable = false)
	private Integer carboidratoDescanso;
	
	@Column(name="gordura_descanso", nullable = false)
	private Integer gorduraDescanso;
	
	@Column(name="fibra_descanso", nullable = false)
	private Integer fibraDescanso;

	@ManyToOne()
	@JoinColumn(name="cod_paciente_fk", nullable = false)
	private Paciente paciente;
	
	public Macros(MacrosDTO historico) {
		this.id = historico.getId();
		this.objetivo = historico.getObjetivo();
		this.nivelAtividadeFisica = historico.getNivelAtividadeFisica();
		this.dataCalculo = historico.getDataCalculo();
		this.imc = historico.getImc();
		this.tmb = historico.getTmb();
		this.gastoCaloricoTotal = historico.getGastoCaloricoTotal();
		this.caloriasTreino = historico.getCaloriasTreino();
		this.proteinaTreino = historico.getProteinaTreino();
		this.carboidratoTreino = historico.getCarboidratoTreino();
		this.gorduraTreino = historico.getGorduraTreino();
		this.fibraTreino = historico.getFibraTreino();
		this.caloriasDescanso = historico.getCaloriasDescanso();
		this.proteinaDescanso = historico.getProteinaDescanso();
		this.carboidratoDescanso = historico.getCarboidratoDescanso();
		this.gorduraDescanso = historico.getGorduraDescanso();
		this.fibraDescanso = historico.getFibraDescanso();
	}
}
