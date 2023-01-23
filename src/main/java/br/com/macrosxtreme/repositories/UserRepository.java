package br.com.macrosxtreme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.macrosxtreme.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
//	Duas formas de fazer a mesma consulta
	
	@Query(value="SELECT * FROM USER U WHERE  U.NAME = :name U.EMAIL = :email AND U.PASSWORD = :password", nativeQuery = true)
	User toCheckLogin(String email, String password);
	
	User findByEmailAndPassword(String email, String password);
	
	
}
