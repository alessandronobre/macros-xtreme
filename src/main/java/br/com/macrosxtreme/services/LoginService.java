package br.com.macrosxtreme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.macrosxtreme.model.User;
import br.com.macrosxtreme.repositories.UserRepository;

@Service
public class LoginService {

	@Autowired
	UserRepository userRepository;

	public Boolean login(User user) {

		User userToCheck = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (userToCheck == null) {
			return false;
		}
		return true;

	}

}
