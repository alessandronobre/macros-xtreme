package br.com.macrosxtreme.dto;

import br.com.macrosxtreme.model.Usuario;
import lombok.Data;

@Data
public class EmailDTO {

    private String destinatario;
    private String titulo;
    private String conteudo;
    private String nomeAnexo;
    private byte[] anexo;

}
