package br.com.macrosxtreme.service;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.HtmlConverter;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.repository.MacrosRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MacrosService {

	private final MacrosRepository macrosRepository;
	private final TemplateEngine templateEngine;
	private final EmailService emailService;
	private final DataMapper dataMapper;
	private final PacienteService pacienteService;
	
	public void enviarMacrosEmail(Long id) {
		EmailDTO email = new EmailDTO();
		email.setTituloEmail("Bem vindo");
		email.setDestinatario("alessandronobre.ti@gmail.com");
		email.setDataEnvio(dataMapper.formatador());
		email.setConteudo("Segue em anexo seus macros");
		MacrosDTO macros = new MacrosDTO(macrosRepository.findByMacros(id));
		macros.setNome(macros.getPaciente().getNome());
		email.setAnexo(macros);
		
		try {
			emailService.enviarEmail(email);
			
		} catch (Exception e) {
			throw new EmailException();
		}
		
	}
	
	public ResponseEntity<?> downloadPDF(Long id) {
		MacrosDTO macros = new MacrosDTO(macrosRepository.findByMacros(id));
		
		Context context = new Context();
		context.setVariable("macros", macros);

		String html = templateEngine.process("macros/macrospdf.html", context);
		
		ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
		HtmlConverter.convertToPdf(html, pdfStream);
		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Macros.pdf")
					.body(pdfStream.toByteArray());
	}

	public void salvarHistorico(PacienteDTO dados) {
		
		String imc = imc(dados.getAltura(), dados.getPeso());
		Integer tmb = calcularTBM(dados.getGenero(), dados.getIdade(), dados.getAltura(), dados.getPeso());
		Integer gastoCaloricoTotal = calcularGT(dados.getGenero(), dados.getIdade(), dados.getAltura(), dados.getPeso(), dados.getNivelAtividadeFisica());
		Integer objetivoTreino = objetivoTreino(dados.getGenero(), dados.getIdade(), dados.getAltura(), dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());
		Integer objetivoDescanso = objetivoDescanso(dados.getGenero(), dados.getIdade(), dados.getAltura(), dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());
		List<Integer> macrosTreino = macrosTreino(dados.getGenero(), dados.getIdade(), dados.getAltura(), dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());
		List<Integer> macrosDescanso = macrosDescanso(dados.getGenero(), dados.getIdade(),dados.getAltura(), dados.getPeso(), dados.getObjetivo(), dados.getNivelAtividadeFisica());

		Macros historico = new Macros();
		historico.setPaciente(pacienteService.buscaPacientePorNome(dados.getNome()));
		historico.setDataCalculo(dataMapper.formatador());
		historico.setImc(imc);
		historico.setTmb(tmb);
		historico.setGastoCaloricoTotal(gastoCaloricoTotal);
		historico.setCaloriasTreino(objetivoTreino);
		historico.setProteinaTreino(macrosTreino.get(0));
		historico.setCarboidratoTreino(macrosTreino.get(1));
		historico.setGorduraTreino(macrosTreino.get(2));
		historico.setFibraTreino(macrosTreino.get(3));
		historico.setCaloriasDescanso(objetivoDescanso);
		historico.setProteinaDescanso(macrosDescanso.get(0));
		historico.setCarboidratoDescanso(macrosDescanso.get(1));
		historico.setGorduraDescanso(macrosDescanso.get(2));
		historico.setFibraDescanso(macrosDescanso.get(3));
		
		macrosRepository.save(historico);
	}

	public MacrosDTO findByMacros(Long codPaciente) {
		Macros hist = macrosRepository.findByMacros(codPaciente);
		if(hist == null) {
			return null;
		}
		MacrosDTO historico = new MacrosDTO(hist);
	
		return historico;

	}

	public List<MacrosDTO> findByHistoricoMacros(Long codPaciente) {
		List<Macros> histMacros = macrosRepository.findByHistoricoMacros(codPaciente);
		List<MacrosDTO> historico = new ArrayList<>();

		if (!histMacros.isEmpty()) {
			histMacros.forEach(histMacro -> historico.add(new MacrosDTO(histMacro)));

			return historico;
		}

		return null;
	}

	private Integer calcularTBM(String genero, int idade, int altura, int peso) {
		int tmb = 0;
		if (genero.equals("Masculino")) {
			double i = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
			tmb = (int) Math.round(i);
			return tmb;
		} else if (genero.equals("Feminino")) {
			double i = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
			tmb = (int) Math.round(i);
			return tmb;

		}
		return tmb;
	}

	private Integer calcularGT(String genero, int idade, int altura, int peso, String nivelAtividadeFisica) {
		double tmb = calcularTBM(genero, idade, altura, peso);
		double i = 0;

		if (genero.equals("Masculino")) {
			switch (nivelAtividadeFisica) {
			case "nivel1": {
				i = tmb * 1.2;
			}
				break;
			case "nivel2": {
				i = tmb * 1.375;
			}
				break;
			case "nivel3": {
				i = tmb * 1.55;
			}
				break;
			case "nivel4": {
				i = tmb * 1.725;
			}
				break;
			case "nivel5": {
				i = tmb * 1.9;
			}
				break;
			default:
				throw new IllegalArgumentException("Valor não existe");
			}

		} else if (genero.equals("Feminino")) {

			switch (nivelAtividadeFisica) {
			case "nivel1": {
				i = tmb * 1.2;
			}
				break;
			case "nivel2": {
				i = tmb * 1.375;
			}
				break;
			case "nivel3": {
				i = tmb * 1.55;
			}
				break;
			case "nivel4": {
				i = tmb * 1.725;
			}
				break;
			case "nivel5": {
				i = tmb * 1.9;
			}
				break;
			default:
				throw new IllegalArgumentException("Valor não existe ");
			}
		}

		int gastoTotal = (int) Math.round(i);
		return gastoTotal;
	}

	private Integer objetivoTreino(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		double i = 0, gastoTotal;
		gastoTotal = calcularGT(genero, idade, altura, peso, nivelAtividadeFisica);

		if (objetivo.equals("Emagrecimento")) {
			i = gastoTotal - (25 * gastoTotal / 100);

		} else if (objetivo.equals("Ganho")) {
			i = gastoTotal + 200;

		}
		int objetivoTreino = (int) Math.round(i);
		return objetivoTreino;

	}

	private Integer objetivoDescanso(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		int objetivoTreino = objetivoTreino(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);

		double objetivoOff = objetivoTreino - (10 * objetivoTreino / 100);
		int objetivoDescanso = (int) Math.round(objetivoOff);

		return objetivoDescanso;

	}

	private List<Integer> macrosTreino(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		int objtivo = objetivoTreino(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);

		int proteina, gordura, carboidatro, fibras = 0;
		double p = peso * 2.240;
		double g = peso * 0.760;
		double c = (objtivo - (p * 4) - (g * 9)) / 4;

		if (objtivo <= 1200) {
			fibras = 10;

		} else if (objtivo > 1200 && objtivo <= 2200) {
			fibras = 20;

		} else if (objtivo > 2200 && objtivo <= 3200) {
			fibras = 30;

		} else if (objtivo > 3200 && objtivo <= 4200) {
			fibras = 40;
		}

		proteina = (int) Math.round(p);
		gordura = (int) Math.round(g);
		carboidatro = (int) Math.round(c);

		List<Integer> macros = new ArrayList<>();
		macros.add(proteina);
		macros.add(carboidatro);
		macros.add(gordura);
		macros.add(fibras);

		return macros;
	}

	private List<Integer> macrosDescanso(String genero, int idade, int altura, int peso, String objetivo,
			String nivelAtividadeFisica) {
		List<Integer> macrosTreino = macrosTreino(genero, idade, altura, peso, objetivo, nivelAtividadeFisica);

		int proteina, gordura, carboidatro, fibras = 0;

		proteina = macrosTreino.get(0);
		carboidatro = macrosTreino.get(1) - (20 * macrosTreino.get(1) / 100);
		gordura = macrosTreino.get(2) - (9 * macrosTreino.get(2) / 100);
		fibras = macrosTreino.get(3);

		List<Integer> macros = new ArrayList<>();
		macros.add(proteina);
		macros.add(carboidatro);
		macros.add(gordura);
		macros.add(fibras);

		return macros;
	}

	private String imc(int altura, int peso) {
		DecimalFormat conversor = new DecimalFormat("#,##");

		String formatAltura = conversor.format(altura);
		double converterAltura = Double.parseDouble(formatAltura);

		String formatImc = conversor.format(peso / (converterAltura * converterAltura));
		double imcFormatado = Double.parseDouble(formatImc);

		String situacao = null;

		if (imcFormatado < 17) {
			situacao = "Muito abaixo do peso";
		} else if (imcFormatado >= 17 && imcFormatado <= 18.5) {
			situacao = "Abaixo do peso";
		} else if (imcFormatado >= 18.6 && imcFormatado <= 24.9) {
			situacao = "Peso normal";
		} else if (imcFormatado >= 25 && imcFormatado <= 29.9) {
			situacao = "Acima do peso";
		} else if (imcFormatado >= 30 && imcFormatado <= 34.9) {
			situacao = "Obesidade 1";
		} else if (imcFormatado >= 35 && imcFormatado <= 39.9) {
			situacao = "Obesidade 2 (Severa)";
		} else if (imcFormatado >= 40) {
			situacao = "Obesidade 3 (Mórbida)";
		}

		return imcFormatado + "%" + " (" + situacao + ")";
	}

}
