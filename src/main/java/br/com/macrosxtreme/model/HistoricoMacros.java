package br.com.macrosxtreme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class HistoricoMacros {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_hist_macros")
	private Long id;
	
	@Column(nullable = false)
	private String usuario;
	
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

}
