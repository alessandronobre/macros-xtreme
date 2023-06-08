package br.com.macrosxtreme.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.dto.DadosUsuarioDTO;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.service.MacrosService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HomeController {

	private final MacrosService macrosService;

	private final MsEmailClient msEmailClient;
//
//	@GetMapping("/historico/email")
//	public ModelAndView findByHistoricoEmail(@ModelAttribute("usuario") UsuarioDTO usuario) {
//		ModelAndView modelAndView = new ModelAndView("home");
//		String nome = "Marcos";
//		ResponseEntity<List<EmailDTO>> lista = null;
//		lista = msEmailClient.findHistoricoPorUsuario(nome);
//
//		List<EmailDTO> listaHistorico = lista.getBody().stream().collect(Collectors.toList());
//
//		modelAndView.addObject("lista", listaHistorico);
//		return modelAndView;
//
//	}

//	@GetMapping("/historico/macros")
//	public void findByHistoricoMacros(UsuarioFreeDTO usuario) {
//
//		List<HistoricoMacros> historico = macrosService.findByHistorico(usuario.getNome());
//		historico.stream().forEach(e -> System.out.println(e.toString()));
//	}

}