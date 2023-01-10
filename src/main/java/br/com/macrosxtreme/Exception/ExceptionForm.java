package br.com.macrosxtreme.Exception;

import br.com.macrosxtreme.dto.UserDTO;

public class ExceptionForm {

	public String error(UserDTO user) {

		if (user.getIdade() <= 0 || user.toCheck()) {

		}
		return null;

	}

}
