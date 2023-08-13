package br.com.macrosxtreme.repository;

import br.com.macrosxtreme.model.Macros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MacrosRepository extends JpaRepository<Macros, Long>{
	
	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE COD_PACIENTE = ?1 " +
			"ORDER BY COD_MACROS DESC " +
			"LIMIT 1", nativeQuery = true)
	Macros findByMacros(Long codPaciente);
	
	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE COD_PACIENTE = ?1 " +
			"ORDER BY COD_MACROS DESC", nativeQuery = true)
	List<Macros> findByHistoricoMacros(Long codPaciente);

}
