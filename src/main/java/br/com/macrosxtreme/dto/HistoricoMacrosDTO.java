package br.com.macrosxtreme.dto;

import lombok.Data;

@Data
public class HistoricoMacrosDTO {

	private String usuario;
	
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
}
