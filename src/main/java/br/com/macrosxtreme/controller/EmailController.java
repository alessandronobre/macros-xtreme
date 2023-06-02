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
import br.com.macrosxtreme.dto.HistoricoEmailDTO;
import br.com.macrosxtreme.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmailController {

	private final MsEmailClient msEmailClient;
	
	@GetMapping("/historico")
	public ModelAndView findByHistoricoEmail(@ModelAttribute("usuario") UsuarioDTO usuario) {
		ModelAndView modelAndView = new ModelAndView("email/historico_email");
		String nome = "Marcos";
		ResponseEntity<List<HistoricoEmailDTO>> lista = null;
		lista = msEmailClient.findHistoricoPorUsuario(nome);

		List<HistoricoEmailDTO> listaHistorico = lista.getBody().stream().collect(Collectors.toList());

		modelAndView.addObject("lista", listaHistorico);
		return modelAndView;

	}

}
