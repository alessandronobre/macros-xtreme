package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
	
	private Long id;
	private Usuario usuario;
	private String tituloEmail;
	private String conteudo;
	private String dataEnvio;
	private String destinatario;
	private Object  anexo;
	
	public EmailDTO(HistoricoEmail email) {
		this.id = email.getId();
		this.usuario = email.getUsuario();
		this.tituloEmail = email.getTituloEmail();
		this.conteudo = email.getConteudo();
		this.dataEnvio = email.getDataEnvio();
		this.destinatario = email.getDestinatario();
	}
	
}
