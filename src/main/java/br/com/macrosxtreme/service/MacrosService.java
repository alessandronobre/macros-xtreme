package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.enums.AtividadeFisica;
import br.com.macrosxtreme.enums.Genero;
import br.com.macrosxtreme.enums.Objetivo;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.mapper.FormatacaoStringMapper;
import br.com.macrosxtreme.mapper.GerarItensEmailMapper;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.repository.MacrosRepository;
import br.com.macrosxtreme.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MacrosService {

    private final MacrosRepository macrosRepository;
    private final PacienteRepository pacienteRepository;
	private final TemplateEngine templateEngine;
	private final DataMapper dataMapper;
    private final GerarItensEmailMapper gerarItensEmailMapper;
    private final FormatacaoStringMapper formatacaoStringMapper;

    private static final String TEMPLATE_PDF = "macros/macrospdf.html";

    public EmailDTO gerarEmailMacros(Long id) {
        Macros macros = macrosRepository.buscarMacrosAtualPorIdPaciente(id);

        return gerarItensEmailMapper.instanciaEmailDtoComAnexoPdf(macros.getPaciente().getEmail(),
                "Obá, chegaram seus macros!",
                gerarItensEmailMapper.processarTemplateHtml("macros/macros_template_email.html",
                        formatacaoStringMapper.obterPrimeiraPalavra(macros.getPaciente().getNome())),
                macros,
                "Macros.pdf",
                TEMPLATE_PDF);
    }

    public ResponseEntity<?> downloadPDF(Long id) {
        MacrosDTO macros = new MacrosDTO(macrosRepository.buscarMacrosAtual(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Macros_" +
                        formatacaoStringMapper.obterPrimeiraPalavra(macros.getPaciente().getNome()) + ".pdf")
                .body(gerarItensEmailMapper.gerarPdf(TEMPLATE_PDF, macros));
    }

    public List<MacrosDTO> buscarMacrosPaciente(Long id) {
        List<Macros> macros = macrosRepository.buscarMacrosPacienteIgnorandoMacrosAtual(id);
        if(!macros.isEmpty()) {
            List<MacrosDTO> macrosPaciente = new ArrayList<>();
            macros.stream().forEach(i -> macrosPaciente.add(new MacrosDTO(i)));
            return macrosPaciente;
        }
        return null;
    }

    public MacrosDTO buscarMacrosAtual(Long id) {
        Macros macros = macrosRepository.buscarMacrosAtualPorIdPaciente(id);
        if(macros == null) {
            return null;
        }
        MacrosDTO macrosAtual = new MacrosDTO(macros);
        return macrosAtual;
    }

    @Transactional
	public void salvarMacros(MacrosDTO dadosMacros, Long id) {
        Paciente paciente = pacienteRepository.buscaPacientePorId(id);
		String imc = imc(paciente.getAltura(), paciente.getPeso());
		Integer tmb = calcularTaxaMetabolicaBasal(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso());
		Integer gastoCaloricoTotal = calcularGastoTotal(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), dadosMacros.getAtividadeFisica());
		Integer objetivoTreino = objetivoTreino(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), dadosMacros.getObjetivo(), dadosMacros.getAtividadeFisica());
		Integer objetivoDescanso = objetivoDescanso(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), dadosMacros.getObjetivo(), dadosMacros.getAtividadeFisica());
		List<Integer> macrosTreino = macrosTreino(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), dadosMacros.getObjetivo(), dadosMacros.getAtividadeFisica());
		List<Integer> macrosDescanso = macrosDescanso(paciente.getGenero(), paciente.getIdade(),paciente.getAltura(), paciente.getPeso(), dadosMacros.getObjetivo(), dadosMacros.getAtividadeFisica());

		Macros macros = new Macros();
        macros.setObjetivo(dadosMacros.getObjetivo());
        macros.setAtividadeFisica(dadosMacros.getAtividadeFisica());
		macros.setDataCalculo(dataMapper.formatador());
		macros.setImc(imc);
		macros.setTmb(tmb);
		macros.setGastoCaloricoTotal(gastoCaloricoTotal);
		macros.setCaloriasTreino(objetivoTreino);
		macros.setProteinaTreino(macrosTreino.get(0));
		macros.setCarboidratoTreino(macrosTreino.get(1));
		macros.setGorduraTreino(macrosTreino.get(2));
		macros.setFibraTreino(macrosTreino.get(3));
		macros.setCaloriasDescanso(objetivoDescanso);
		macros.setProteinaDescanso(macrosDescanso.get(0));
		macros.setCarboidratoDescanso(macrosDescanso.get(1));
		macros.setGorduraDescanso(macrosDescanso.get(2));
		macros.setFibraDescanso(macrosDescanso.get(3));
        macros.setPaciente(paciente);

        List<MacrosDTO> macrosList = buscarMacrosPaciente(id);
        if (macrosList != null && macrosList.size() == 5) {
            macrosRepository.deleteById(macrosRepository.buscarRegistroMaisAntigo(id));
        }
        macrosRepository.save(macros);
	}

    public void deletar(Long id) {
        macrosRepository.deleteAll(macrosRepository.buscarTodosMacrosPaciente(id));
    }

    private Integer calcularTaxaMetabolicaBasal(Genero genero, int idade, int altura, int peso) {
        int tmb = 0;
        if (genero.equals(Genero.MASCULINO)) {
            double i = (10 * peso) + (6.25 * altura) - (5 * idade) + 5;
            tmb = (int) Math.round(i);
            return tmb;
        } else if (genero.equals(Genero.FEMININO)) {
            double i = (10 * peso) + (6.25 * altura) - (5 * idade) - 161;
            tmb = (int) Math.round(i);
            return tmb;
        }
        return tmb;
    }

    private Integer calcularGastoTotal(Genero genero, int idade, int altura, int peso, AtividadeFisica nivelAtividade) {
        double tmb = calcularTaxaMetabolicaBasal(genero, idade, altura, peso);
        double i = 0;
        if (genero.equals(Genero.MASCULINO)) {
            switch (nivelAtividade.getValor()) {
                case 1: {
                    i = tmb * 1.2;
                }
                break;
                case 2: {
                    i = tmb * 1.375;
                }
                break;
                case 3: {
                    i = tmb * 1.55;
                }
                break;
                case 4: {
                    i = tmb * 1.725;
                }
                break;
                case 5: {
                    i = tmb * 1.9;
                }
            }

        } else {
            switch (nivelAtividade.getValor()) {
                case 1: {
                    i = tmb * 1.2;
                }
                break;
                case 2: {
                    i = tmb * 1.375;
                }
                break;
                case 3: {
                    i = tmb * 1.55;
                }
                break;
                case 4: {
                    i = tmb * 1.725;
                }
                break;
                case 5: {
                    i = tmb * 1.9;
                }
            }
        }
        int gastoTotal = (int) Math.round(i);
        return gastoTotal;
    }

    private Integer objetivoTreino(Genero genero, int idade, int altura, int peso, Objetivo objetivo, AtividadeFisica nivelAtividade) {
        double i = 0, gastoTotal;
        gastoTotal = calcularGastoTotal(genero, idade, altura, peso, nivelAtividade);
        if (objetivo.equals(Objetivo.EMAGRECIMENTO)) {
            i = gastoTotal - (25 * gastoTotal / 100);
        } else {
            i = gastoTotal + 200;
        }
        int objetivoTreino = (int) Math.round(i);
        return objetivoTreino;
    }

    private Integer objetivoDescanso(Genero genero, int idade, int altura, int peso, Objetivo objetivo, AtividadeFisica nivelAtividade) {
        int objetivoTreino = objetivoTreino(genero, idade, altura, peso, objetivo, nivelAtividade);
        double objetivoOff = objetivoTreino - (10 * objetivoTreino / 100);
        int objetivoDescanso = (int) Math.round(objetivoOff);
        return objetivoDescanso;
    }

    private List<Integer> macrosTreino(Genero genero, int idade, int altura, int peso, Objetivo objetivo, AtividadeFisica nivelAtividade) {
        int objtivo = objetivoTreino(genero, idade, altura, peso, objetivo, nivelAtividade);
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

    private List<Integer> macrosDescanso(Genero genero, int idade, int altura, int peso, Objetivo objetivo, AtividadeFisica nivelAtividade) {
        List<Integer> macrosTreino = macrosTreino(genero, idade, altura, peso, objetivo, nivelAtividade);
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
        return imcFormatado + "%";
    }
}
