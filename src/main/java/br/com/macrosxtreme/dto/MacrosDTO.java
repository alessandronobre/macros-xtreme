package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MacrosDTO {

	private Usuario usuario;
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
	
	public MacrosDTO(Macros historico) {
		this.usuario = historico.getUsuario();
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
