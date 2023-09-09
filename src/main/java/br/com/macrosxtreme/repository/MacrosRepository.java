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
			"WHERE COD_MACROS = ?1 " +
			"ORDER BY COD_MACROS DESC " +
			"LIMIT 1", nativeQuery = true)
	Macros buscarMacrosAtual(Long id);

	@Query(value="SELECT MIN(COD_MACROS) " +
			"FROM MACROS " +
			"WHERE COD_PACIENTE_FK = ?1 ", nativeQuery = true)
	Long buscarRegistroMaisAntigo(Long id);

	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE COD_PACIENTE_FK = ?1 " +
			"ORDER BY COD_MACROS DESC " +
			"LIMIT 1", nativeQuery = true)
	Macros buscarMacrosAtualPorIdPaciente(Long id);

	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE COD_PACIENTE_FK = ?1 " +
			"AND COD_MACROS < (SELECT MAX(COD_MACROS) FROM MACROS) " +
			"ORDER BY COD_MACROS DESC", nativeQuery = true)
	List<Macros> buscarMacrosPacienteIgnorandoMacrosAtual(Long id);

	@Query(value="SELECT * " +
			"FROM MACROS " +
			"WHERE COD_PACIENTE_FK = ?1 ", nativeQuery = true)
	List<Macros> buscarTodosMacrosPaciente(Long id);
}
