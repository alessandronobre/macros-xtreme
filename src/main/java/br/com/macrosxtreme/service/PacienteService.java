package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;
	public Paciente buscaPacientePorNome(String nome) {
		return pacienteRepository.findByNome(nome);
	}

	public List<PacienteDTO> buscarPacientes() {
		List<Paciente> pacientes = pacienteRepository.findByAll();

		List<PacienteDTO> listaPacientes = new ArrayList<>();
		pacientes.forEach(paciente -> listaPacientes.add(new PacienteDTO(paciente)));

		return listaPacientes;
	}

	public void save(PacienteDTO dadosPaciente) {
		Paciente paciente = new Paciente(dadosPaciente);
		pacienteRepository.save(paciente);
	}

	public String buscarEmailPaciente(Long pacienteId) {
		return pacienteRepository.buscarEmailPaciente(pacienteId);
	}
}
