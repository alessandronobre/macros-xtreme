package br.com.macrosxtreme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;

	public Paciente buscaPaciente(String nome) {

		return pacienteRepository.findByNome(nome);
	}

	public List<PacienteDTO> buscarPacientes() {
		List<Paciente> pacientes = pacienteRepository.findAll();

		List<PacienteDTO> listaPacientes = new ArrayList<>();
		pacientes.forEach(paciente -> listaPacientes.add(new PacienteDTO(paciente)));

		return listaPacientes;
	}

	public void save(PacienteDTO dadosPaciente) {
		Paciente paciente = new Paciente(dadosPaciente);
		pacienteRepository.save(paciente);
	}

}
