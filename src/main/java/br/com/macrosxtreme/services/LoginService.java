package br.com.macrosxtreme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.LoginDTO;
import br.com.macrosxtreme.dto.UserDTO;
import br.com.macrosxtreme.model.User;
import br.com.macrosxtreme.repositories.UserRepository;

@Service
public class LoginService {

	@Autowired
	UserRepository userRepository;

	public LoginDTO login(LoginDTO loginDTO) {

		User toCheck = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		if(toCheck == null) {
			return null;
		}
		LoginDTO userToCheck = new LoginDTO(toCheck);
		return userToCheck;

	}
	
	public void save(UserDTO userDTO) {
		User user = new User(userDTO);
		userRepository.save(user);

	}
	

}
