package br.com.macrosxtreme.dto;

public class UserDTO {
	
	private String genero;
	private int idade;
	private int altura;
	private int peso;
	private String objetivo;
	private String nivelAtividadeFisica;

	public UserDTO() {
	}

	public UserDTO(String genero, int idade, int altura, int peso, String objetivo, String nivelAtividadeFisica) {
		this.genero = genero;
		this.idade = idade;
		this.altura = altura;
		this.peso = peso;
		this.objetivo = objetivo;
		this.nivelAtividadeFisica = nivelAtividadeFisica;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNivelAtividadeFisica() {
		return nivelAtividadeFisica;
	}

	public void setNivelAtividadeFisica(String nivelAtividadeFisica) {
		this.nivelAtividadeFisica = nivelAtividadeFisica;
	}
	
//	Metodo para verificar o tipo do atributo
	
	public Boolean toCheck() {
	if (objetivo instanceof String) {
		return true;
	}
	return false;
	}

}