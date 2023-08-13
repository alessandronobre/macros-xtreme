package br.com.macrosxtreme.exception;

public class EmailException extends RuntimeException {

	private static final long serialVersionUID = -945831520065815279L;

	private static final String ERRO_CONEXAO = "Erro ao tentar se comunicar com o serviço de email";
	
	public EmailException() {

	}

	public EmailException(String msg, String error) {

	}

	public String getMessage() {
		return ERRO_CONEXAO;

	}
}