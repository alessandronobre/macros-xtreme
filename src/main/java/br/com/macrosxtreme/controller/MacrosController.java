package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.service.MacrosService;
import br.com.macrosxtreme.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MacrosController {

	private final MacrosService macrosService;
	private final PacienteService pacienteService;

	@GetMapping("/macros/form")
	public ModelAndView macrosForm() {
		ModelAndView modelAndView = new ModelAndView("macros/buscar_form_macros");
		List<PacienteDTO> lista = pacienteService.buscarPacientes();
		if (!lista.isEmpty()) {
			modelAndView.addObject("lista", lista);

			return modelAndView;
		}
		return modelAndView;

	}

	@GetMapping("/macros/{pacienteId}")
	public ModelAndView macros(@PathVariable Long pacienteId, HttpStatus status, String msg) {
		ModelAndView modelAndView = new ModelAndView("macros/macros");
		MacrosDTO macros = macrosService.findByMacros(pacienteId);
		modelAndView.addObject("macros", macros);
		if (status != null) {
			modelAndView.addObject("status", status.value());
			modelAndView.addObject("msg", msg);
		}
		return modelAndView;
	}

	@GetMapping("/historico/form")
	public ModelAndView findByHistoricoMacros() {
		ModelAndView modelAndView = new ModelAndView("macros/buscar_form_hist");
		List<PacienteDTO> lista = pacienteService.buscarPacientes();
		if (!lista.isEmpty()) {
			modelAndView.addObject("lista", lista);

			return modelAndView;
		}
		return modelAndView;

	}

	@GetMapping("/historico/macros")
	public ModelAndView findByHistoricoMacros(@RequestParam("nome") String nome) {
		ModelAndView modelAndView = new ModelAndView("macros/historico_macros");
		Paciente paciente = pacienteService.buscaPacientePorNome(nome);

		List<MacrosDTO> lista = macrosService.findByHistoricoMacros(paciente.getId());

		modelAndView.addObject("nome", paciente.getNome());
		modelAndView.addObject("lista", lista);

		return modelAndView;

	}

	@GetMapping("/calcular")
	public ModelAndView calcular(PacienteDTO dados) {
		ModelAndView modelAndView = new ModelAndView("macros/form");
		
		List<PacienteDTO> lista = pacienteService.buscarPacientes();
		if (!lista.isEmpty()) {
			modelAndView.addObject("lista", lista);

			return modelAndView;
		}
		return modelAndView;

	}

	@PostMapping("/calcular")
	public ModelAndView calcula(PacienteDTO dados) {
		ModelAndView modelAndView = new ModelAndView("redirect:/api/home");
		macrosService.salvarHistorico(dados);

		return modelAndView;

	}
	
	@GetMapping(value = "/download/macros", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> downloadPDF(@RequestParam("pacienteId") Long id) throws IOException {
		return macrosService.downloadPDF(id);
		
	}
	
	@GetMapping("/enviar/macros")
	public ModelAndView enviarMacrosEmail(@RequestParam("pacienteId") Long pacienteId) {
		try {
			macrosService.enviarMacrosEmail(pacienteId);
			return macros(pacienteId, HttpStatus.OK, "Envio realizado com sucesso");
			
		} catch (EmailException e) {
			log.error(e.getMessage());
			return macros(pacienteId, HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return macros(pacienteId, HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado");
		}
		
	}

}