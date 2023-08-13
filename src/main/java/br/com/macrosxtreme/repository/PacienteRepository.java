package br.com.macrosxtreme.repository;

import br.com.macrosxtreme.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	Paciente findByNome(String paciente);
	@Query(value="SELECT EMAIL FROM PACIENTE WHERE COD_PACIENTE = ?", nativeQuery = true)
	String buscarEmailPaciente(Long pacienteId);

	@Query(value="SELECT * FROM PACIENTE ORDER BY NOME", nativeQuery = true)
	List<Paciente> findByAll();
}
