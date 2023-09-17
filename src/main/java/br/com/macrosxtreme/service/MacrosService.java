package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.enums.Genero;
import br.com.macrosxtreme.enums.NivelAtividadeFisica;
import br.com.macrosxtreme.enums.Objetivo;
import br.com.macrosxtreme.interfaces.CalculosMacros;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MacrosService implements CalculosMacros {

    private final MacrosRepository macrosRepository;
    private final PacienteRepository pacienteRepository;
	private final DataMapper dataMapper;
    private final GerarItensEmailMapper gerarItensEmailMapper;
    private final FormatacaoStringMapper formatacaoStringMapper;

    private static final String TEMPLATE_PDF = "macros/macrospdf.html";

    public EmailDTO gerarEmailMacros(Long id) {
        Macros macros = macrosRepository.buscarMacrosAtualPorIdPaciente(id);
        return gerarItensEmailMapper.instanciaEmailDtoComAnexoPdf(macros.getPaciente().getEmail(),
                "Obá, chegaram seus macros!",
                gerarItensEmailMapper.processarTemplateHtml("/macros/macros_template_email.html",
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
	public void salvarMacros(MacrosDTO macros, Long id) {
        excluirMacrosMaisAntigoSeLimiteAtingido(id);
        macrosRepository.save(gerarNovosMacros(macros, id));
	}

    private void excluirMacrosMaisAntigoSeLimiteAtingido(Long id) {
        List<MacrosDTO> macrosPaciente = buscarMacrosPaciente(id);
        if (macrosPaciente != null && macrosPaciente.size() == 5) {
            macrosRepository.deleteById(macrosRepository.buscarRegistroMaisAntigo(id));
        }
    }

    private Macros gerarNovosMacros(MacrosDTO macros, Long id) {
        Paciente paciente = pacienteRepository.buscaPacientePorId(id);
        String imc = calcularImc(paciente.getAltura(), paciente.getPeso());
        Integer taxaMetabolicaBasal = calcularTaxaMetabolicaBasal(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso());
        Integer gastoCaloricoTotal = calcularGastoTotalCalorias(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), macros.getNivelAtividadeFisica());
        Integer proteina = calcularProteina(paciente.getPeso());
        Integer caloriasTreino = calcularCaloriasTreino(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), macros.getObjetivo(), macros.getNivelAtividadeFisica());
        Integer gorduraTreino = calcularGorduraTreino(paciente.getPeso());
        Integer carboidratoTreino = calcularCarboidratoTreino(caloriasTreino, proteina, gorduraTreino);
        Integer caloriasDescanso = calcularCaloriasDescanso(paciente.getGenero(), paciente.getIdade(), paciente.getAltura(), paciente.getPeso(), macros.getObjetivo(), macros.getNivelAtividadeFisica());
        Integer gorduraDescanso = calcularGorduraDescanso(gorduraTreino);
        Integer carboidratoDescanso = calcularCarboidratoDescanso(carboidratoTreino);
        Integer fibra = calcularFibra(caloriasTreino);

        Macros macrosNovos = new Macros();
        macrosNovos.setObjetivo(macros.getObjetivo());
        macrosNovos.setNivelAtividadeFisica(macros.getNivelAtividadeFisica());
        macrosNovos.setDataCalculo(dataMapper.formatador());
        macrosNovos.setImc(imc);
        macrosNovos.setTmb(taxaMetabolicaBasal);
        macrosNovos.setGastoCaloricoTotal(gastoCaloricoTotal);
        macrosNovos.setCaloriasTreino(caloriasTreino);
        macrosNovos.setProteinaTreino(proteina);
        macrosNovos.setCarboidratoTreino(carboidratoTreino);
        macrosNovos.setGorduraTreino(gorduraTreino);
        macrosNovos.setFibraTreino(fibra);
        macrosNovos.setCaloriasDescanso(caloriasDescanso);
        macrosNovos.setProteinaDescanso(proteina);
        macrosNovos.setCarboidratoDescanso(carboidratoDescanso);
        macrosNovos.setGorduraDescanso(gorduraDescanso);
        macrosNovos.setFibraDescanso(fibra);
        macrosNovos.setPaciente(paciente);
        return macrosNovos;
    }

    public void deletar(Long id) {
        macrosRepository.deleteAll(macrosRepository.buscarTodosMacrosPaciente(id));
    }

    @Override
    public String calcularImc(int altura, int peso) {
        DecimalFormat conversor = new DecimalFormat("#,##");
        String formatAltura = conversor.format(altura);
        double converterAltura = Double.parseDouble(formatAltura);

        String formatImc = conversor.format(peso / (converterAltura * converterAltura));
        double imcFormatado = Double.parseDouble(formatImc);
        return imcFormatado + "%";
    }

    @Override
    public Integer calcularTaxaMetabolicaBasal(Genero genero, int idade, int altura, int peso) {
        if (genero.equals(Genero.MASCULINO)) {
            return Genero.MASCULINO.calcularTaxaMetabolicaBasal(idade, altura, peso);
        } else {
            return Genero.FEMININO.calcularTaxaMetabolicaBasal(idade, altura, peso);
        }
    }

    @Override
    public Integer calcularGastoTotalCalorias(Genero genero, int idade, int altura, int peso, NivelAtividadeFisica nivelAtividade) {
        return nivelAtividade.calcularGastoTotalCalorias(calcularTaxaMetabolicaBasal(genero, idade, altura, peso));
    }

    @Override
    public Integer calcularProteina(int peso) {
        return (int) Math.round(peso * 2.240);
    }

    @Override
    public Integer calcularFibra(Integer caloriasTreino) {
        int fibras = 0;
        if (caloriasTreino <= 1200) {
            fibras = 10;
        } else if (caloriasTreino > 1200 && caloriasTreino <= 2200) {
            fibras = 20;

        } else if (caloriasTreino > 2200 && caloriasTreino <= 3200) {
            fibras = 30;

        } else if (caloriasTreino > 3200 && caloriasTreino <= 4200) {
            fibras = 40;
        }
        return fibras;
    }

    @Override
    public Integer calcularCaloriasTreino(Genero genero, int idade, int altura, int peso, Objetivo objetivo, NivelAtividadeFisica nivelAtividade) {
        return objetivo.calcularCaloriasTreino(calcularGastoTotalCalorias(genero, idade, altura, peso, nivelAtividade));
    }

    @Override
    public Integer calcularCarboidratoTreino(Integer caloriasTreino, int proteina, int gordura) {
        return (caloriasTreino - (proteina * 4) - (gordura * 9)) / 4;
    }

    @Override
    public Integer calcularGorduraTreino(int peso) {
        return (int) Math.round(peso * 0.760);
    }

    @Override
    public Integer calcularCaloriasDescanso(Genero genero, int idade, int altura, int peso, Objetivo objetivo, NivelAtividadeFisica nivelAtividade) {
        return objetivo.calcularCaloriasDescanso(calcularCaloriasTreino(genero, idade, altura, peso, objetivo, nivelAtividade));
    }

    @Override
    public Integer calcularCarboidratoDescanso(Integer carboidratoTreino) {
        return carboidratoTreino - (20 * carboidratoTreino / 100);
    }

    @Override
    public Integer calcularGorduraDescanso(Integer gorduraTreino) {
        return gorduraTreino - (9 * gorduraTreino / 100);
    }
}
