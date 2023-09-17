package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.repository.PacienteRepository;
import br.com.macrosxtreme.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;
	private final UsuarioRepository usuarioRepository;

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

	public List<PacienteDTO> buscarTodosPacientes(Long id) {
		List<Paciente> pacientes = pacienteRepository.buscarTodosPacientes(id);
		List<PacienteDTO> listaPacientes = new ArrayList<>();
		pacientes.forEach(paciente -> listaPacientes.add(new PacienteDTO(paciente)));
		return listaPacientes;
	}

	public void salvarPaciente(PacienteDTO dadosPaciente, UsuarioDTO usuarioLogado) {
			Paciente pacienteNovo = new Paciente(dadosPaciente);
			pacienteNovo.setUsuario(usuarioRepository.buscarUsuarioPorUsuario(usuarioLogado.getUsuario()));
			pacienteRepository.save(pacienteNovo);
	}

	public void editarPaciente(PacienteDTO dadosPaciente) {
		Paciente paciente = pacienteRepository.buscaPacientePorId(dadosPaciente.getId());
		paciente.setNome(dadosPaciente.getNome());
		paciente.setEmail(dadosPaciente.getEmail());
		paciente.setGenero(dadosPaciente.getGenero());
		paciente.setIdade(dadosPaciente.getIdade());
		paciente.setAltura(dadosPaciente.getAltura());
		paciente.setPeso(dadosPaciente.getPeso());
		pacienteRepository.save(paciente);
	}

	public void deletarPaciente(Long id) {
		pacienteRepository.delete(pacienteRepository.buscaPacientePorId(id));
	}
}
