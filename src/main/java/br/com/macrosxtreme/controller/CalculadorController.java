package br.com.macrosxtreme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.HistoricoMacrosDTO;
import br.com.macrosxtreme.dto.UsuarioFreeDTO;
import br.com.macrosxtreme.model.HistoricoMacros;
import br.com.macrosxtreme.services.CalculadorService;
import br.com.macrosxtreme.services.HistoricoMacrosService;

@RestController
@RequestMapping("/api/calculador")
public class CalculadorController {
	
	@Autowired
	private CalculadorService calculadorService;
	
	@Autowired
	private HistoricoMacrosService historicoMacrosService;
	
	
	@GetMapping("/calcular")
	public ModelAndView calcular(UsuarioFreeDTO usuario) {
		ModelAndView modelAndView = new ModelAndView("calculador/form");
		
		return modelAndView;

	}

	@PostMapping("/macros")
	public ModelAndView macros(UsuarioFreeDTO usuario) {
		ModelAndView modelAndView = new ModelAndView("calculador/macros");
		
		String imc = calculadorService.imc(usuario.getAltura(), usuario.getPeso());
		Integer tmb =  calculadorService.calcularTBM(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso());
		Integer gastoCaloricoTotal = calculadorService.calcularGT(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getNivelAtividadeFisica());
		Integer objetivoTreino = calculadorService.objetivoTreino(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		Integer objetivoDescanso = calculadorService.objetivoDescanso(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		List<Integer> macrosTreino = calculadorService.macrosTreino(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		List<Integer> macrosDescanso = calculadorService.macrosDescanso(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		
		String nome = "Teste";
		HistoricoMacrosDTO historicoMacrosDTO = new HistoricoMacrosDTO();
		historicoMacrosDTO.setUsuario(nome);
		historicoMacrosDTO.setImc(imc);
		historicoMacrosDTO.setTmb(tmb);
		historicoMacrosDTO.setGastoCaloricoTotal(gastoCaloricoTotal);
		historicoMacrosDTO.setCaloriasTreino(objetivoTreino);
		historicoMacrosDTO.setProteinaTreino(macrosTreino.get(0));
		historicoMacrosDTO.setCarboidratoTreino(macrosTreino.get(1));
		historicoMacrosDTO.setGorduraTreino(macrosTreino.get(2));
		historicoMacrosDTO.setFibraTreino(macrosTreino.get(3));
		historicoMacrosDTO.setCaloriasDescanso(objetivoDescanso);
		historicoMacrosDTO.setProteinaDescanso(macrosDescanso.get(0));
		historicoMacrosDTO.setCarboidratoDescanso(macrosDescanso.get(1));
		historicoMacrosDTO.setGorduraDescanso(macrosDescanso.get(2));
		historicoMacrosDTO.setFibraDescanso(macrosDescanso.get(3));
		
		historicoMacrosService.salvarHistorico(historicoMacrosDTO);
		
		modelAndView.addObject("imc", imc);
		modelAndView.addObject("tmb", tmb);
		modelAndView.addObject("gastoT", gastoCaloricoTotal);
		modelAndView.addObject("objT", objetivoTreino);
		modelAndView.addObject("objOff", objetivoDescanso);
		modelAndView.addObject("macrosT", macrosTreino);
		modelAndView.addObject("macrosOff", macrosDescanso);


		return modelAndView;

	}
	
	@GetMapping("/buscar/historico")
	public void buscarHistorico(UsuarioFreeDTO usuario) {
		
		List<HistoricoMacros> historico = historicoMacrosService.buscarHistorico(usuario.getNome());
		historico.stream()
			.forEach(e -> System.out.println(e.toString()));
	}

}