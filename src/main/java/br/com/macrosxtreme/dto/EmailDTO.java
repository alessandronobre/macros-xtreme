package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailDTO {
	
	private Usuario usuario;
	private String tituloEmail;
	private String conteudo;
	private String dataEnvio;
	private String destinatario;
	private Object  anexo;
	
	public EmailDTO(HistoricoEmail email) {
		this.usuario = email.getUsuario();
		this.tituloEmail = email.getTituloEmail();
		this.conteudo = email.getConteudo();
		this.dataEnvio = email.getDataEnvio();
		this.destinatario = email.getDestinatario();
	}
	
}
