package jobboard.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobboard.dto.OfertaResponseDTO;
import jobboard.entity.Oferta;
import jobboard.entity.Usuario;
import jobboard.enums.Modalidad;
import jobboard.service.OfertaService;
import jobboard.service.UsuarioService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor
public class OfertaController {
	
	private final OfertaService ofertaService;
	private final UsuarioService usuarioService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<OfertaResponseDTO>> listar() {
		List<OfertaResponseDTO> ofertas = ofertaService.listarActivas()
				.stream()
				.map(ofertaService::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(ofertas);
	}
	
	@GetMapping("/filtrar")
	public ResponseEntity<List<OfertaResponseDTO>> filtrar(
			@RequestParam(required = false) String tecnologia,
			@RequestParam(required = false) String modalidad) {
		List<Oferta> resultado;
		
		if (tecnologia != null ) {
			resultado = ofertaService.filtrarPorTecnologia(tecnologia);
		} else if (modalidad != null) {
			resultado = ofertaService.filtrarPorModalidad(Modalidad.valueOf(modalidad));
		} else {
			resultado = ofertaService.listarActivas();
		}
		
		List<OfertaResponseDTO> dtos = resultado.stream()
				.map(ofertaService::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}
	
	@PostMapping("/crear")
	public ResponseEntity<OfertaResponseDTO> crear(@RequestBody Oferta oferta, Authentication auth){
		Usuario empresa = usuarioService.buscarPorEmail(auth.getName());
		Oferta creada = ofertaService.crear(oferta, empresa);
		return ResponseEntity.ok(ofertaService.convertirADTO(creada));
	}
	
	@GetMapping("/mis-ofertas")
	public ResponseEntity<List<OfertaResponseDTO>> misOfertas(Authentication auth){
		Usuario empresa = usuarioService.buscarPorEmail(auth.getName());
		List<OfertaResponseDTO> dtos = ofertaService.listarPorEmpresa(empresa.getId())
				.stream()
				.map(ofertaService::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id, Authentication auth) {
		Usuario empresa = usuarioService.buscarPorEmail(auth.getName());
		ofertaService.desactivar(id, empresa.getId());
		return ResponseEntity.ok(Map.of("mensaje", "Oferta desctivada correctamente"));
	}
	

}
