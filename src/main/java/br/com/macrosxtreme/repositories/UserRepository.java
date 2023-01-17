package br.com.macrosxtreme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.macrosxtreme.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

}
