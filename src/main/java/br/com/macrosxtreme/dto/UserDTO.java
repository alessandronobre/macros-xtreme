package br.com.macrosxtreme.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	
	private String name;
	private String email;
	private String password;
	
}