package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.service.PacienteService;
import br.com.macrosxtreme.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

	private final PacienteService pacienteService;
	private final UsuarioService usuarioService;

	@GetMapping("/lista")
	public ModelAndView listarPacientes(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("paciente/listar");
		UsuarioDTO usuarioLogado = (UsuarioDTO) request.getSession().getAttribute("user");
		List<PacienteDTO> lista = pacienteService.buscarTodosPacientes(
				usuarioService.buscarUsuarioPorUsuario(usuarioLogado.getUsuario()).getId());
		if (!lista.isEmpty()) {
			modelAndView.addObject("lista", lista);
			return modelAndView;
		}
		return modelAndView;
	}

	@GetMapping("/perfil/{id}")
	public ModelAndView perfil(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("paciente/perfil");
		PacienteDTO paciente = pacienteService.buscaPacientePorId(id);
		modelAndView.addObject("paciente", paciente);
		return modelAndView;
	}

	@GetMapping("/editar")
	public ModelAndView editar(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("paciente/formulario");
		PacienteDTO paciente = pacienteService.buscaPacientePorId(id);
		modelAndView.addObject("paciente", paciente);
		return modelAndView;
	}

	@PostMapping("/editar")
	public ModelAndView editar(PacienteDTO paciente) {
		pacienteService.editarPaciente(paciente);
		return perfil(paciente.getId());
	}

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(String msg) {
		ModelAndView modelAndView = new ModelAndView("paciente/formulario");
		modelAndView.addObject("paciente", new PacienteDTO());
		modelAndView.addObject("msg", msg);
		return modelAndView;

	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrar(HttpServletRequest request, PacienteDTO paciente) {
		if (pacienteService.validaEmail(paciente.getEmail())) {
			return cadastrar("erro");
		}
		UsuarioDTO usuarioLogado = (UsuarioDTO) request.getSession().getAttribute("user");
		pacienteService.salvarPaciente(paciente, usuarioLogado);
		return listarPacientes(request);

	}

	@GetMapping("/deletar")
	public ModelAndView deletarPaciente(HttpServletRequest request, @RequestParam("id") Long id) {
		pacienteService.deletarPaciente(id);
		return listarPacientes(request);
	}
}