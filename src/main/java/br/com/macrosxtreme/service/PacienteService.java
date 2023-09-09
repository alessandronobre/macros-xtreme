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

	public Boolean validaEmail(String email) {
		String mail = pacienteRepository.buscarEmailPaciente(email);
		if (mail != null) {
			return true;
		}
		return false;
	}

	public PacienteDTO buscaPacientePorId(Long id) {
		Paciente paciente = pacienteRepository.buscaPacientePorId(id);
		return new PacienteDTO(paciente);
	}

	public List<PacienteDTO> buscarTodosPacientes() {
		List<Paciente> pacientes = pacienteRepository.buscarTodosPacientes();
		List<PacienteDTO> listaPacientes = new ArrayList<>();
		pacientes.forEach(paciente -> listaPacientes.add(new PacienteDTO(paciente)));
		return listaPacientes;
	}

	public void salvar(PacienteDTO dadosPaciente) {
		if(dadosPaciente.getId() == null) {
			Paciente pacienteNovo = new Paciente(dadosPaciente);
			pacienteRepository.save(pacienteNovo);
		} else {
			Paciente paciente = pacienteRepository.buscaPacientePorId(dadosPaciente.getId());
			paciente.setNome(dadosPaciente.getNome());
			paciente.setEmail(dadosPaciente.getEmail());
			paciente.setGenero(dadosPaciente.getGenero());
			paciente.setIdade(dadosPaciente.getIdade());
			paciente.setAltura(dadosPaciente.getAltura());
			paciente.setPeso(dadosPaciente.getPeso());
			pacienteRepository.save(paciente);
		}
	}

	public void deletar(Long id) {
		pacienteRepository.delete(pacienteRepository.buscaPacientePorId(id));
	}
}
