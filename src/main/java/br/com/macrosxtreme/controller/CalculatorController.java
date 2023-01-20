package br.com.macrosxtreme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.UserFreeDTO;
import br.com.macrosxtreme.services.CalculatorService;

@Controller
@RequestMapping("/calculo")
public class CalculatorController {

	@Autowired
	private CalculatorService calculatorService;
	
	@GetMapping("/form")
	public ModelAndView form(UserFreeDTO userFreeDTO) {
		ModelAndView modelAndView = new ModelAndView("calculator/form");
		modelAndView.addObject("nivelA", calculatorService.levelActivity());
		
		return modelAndView;

	}

	@PostMapping("/result")
	public ModelAndView form2(UserFreeDTO userFreeDTO) {
		ModelAndView modelAndView = new ModelAndView("calculator/result");
		modelAndView.addObject("tmb", calculatorService.calculatorTBM(userFreeDTO.getGenero(), userFreeDTO.getIdade(), userFreeDTO.getAltura(), userFreeDTO.getPeso()));
		modelAndView.addObject("gastoT", calculatorService.calculatorGT(userFreeDTO.getGenero(), userFreeDTO.getIdade(), userFreeDTO.getAltura(), userFreeDTO.getPeso(), userFreeDTO.getNivelAtividadeFisica()));
		modelAndView.addObject("objT", calculatorService.calculatorObjTraining(userFreeDTO.getGenero(), userFreeDTO.getIdade(), userFreeDTO.getAltura(), userFreeDTO.getPeso(), userFreeDTO.getObjetivo(), userFreeDTO.getNivelAtividadeFisica()));
		modelAndView.addObject("objOff", calculatorService.calculatorObjOff(userFreeDTO.getGenero(), userFreeDTO.getIdade(), userFreeDTO.getAltura(), userFreeDTO.getPeso(), userFreeDTO.getObjetivo(), userFreeDTO.getNivelAtividadeFisica()));
		modelAndView.addObject("macrosT", calculatorService.macrosTraining(userFreeDTO.getGenero(), userFreeDTO.getIdade(), userFreeDTO.getAltura(), userFreeDTO.getPeso(), userFreeDTO.getObjetivo(), userFreeDTO.getNivelAtividadeFisica()));
		modelAndView.addObject("macrosOff", calculatorService.macrosOff(userFreeDTO.getGenero(), userFreeDTO.getIdade(), userFreeDTO.getAltura(), userFreeDTO.getPeso(), userFreeDTO.getObjetivo(), userFreeDTO.getNivelAtividadeFisica()));
		
		return modelAndView;

	}

}