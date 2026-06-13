package jobboard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jobboard.entity.Oferta;
import jobboard.entity.Usuario;
import jobboard.repository.OfertaRepository;

@ExtendWith(MockitoExtension.class)
class OfertaServiceTest {
	
	@Mock
	private OfertaRepository ofertaRepository;
	
	@InjectMocks
	private OfertaService ofertaService;
	
	private Oferta oferta;
	private Usuario empresaPropietaria;
	private Usuario otraEmpresa;
	
	@BeforeEach
	void setUp() {
		empresaPropietaria = new Usuario();
		empresaPropietaria.setId(1L);
		empresaPropietaria.setEmail("propietaria@gmail.com");
		
		otraEmpresa = new Usuario();
		otraEmpresa.setId(2L);
		otraEmpresa.setEmail("otra@gmail.com");
		
		oferta = new Oferta();
		oferta.setId(10L);
		oferta.setTitulo("Backend Developer");
		oferta.setEmpresa(empresaPropietaria);
		oferta.setActiva(true);
	}
	
	@Test
	void desactivar_deberiaDesactivarOfertaCuandoEsLaEmpresaPropietaria(){
		when(ofertaRepository.findById(10L)).thenReturn(Optional.of(oferta));
		when(ofertaRepository.save(oferta)).thenReturn(oferta);
		
		ofertaService.desactivar(10L, 1L);
		
		assertFalse(oferta.isActiva());
		verify(ofertaRepository, times(1)).save(oferta);
	}
	
	@Test
	void desactivar_deberiaLanzarExcepcionCuandoNoEsLaEmpresaPropietaria() {
		when(ofertaRepository.findById(10L)).thenReturn(Optional.of(oferta));
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			ofertaService.desactivar(oferta.getId(), otraEmpresa.getId());
		});
		
		assertEquals("No tienes permiso para desactivar esta oferta", exception.getMessage());
		verify(ofertaRepository, never()).save(any());
	}
	

}
