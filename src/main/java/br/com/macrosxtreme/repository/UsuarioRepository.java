package br.com.macrosxtreme.repository;

import br.com.macrosxtreme.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query(value="SELECT * FROM USUARIO WHERE EMAIL = ?1", nativeQuery = true)
	Usuario findByUser(String email);
	
	@Query(value="SELECT EMAIL FROM USUARIO WHERE EMAIL = ?1", nativeQuery = true)
	String findByEmail(String email);
	
    
	
}
