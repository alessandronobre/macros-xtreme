package br.com.macrosxtreme.model;

import br.com.macrosxtreme.dto.EmailDTO;
import jakarta.persistence.*;
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
		this.id = email.getId();
		this.usuario = email.getUsuario();
		this.tituloEmail = email.getTituloEmail();
		this.conteudo = email.getConteudo();
		this.dataEnvio = email.getDataEnvio();
		this.destinatario = email.getDestinatario();
	}

	
}
