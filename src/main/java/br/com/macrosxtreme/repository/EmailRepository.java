package br.com.macrosxtreme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.macrosxtreme.model.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
	
	@Query(value="SELECT * FROM HISTORICO_EMAIL WHERE USUARIO = ?1 ORDER BY COD_HIST_EMAIL", nativeQuery = true)
	List<Email> findEmailUsuario(String usuario);

	
}

