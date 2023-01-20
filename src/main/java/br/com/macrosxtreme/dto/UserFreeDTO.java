package br.com.macrosxtreme.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFreeDTO {
	
	private String genero;
	private int idade;
	private int altura;
	private int peso;
	private String objetivo;
	private String nivelAtividadeFisica;

}