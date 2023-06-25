package br.com.macrosxtreme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.macrosxtreme.dto.PacienteDTO;
import br.com.macrosxtreme.model.Paciente;
import br.com.macrosxtreme.repository.PacienteRepository;

public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscaPaciente() {
        // Mocking data
        String nome = "João";
        Paciente paciente = new Paciente();
        paciente.setNome(nome);

        // Stubbing the pacienteRepository.findByNome(nome) method
        when(pacienteRepository.findByNome(nome)).thenReturn(paciente);

        // Calling the method under test
        Paciente result = pacienteService.buscaPaciente(nome);

        // Assertions
        assertNotNull(result);
        assertEquals(nome, result.getNome());
    }

    @Test
    void testBuscarPacientes() {
        // Mocking data
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(new Paciente());
        pacientes.add(new Paciente());

        // Stubbing the pacienteRepository.findAll() method
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        // Calling the method under test
        List<PacienteDTO> result = pacienteService.buscarPacientes();

        // Assertions
        assertNotNull(result);
        assertEquals(pacientes.size(), result.size());
    }

    @Test
    void testSave() {
        // Mocking data
        PacienteDTO dadosPaciente = new PacienteDTO();

        // Calling the method under test
        pacienteService.save(dadosPaciente);

        // Verifying that the pacienteRepository.save(paciente) method was called
        verify(pacienteRepository).save(any(Paciente.class));
    }

    // ... Rest of the test methods for the remaining methods in PacienteService class
}
