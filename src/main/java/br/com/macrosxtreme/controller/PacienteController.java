package br.com.macrosxtreme.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.service.PacienteService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

	private final PacienteService pacienteService;

	@GetMapping("/cadastrar")
	public ModelAndView cadastra(PacienteDTO dados) {
		ModelAndView modelAndView = new ModelAndView("paciente/form_paciente");

		return modelAndView;

	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrar(PacienteDTO dados) {
		pacienteService.save(dados);

		return buscarPacientes();

	}

	@GetMapping("/lista/pacientes")
	public ModelAndView buscarPacientes() {
		ModelAndView modelAndView = new ModelAndView("paciente/lista_pacientes");
		List<PacienteDTO> lista = pacienteService.buscarPacientes();
		if (!lista.isEmpty()) {
			modelAndView.addObject("lista", lista);

			return modelAndView;
		}
		return modelAndView;

	}

}