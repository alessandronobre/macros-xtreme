package br.com.macrosxtreme.controller;

import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.service.MacrosService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/macros")
public class MacrosController {

	private final MacrosService macrosService;
    private final EmailController emailController;

    @GetMapping("/gerar")
    public ModelAndView gerar(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("macros/gerar");
        modelAndView.addObject("pacienteId", id);
        modelAndView.addObject("macros", new MacrosDTO());
        return modelAndView;
    }

    @PostMapping("/gerar")
    public RedirectView  gerar(@RequestParam("id") Long id, MacrosDTO macros) {
        macrosService.salvarMacros(macros, id);
        return new RedirectView("/api/macros/historico?id=" + id + "&msg=");

    }

    @GetMapping("/historico")
    public ModelAndView macros(@RequestParam("id") Long id, @RequestParam("msg") String msg) {
        ModelAndView modelAndView = new ModelAndView("macros/macros");
        MacrosDTO macrosAtual = macrosService.buscarMacrosAtual(id);
        List<MacrosDTO> macrosPaciente = macrosService.buscarMacrosPaciente(id);

        modelAndView.addObject("msg", msg);
        modelAndView.addObject("pacienteId", id);
        modelAndView.addObject("macrosAtual", macrosAtual);
        modelAndView.addObject("macrosPaciente", macrosPaciente);
        return modelAndView;
    }

    @GetMapping("/deletar")
    public RedirectView deletar(@RequestParam("id") Long id) {
        macrosService.deletar(id);
        return new RedirectView("/api/paciente/perfil/" + id);
    }

	@GetMapping(value = "/download", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> downloadPDF(@RequestParam("id") Long id) throws IOException {
		return macrosService.downloadPDF(id);
	}

    @GetMapping("/enviar")
    public RedirectView enviarMacrosEmail(@RequestParam("id") Long id) {
        String msg = null;
        HttpStatus resp = emailController.enviarEmail(macrosService.gerarEmailMacros(id));
        if (resp == HttpStatus.OK) {
            msg = "sucesso";
        } else {
            msg = "erro";
        }
        return new RedirectView("/api/macros/historico?id=" + id + "&msg=" + msg);
    }
}