package br.com.macrosxtreme.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioFreeDTO {
	
	private String nome;
	private String genero;
	private int idade;
	private int altura;
	private int peso;
	private String objetivo;
	private String nivelAtividadeFisica;

}