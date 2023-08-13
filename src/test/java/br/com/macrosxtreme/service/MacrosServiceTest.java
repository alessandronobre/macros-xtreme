package br.com.macrosxtreme.service;

import br.com.macrosxtreme.dto.MacrosDTO;
import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.model.Macros;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.repository.MacrosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MacrosServiceTest {

    @Mock
    private MacrosRepository macrosRepository;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private EmailService emailService;
    @Mock
    private DataMapper dataMapper;
    @Mock
    private PacienteService pacienteService;
    @InjectMocks
    private MacrosService macrosService;
    @BeforeEach
    void setUp() {
        macrosService = new MacrosService(macrosRepository, templateEngine, emailService, dataMapper, pacienteService);
    }

    @Test
    @DisplayName("Teste enviar email com macros retornando sucesso ")
    void testEnviarMacrosEmail_RetornandoSucesso() {
        Paciente paciente = new Paciente();
        paciente.setNome("Teste");
        paciente.setEmail("teste@gmail.com");
        paciente.setGenero("Masculino");
        paciente.setIdade(30);
        paciente.setAltura(165);
        paciente.setPeso(65);
        paciente.setObjetivo("Emagrecimento");
        paciente.setNivelAtividadeFisica("nivel4");
        Macros macros = new Macros();
        macros.setPaciente(paciente);
        when(pacienteService.buscarEmailPaciente(1L)).thenReturn("teste@gmail.com");
        when(dataMapper.formatador()).thenReturn("formattedDate");
        when(macrosRepository.findByMacros(1L)).thenReturn(macros);

        macrosService.enviarMacrosEmail(1L);

    }

    @Test
    @DisplayName("Teste ao baixar pdf retornando sucesso")
    void testdownloadPDF_RetornandoSucesso() {
        String html = "<html><body>Sample HTML</body></html>";
        when(macrosRepository.findByMacros(1L)).thenReturn(new Macros());
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn(html);
        ResponseEntity<?> responseEntity = macrosService.downloadPDF(1L);

        verify(macrosRepository).findByMacros(1L);
        verify(templateEngine).process(eq("macros/macrospdf.html"), any(Context.class));
        HttpHeaders headers = responseEntity.getHeaders();
        Assertions.assertEquals("attachment; filename=Macros.pdf", headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

    }

    @Test
    @DisplayName("Teste salvar historico genero masculino com sucesso ")
    void testSalvarHistorico_RetornandoSucessoParaGeneroMasculino() {
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setNome("Teste");
        pacienteDTO.setEmail("teste@gmail.com");
        pacienteDTO.setGenero("Masculino");
        pacienteDTO.setIdade(20);
        pacienteDTO.setAltura(178);
        pacienteDTO.setPeso(85);
        pacienteDTO.setObjetivo("Emagrecimento");
        pacienteDTO.setNivelAtividadeFisica("nivel1");
        Paciente paciente = new Paciente();
        paciente.setNome("Teste");
        paciente.setEmail("teste@gmail.com");
        paciente.setGenero("Masculino");
        paciente.setIdade(20);
        paciente.setAltura(178);
        paciente.setPeso(85);
        paciente.setObjetivo("Emagrecimento");
        paciente.setNivelAtividadeFisica("nivel1");

        when(dataMapper.formatador()).thenReturn("formattedDate");
        when(pacienteService.buscaPacientePorNome("Teste")).thenReturn(paciente);

        macrosService.salvarHistorico(pacienteDTO);

        ArgumentCaptor<Macros> macrosCaptor = ArgumentCaptor.forClass(Macros.class);
        verify(macrosRepository).save(macrosCaptor.capture());
        Macros capturedMacros = macrosCaptor.getValue();

        assertEquals(pacienteDTO.getEmail(), capturedMacros.getPaciente().getEmail());

    }

    @Test
    @DisplayName("Teste salvar historico genero Feminino com sucesso ")
    void testSalvarHistorico_RetornandoSucessoParaGeneroFeminino() {
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setNome("Teste");
        pacienteDTO.setEmail("teste@gmail.com");
        pacienteDTO.setGenero("Feminino");
        pacienteDTO.setIdade(20);
        pacienteDTO.setAltura(178);
        pacienteDTO.setPeso(85);
        pacienteDTO.setObjetivo("Ganho");
        pacienteDTO.setNivelAtividadeFisica("nivel2");
        Paciente paciente = new Paciente();
        paciente.setNome("Teste");
        paciente.setEmail("teste@gmail.com");
        paciente.setGenero("Feminino");
        paciente.setIdade(20);
        paciente.setAltura(178);
        paciente.setPeso(85);
        paciente.setObjetivo("Ganho");
        paciente.setNivelAtividadeFisica("nivel2");

        when(dataMapper.formatador()).thenReturn("formattedDate");
        when(pacienteService.buscaPacientePorNome("Teste")).thenReturn(paciente);

        macrosService.salvarHistorico(pacienteDTO);

        ArgumentCaptor<Macros> macrosCaptor = ArgumentCaptor.forClass(Macros.class);
        verify(macrosRepository).save(macrosCaptor.capture());
        Macros capturedMacros = macrosCaptor.getValue();

        assertEquals(pacienteDTO.getEmail(), capturedMacros.getPaciente().getEmail());

    }

    @Test
    @DisplayName("Teste buscar macros com retorno de sucesso")
    void testFindByMacros_RetornandoSucesso() {
        when(macrosRepository.findByMacros(1L)).thenReturn(new Macros());

        MacrosDTO historico = macrosService.findByMacros(1L);
        assertNotNull(historico);
    }

    @Test
    @DisplayName("Teste buscar macros com retorno nulo")
    void testFindByMacros_RetornandoNulo() {
        when(macrosRepository.findByMacros(1L)).thenReturn(null);

        MacrosDTO historico = macrosService.findByMacros(1L);
        assertNull(historico);
    }

    @Test
    @DisplayName("Teste buscar macros com retorno de sucesso")
    void testFindByHistoricoMacros_RetornandoSucesso() {
        List<Macros> histMacros = new ArrayList<>();
        histMacros.add(new Macros());
        when(macrosRepository.findByHistoricoMacros(1L)).thenReturn(histMacros);

        List<MacrosDTO> historico= macrosService.findByHistoricoMacros(1L);
        assertNotNull(historico);
        assertFalse(historico.isEmpty());
    }

    @Test
    @DisplayName("Teste buscar macros com retorno Lista Vazia")
    void testFindByHistoricoMacros_RetornandoListaVazia() {
        when(macrosRepository.findByHistoricoMacros(1L)).thenReturn(new ArrayList<>());

        List<MacrosDTO> historico= macrosService.findByHistoricoMacros(1L);
        assertNull(historico);
    }
}
