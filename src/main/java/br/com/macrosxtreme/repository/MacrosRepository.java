package br.com.macrosxtreme.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.macrosxtreme.model.HistoricoMacros;


@Repository
public interface MacrosRepository extends JpaRepository<HistoricoMacros, Long>{
	
	@Query(value="SELECT * " +
			"FROM HISTORICO_MACROS " +
			"WHERE USUARIO = ?1 " +
			"ORDER BY COD_HIST_MACROS DESC " +
			"LIMIT 1", nativeQuery = true)
	HistoricoMacros findByMacros(String usuario);
	
	@Query(value="SELECT * " +
			"FROM HISTORICO_MACROS " +
			"WHERE USUARIO = ?1 " +
			"ORDER BY COD_HIST_MACROS ", nativeQuery = true)
	List<HistoricoMacros> findByHistoricoMacros(String usuario);

}
