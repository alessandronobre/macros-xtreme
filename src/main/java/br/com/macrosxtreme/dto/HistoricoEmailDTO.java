package br.com.macrosxtreme.dto;

import lombok.Data;

@Data
public class HistoricoEmailDTO {
	
	private String usuario;
	private String tituloEmail;
	private String conteudo;
	private String dataEnvio;
	private String destinatario;

}
