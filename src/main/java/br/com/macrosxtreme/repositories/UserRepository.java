package br.com.macrosxtreme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.macrosxtreme.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(value="SELECT * FROM USER U WHERE  U.email = ?1", nativeQuery = true)
	User findByUser(String email);
	
	@Query(value="SELECT U.EMAIL FROM USER U WHERE  U.email = ?1", nativeQuery = true)
	String findByEmail(String email);
	
    
	
}
