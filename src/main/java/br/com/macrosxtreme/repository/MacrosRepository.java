package br.com.macrosxtreme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.macrosxtreme.model.Macros;


@Repository
public interface MacrosRepository extends JpaRepository<Macros, Long>{
	
	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE USUARIO = ?1 " +
			"ORDER BY COD_MACROS DESC " +
			"LIMIT 1", nativeQuery = true)
	Macros findByMacros(String usuario);
	
	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE USUARIO = ?1 " +
			"ORDER BY COD_MACROS DESC", nativeQuery = true)
	List<Macros> findByHistoricoMacros(String usuario);

}
