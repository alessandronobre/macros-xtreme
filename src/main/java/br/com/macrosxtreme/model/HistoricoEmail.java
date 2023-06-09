package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.EmailDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
public class HistoricoEmail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_email")
	private Long id;
	
	@Column(name="titulo_email", nullable = false)
	private String tituloEmail;
	
	@Column(nullable = false)
	private String conteudo;
	
	@Column(name="data_envio", nullable = false)
	private String dataEnvio;

	@Column(nullable = false)
	private String destinatario;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="cod_usuario", nullable = false)
	private Usuario usuario ;
	
	public HistoricoEmail(EmailDTO email) {
		this.usuario = email.getUsuario();
		this.tituloEmail = email.getTituloEmail();
		this.conteudo = email.getConteudo();
		this.dataEnvio = email.getDataEnvio();
		this.destinatario = email.getDestinatario();
	}

	
}
