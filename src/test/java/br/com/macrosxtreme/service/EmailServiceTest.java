package br.com.macrosxtreme.service;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.EmailDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.model.HistoricoEmail;
import br.com.macrosxtreme.repository.EmailRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

	@Mock
	private EmailRepository emailRepository;
	@Mock
	private  MsEmailClient msEmailClient;
	@InjectMocks
	private EmailService emailService;
	@BeforeEach
	void setUp() {
		emailService = new EmailService(emailRepository, msEmailClient);
	}

	@Test
	@DisplayName("Enviar email com retorno de sucesso da Api de Email")
	void testEnviarEmail_RetornandoSucesso() {
		EmailDTO email = new EmailDTO(1L, null, "Teste", "Teste", "Teste", "Teste", null);
		emailService.enviarEmail(email);

		ArgumentCaptor<String> jsonCaptor = ArgumentCaptor.forClass(String.class);
		verify(msEmailClient).enviar(jsonCaptor.capture());

		String expectedJson = new Gson().toJson(email);
		String capturedJson = jsonCaptor.getValue();
		assertEquals(expectedJson, capturedJson);
	}

	@Test
	@DisplayName("Enviar email com retorno de Exceção da Api de Email")
	void testEnviarEmail_RetornandoEmailException(){
		EmailDTO email = new EmailDTO(1L, null, "Teste", "Teste", "Teste", "Teste", null);

		doThrow(new EmailException()).when(msEmailClient).enviar(any(String.class));
		assertThrows(EmailException.class, () -> emailService.enviarEmail(email));
	}

	@Test
	@DisplayName("Busca a lista de historicoEmail e retorna sucesso")
	void testFindEmailUsuario_RetornandoListaComSucesso() {
		List<HistoricoEmail> lista = new ArrayList<>();
		lista.add(new HistoricoEmail(new EmailDTO(1L, null, "", "", "", "", null)));
		when(emailRepository.findEmailUsuario(1L)).thenReturn(lista);
		
		List<EmailDTO> findEmailUsuario = emailService.findEmailUsuario(1L);
		assertEquals(lista.get(0).getId(), findEmailUsuario.get(0).getId());
		
	}
	
	@Test
	@DisplayName("Busca a lista de historicoEmail e retorna vazia")
	void testFindEmailUsuario_RetornandoListaVazia() {
		List<HistoricoEmail> lista = new ArrayList<>();
		when(emailRepository.findEmailUsuario(1L)).thenReturn(lista);
		
		List<EmailDTO> findEmailUsuario = emailService.findEmailUsuario(1L);
		assertTrue(findEmailUsuario.isEmpty());
		
	}

	@Test
	@DisplayName("Salvar historico de email com retorno de sucesso")
	void testSalvarHistoricoEmail_RetornandoSucesso() {
		EmailDTO email = new EmailDTO(1L, null, "Teste", "Teste", "Teste", "Teste", null);

		emailService.salvarHistoricoEmail(email);
		ArgumentCaptor<HistoricoEmail> historicoCaptor = ArgumentCaptor.forClass(HistoricoEmail.class);
		verify(emailRepository).save(historicoCaptor.capture());

		HistoricoEmail capturedHistorico = historicoCaptor.getValue();
		Assertions.assertEquals(email.getId(), capturedHistorico.getId());
	}
}
