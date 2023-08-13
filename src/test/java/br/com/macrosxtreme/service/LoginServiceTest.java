package br.com.macrosxtreme.service;

import br.com.macrosxtreme.client.MsEmailClient;
import br.com.macrosxtreme.dto.UsuarioDTO;
import br.com.macrosxtreme.exception.EmailException;
import br.com.macrosxtreme.mapper.DataMapper;
import br.com.macrosxtreme.model.Usuario;
import br.com.macrosxtreme.repository.UsuarioRepository;
import br.com.macrosxtreme.utils.SenhaUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private MsEmailClient msEmailClient;
    @InjectMocks
    private LoginService loginService;
    @Mock
    private DataMapper dataMapper;
    @BeforeEach
    void setUp() {
        loginService = new LoginService(usuarioRepository, msEmailClient, dataMapper);
    }

    @Test
    @DisplayName("Teste de login retornando sucesso para as credenciais fornecidas")
    void testLogin_RetornandoLoginValido() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("password123");

        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword(SenhaUtils.passwordEncoder().encode("password123"));
        when(usuarioRepository.findByUser(anyString())).thenReturn(usuario);

        Boolean resultado = loginService.login(usuarioDTO);
        Assertions.assertTrue(resultado);
    }

    @Test
    @DisplayName("Teste de login retornando credenciais invalidas")
    void testLogin_RetornandoLoginInvalido() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("wrongpassword");

        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword(SenhaUtils.passwordEncoder().encode("password123"));
        when(usuarioRepository.findByUser(anyString())).thenReturn(usuario);

        Boolean resultado = loginService.login(usuarioDTO);
        Assertions.assertFalse(resultado);
    }

    @Test
    @DisplayName("Teste de email retornando email valido")
    void testValidaEmail_RetornandoSucesso() {
        String email = "test@example.com";
        when(usuarioRepository.findByEmail(anyString())).thenReturn(email);

        Boolean resultado = loginService.validaEmail(email);
        Assertions.assertTrue(resultado);
    }

    @Test
    @DisplayName("Teste de email retornando email invalido")
    void testValidaEmail_RetornandoInvalido() {
        String email = "test@example.com";
        when(usuarioRepository.findByEmail(anyString())).thenReturn(null);

        Boolean resultado = loginService.validaEmail(email);
        Assertions.assertFalse(resultado);
    }

    @Test
    @DisplayName("Teste ao tentar salvar usuario com sucesso")
    void testSalvar_ComRetornodeSucesso() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Test User");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("password123");
        when(dataMapper.formatador()).thenReturn("formattedDate");
        loginService.salvar(usuarioDTO);

        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioCaptor.capture());
        Usuario capturedHistorico = usuarioCaptor.getValue();

        assertEquals(usuarioDTO.getEmail(), capturedHistorico.getEmail());
    }

    @Test
    @DisplayName("Teste ao tentar salvar usuario com falha no envio de email")
    void testSalvar_ErroEnvioEmail() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Test User");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("password123");
        when(dataMapper.formatador()).thenReturn("formattedDate");
        doThrow(new EmailException()).when(msEmailClient).enviar(anyString());

        assertThrows(EmailException.class, () -> loginService.salvar(usuarioDTO));
    }
}
