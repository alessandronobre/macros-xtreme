package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.EmailDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_email")
	private Long id;
	
	@Column(nullable = false)
	private String usuario;
	
	@Column(name="titulo_email", nullable = false)
	private String tituloEmail;
	
	@Column(nullable = false)
	private String conteudo;
	
	@Column(name="data_envio", nullable = false)
	private String dataEnvio;

	@Column(nullable = false)
	private String destinatario;
	
	public Email(EmailDTO email) {
		this.usuario = email.getUsuario();
		this.tituloEmail = email.getTituloEmail();
		this.conteudo = email.getConteudo();
		this.dataEnvio = email.getDataEnvio();
		this.destinatario = email.getDestinatario();
	}

	
}
