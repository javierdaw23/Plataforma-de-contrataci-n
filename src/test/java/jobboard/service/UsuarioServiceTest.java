package jobboard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import jobboard.entity.Usuario;
import jobboard.enums.Role;
import jobboard.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
 class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	@BeforeEach
	void setUp() {
		usuario = new Usuario();
		usuario.setEmail("test@gmail.com");
		usuario.setPassword("1234");
		usuario.setNombre("Test User");
		usuario.setRole(Role.DEVELOPER);
	}
	
	@Test
	void registrar_deberiaGuardarUsuarioCuandoEmailNoExiste() {
		
		// Arrange (preparar)
		when(usuarioRepository.existsByEmail("test@gmail.com")).thenReturn(false);
		when(passwordEncoder.encode("1234")).thenReturn("passwordEncriptado");
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		
		// Act (ejecutar)
		Usuario resultado = usuarioService.registrar(usuario);
		
		// Assert (verificar)
		assertEquals ("passwordEncriptado", resultado.getPassword());
		verify(usuarioRepository, times(1)).save(usuario);
	}
	
	@Test
	void registrar_deberiaLanzarExcepcionCuandoEmailYaExiste() {
		
		when(usuarioRepository.existsByEmail("test@gmail.com")).thenReturn(true);
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			usuarioService.registrar(usuario);
		});
		
		assertEquals("El email ya está registrado", exception.getMessage());
		verify(usuarioRepository, never()).save(any());
	}
}
