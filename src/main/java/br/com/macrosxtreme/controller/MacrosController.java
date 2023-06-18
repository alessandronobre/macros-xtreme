package br.com.macrosxtreme.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.service.MacrosService;
import br.com.macrosxtreme.service.PacienteService;
import lombok.RequiredArgsConstructor;

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

	@GetMapping("/macros/{id}")
	public ModelAndView macros(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("macros/macros");

		MacrosDTO macros = macrosService.findByMacros(id);
		if (macros != null) {
			modelAndView.addObject("data", macros.getDataCalculo());
			modelAndView.addObject("imc", macros.getImc());
			modelAndView.addObject("imc", macros.getImc());
			modelAndView.addObject("tmb", macros.getTmb());
			modelAndView.addObject("gastoTotal", macros.getGastoCaloricoTotal());
			modelAndView.addObject("caloriasTreino", macros.getCaloriasTreino());
			modelAndView.addObject("caloriasDescanso", macros.getCaloriasDescanso());
			modelAndView.addObject("proteinaTreino", macros.getProteinaTreino());
			modelAndView.addObject("carboTreino", macros.getCarboidratoTreino());
			modelAndView.addObject("gorduraTreino", macros.getGorduraTreino());
			modelAndView.addObject("fibraTreino", macros.getFibraTreino());
			modelAndView.addObject("proteinaDescanso", macros.getProteinaDescanso());
			modelAndView.addObject("carboDescanso", macros.getCarboidratoDescanso());
			modelAndView.addObject("gorduraDescanso", macros.getGorduraDescanso());
			modelAndView.addObject("fibraDescanso", macros.getFibraDescanso());

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
		Paciente paciente = pacienteService.buscaPaciente(nome);

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
		
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		String dataHoraFormatada = dataHoraAtual.format(formatador);

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
		historicoMacros.setPaciente(pacienteService.buscaPaciente(dados.getNome()));
		historicoMacros.setDataCalculo(dataHoraFormatada);
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

}