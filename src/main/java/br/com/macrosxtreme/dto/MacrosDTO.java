package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.enums.AtividadeFisica;
import br.com.macrosxtreme.enums.Objetivo;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Paciente;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MacrosDTO {
	
	private Long id;
	private Objetivo objetivo;
	private AtividadeFisica atividadeFisica;
	private String dataCalculo;
	private String imc;
	private Integer tmb;
	private Integer gastoCaloricoTotal;
	private Integer caloriasTreino;
	private Integer proteinaTreino;
	private Integer carboidratoTreino;
	private Integer gorduraTreino;
	private Integer fibraTreino;
	private Integer caloriasDescanso;
	private Integer proteinaDescanso;
	private Integer carboidratoDescanso;
	private Integer gorduraDescanso;
	private Integer fibraDescanso;
	private PacienteDTO paciente;
	
	public MacrosDTO(Macros macros) {
		this.id = macros.getId();
		this.objetivo = macros.getObjetivo();
		this.atividadeFisica = macros.getAtividadeFisica();
		this.dataCalculo = macros.getDataCalculo();
		this.imc = macros.getImc();
		this.tmb = macros.getTmb();
		this.gastoCaloricoTotal = macros.getGastoCaloricoTotal();
		this.caloriasTreino = macros.getCaloriasTreino();
		this.proteinaTreino = macros.getProteinaTreino();
		this.carboidratoTreino = macros.getCarboidratoTreino();
		this.gorduraTreino = macros.getGorduraTreino();
		this.fibraTreino = macros.getFibraTreino();
		this.caloriasDescanso = macros.getCaloriasDescanso();
		this.proteinaDescanso = macros.getProteinaDescanso();
		this.carboidratoDescanso = macros.getCarboidratoDescanso();
		this.gorduraDescanso = macros.getGorduraDescanso();
		this.fibraDescanso = macros.getFibraDescanso();
		this.paciente = new PacienteDTO(macros.getPaciente());
	}
}
