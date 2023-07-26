package br.com.macrosxtreme.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.service.MacrosService;
import br.com.macrosxtreme.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MacrosController {

	private final MacrosService macrosService;
	private final PacienteService pacienteService;
	private final DataMapper dataMapper;

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

	@GetMapping("/macros/{id}")
	public ModelAndView macros(@PathVariable Long id, HttpStatus status, String msg) {
		ModelAndView modelAndView = new ModelAndView("macros/macros");
		MacrosDTO macros = macrosService.findByMacros(id);
		
		if (macros != null) {
			modelAndView.addObject("macros", macros);
			if (status != null) {
				modelAndView.addObject("status", status.value());
				modelAndView.addObject("msg", msg);

			}
			return modelAndView;

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

		String imc = macrosService.imc(dados.getAltura(), dados.getPeso());
		Integer tmb = macrosService.calcularTBM(dados.getGenero(), dados.getIdade(), dados.getAltura(),
				dados.getPeso());
		Integer gastoCaloricoTotal = macrosService.calcularGT(dados.getGenero(), dados.getIdade(), dados.getAltura(),
				dados.getPeso(), dados.getNivelAtividadeFisica());
		Integer objetivoTreino = macrosService.objetivoTreino(dados.getGenero(), dados.getIdade(), dados.getAltura(),
				dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());
		Integer objetivoDescanso = macrosService.objetivoDescanso(dados.getGenero(), dados.getIdade(),
				dados.getAltura(), dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());
		List<Integer> macrosTreino = macrosService.macrosTreino(dados.getGenero(), dados.getIdade(), dados.getAltura(),
				dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());
		List<Integer> macrosDescanso = macrosService.macrosDescanso(dados.getGenero(), dados.getIdade(),
				dados.getAltura(), dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());

		MacrosDTO historicoMacros = new MacrosDTO();
		historicoMacros.setPaciente(pacienteService.buscaPacientePorNome(dados.getNome()));
		historicoMacros.setDataCalculo(dataMapper.formatador());
		historicoMacros.setImc(imc);
		historicoMacros.setTmb(tmb);
		historicoMacros.setGastoCaloricoTotal(gastoCaloricoTotal);
		historicoMacros.setCaloriasTreino(objetivoTreino);
		historicoMacros.setProteinaTreino(macrosTreino.get(0));
		historicoMacros.setCarboidratoTreino(macrosTreino.get(1));
		historicoMacros.setGorduraTreino(macrosTreino.get(2));
		historicoMacros.setFibraTreino(macrosTreino.get(3));
		historicoMacros.setCaloriasDescanso(objetivoDescanso);
		historicoMacros.setProteinaDescanso(macrosDescanso.get(0));
		historicoMacros.setCarboidratoDescanso(macrosDescanso.get(1));
		historicoMacros.setGorduraDescanso(macrosDescanso.get(2));
		historicoMacros.setFibraDescanso(macrosDescanso.get(3));
		macrosService.salvarHistorico(historicoMacros);

		return modelAndView;

	}
	
	@GetMapping(value = "/download/macros", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> downloadPDF(@RequestParam("pacienteId") Long id) throws IOException {
		return macrosService.downloadPDF(id);
		
	}
	
	@GetMapping("/enviar/macros")
	public ModelAndView enviarMacrosEmail(@RequestParam("pacienteId") Long pacienteId, @RequestParam("macrosId") Long macrosId) {
		try {
			macrosService.enviarMacrosEmail(pacienteId);
			return macros(macrosId, HttpStatus.OK, "Envio realizado com sucesso");
			
		} catch (EmailException e) {
			log.error(e.getMessage());
			return macros(macrosId, HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return macros(macrosId, HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado");
		}
		
	}

}