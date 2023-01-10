package br.com.macrosxtreme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.dto.UserDTO;
import br.com.macrosxtreme.services.CalculatorService;

@Controller
@RequestMapping("/calculo")
public class CalculatorController {

	@Autowired
	private CalculatorService calculatorService;
	
	@GetMapping("/form")
	public ModelAndView form(UserDTO userDTO) {
		ModelAndView modelAndView = new ModelAndView("calculator/form");
		modelAndView.addObject("genero", calculatorService.typeGenre());
		modelAndView.addObject("nivelA", calculatorService.levelActivity());
		modelAndView.addObject("obj", calculatorService.goal());
		return modelAndView;

	}

	@PostMapping("/result")
	public ModelAndView form2(UserDTO userDTO) {
		ModelAndView modelAndView = new ModelAndView("calculator/result");
		modelAndView.addObject("tmbM", calculatorService.calculatorM(userDTO.getIdade(), userDTO.getAltura(), userDTO.getPeso()));
		modelAndView.addObject("tmbF", calculatorService.calculatorF(userDTO.getIdade(), userDTO.getAltura(), userDTO.getPeso()));
		modelAndView.addObject("gastoT", calculatorService.calculatorGT(userDTO.getGenero(), userDTO.getIdade(), userDTO.getAltura(), userDTO.getPeso(), userDTO.getNivelAtividadeFisica()));
		modelAndView.addObject("obj", calculatorService.calculatorObj(userDTO.getGenero(), userDTO.getIdade(), userDTO.getAltura(), userDTO.getPeso(), userDTO.getObjetivo(), userDTO.getNivelAtividadeFisica()));
		modelAndView.addObject("macros", calculatorService.macros(userDTO.getGenero(), userDTO.getIdade(), userDTO.getAltura(), userDTO.getPeso(), userDTO.getObjetivo(), userDTO.getNivelAtividadeFisica()));
		return modelAndView;

	}

}