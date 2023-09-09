package br.com.macrosxtreme.repository;

import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	@Query(value = "SELECT * FROM PACIENTE WHERE COD_PACIENTE = ?", nativeQuery = true)
	Paciente buscaPacientePorId(Long id);

	@Query(value = "SELECT EMAIL FROM PACIENTE WHERE EMAIL = ?", nativeQuery = true)
	String buscarEmailPaciente(String email);

	@Query(value = "SELECT * FROM PACIENTE ORDER BY NOME", nativeQuery = true)
	List<Paciente> buscarTodosPacientes();
}