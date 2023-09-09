package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

	private final PacienteService pacienteService;

	@GetMapping("/lista")
	public ModelAndView listarPacientes() {
		ModelAndView modelAndView = new ModelAndView("paciente/listar");
		List<PacienteDTO> lista = pacienteService.buscarTodosPacientes();
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
		pacienteService.salvar(paciente);
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
	public ModelAndView cadastrar(PacienteDTO paciente) {
		if (pacienteService.validaEmail(paciente.getEmail())) {
			return cadastrar("erro");
		}
		pacienteService.salvar(paciente);
		return listarPacientes();

	}

	@GetMapping("/deletar")
	public ModelAndView deletar(@RequestParam("id") Long id) {
		pacienteService.deletar(id);
		return listarPacientes();
	}
}