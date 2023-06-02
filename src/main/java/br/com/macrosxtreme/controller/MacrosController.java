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
import br.com.macrosxtreme.service.MacrosService;

@RestController
@RequestMapping("/api")
public class MacrosController {
	
	@Autowired
	private MacrosService macrosService;
	
	@GetMapping("/macros")
	public ModelAndView macros(UsuarioFreeDTO usuario) {
	ModelAndView modelAndView = new ModelAndView("macros/macros");
		
		String nome = "Teste";
		HistoricoMacros macros = macrosService.findByMacros(nome);
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
	
	@GetMapping("/historico/macros")
	public ModelAndView findByHistoricoMacros() {
		ModelAndView modelAndView = new ModelAndView("macros/historico_macros");
		
		String nome = "Teste";
		List<HistoricoMacrosDTO> lista = macrosService.findByHistoricoMacros(nome);

		modelAndView.addObject("lista", lista);
		return modelAndView;

	}
	
	@GetMapping("/calcular")
	public ModelAndView calcular(UsuarioFreeDTO usuario) {
		ModelAndView modelAndView = new ModelAndView("macros/form");
		
		return modelAndView;

	}

	@PostMapping("/calcular")
	public ModelAndView calcula(UsuarioFreeDTO usuario) {

		String imc = macrosService.imc(usuario.getAltura(), usuario.getPeso());
		Integer tmb =  macrosService.calcularTBM(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso());
		Integer gastoCaloricoTotal = macrosService.calcularGT(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getNivelAtividadeFisica());
		Integer objetivoTreino = macrosService.objetivoTreino(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		Integer objetivoDescanso = macrosService.objetivoDescanso(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		List<Integer> macrosTreino = macrosService.macrosTreino(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		List<Integer> macrosDescanso = macrosService.macrosDescanso(usuario.getGenero(), usuario.getIdade(), usuario.getAltura(), usuario.getPeso(), usuario.getObjetivo(), usuario.getNivelAtividadeFisica());
		
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
		
		macrosService.salvarHistorico(historicoMacrosDTO);
		
		return macros(usuario);

	}

}