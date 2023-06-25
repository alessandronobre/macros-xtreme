package br.com.macrosxtreme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.repository.EmailRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindEmailUsuario() {
        Long codUsuario = 1L;
        HistoricoEmail histMacro1 = new HistoricoEmail();
        HistoricoEmail histMacro2 = new HistoricoEmail();
        List<HistoricoEmail> histMacros = new ArrayList<>();
        histMacros.add(histMacro1);
        histMacros.add(histMacro2);

        when(emailRepository.findEmailUsuario(codUsuario)).thenReturn(histMacros);

        List<EmailDTO> result = emailService.findEmailUsuario(codUsuario);

        assertEquals(2, result.size());

    }

    @Test
    void testFindEmailUsuarioEmpty() {
        Long codUsuario = 1L;

        when(emailRepository.findEmailUsuario(codUsuario)).thenReturn(new ArrayList<>());

        List<EmailDTO> result = emailService.findEmailUsuario(codUsuario);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSalvarHistoricoEmail() {
        EmailDTO email = new EmailDTO();

        emailService.salvarHistoricoEmail(email);

        verify(emailRepository).save(any(HistoricoEmail.class));
    }
}
