package br.com.macrosxtreme.repository;

import br.com.macrosxtreme.model.HistoricoEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<HistoricoEmail, Long> {
	
	@Query(value="SELECT * FROM HISTORICO_EMAIL WHERE COD_USUARIO = ?1 ORDER BY COD_EMAIL", nativeQuery = true)
	List<HistoricoEmail> findEmailUsuario(Long codUsuario);

	
}

