package jobboard.controller;

import java.util.List;
import java.util.Map;

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
	public ResponseEntity<List<Oferta>> listar() {
		return ResponseEntity.ok(ofertaService.listarActivas());
	}
	
	@GetMapping("/filtrar")
	public ResponseEntity<List<Oferta>> filtrar(
			@RequestParam(required = false) String tecnologia,
			@RequestParam(required = false) String modalidad) {
		if (tecnologia != null ) {
			return ResponseEntity.ok(ofertaService.filtrarPorTecnologia(tecnologia));
		}
		if (modalidad != null) {
			return ResponseEntity.ok(ofertaService.filtrarPorModalidad(Modalidad.valueOf(modalidad)));
		}
		return ResponseEntity.ok(ofertaService.listarActivas());	
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Oferta> crear(@RequestBody Oferta oferta, Authentication auth){
		Usuario empresa = usuarioService.buscarPorEmail(auth.getName());
		return ResponseEntity.ok(ofertaService.crear(oferta, empresa));
	}
	
	@GetMapping("/mis-ofertas")
	public ResponseEntity<List<Oferta>> misOfertas(Authentication auth){
		Usuario empresa = usuarioService.buscarPorEmail(auth.getName());
		return ResponseEntity.ok(ofertaService.listarPorEmpresa(empresa.getId()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id, Authentication auth) {
		Usuario empresa = usuarioService.buscarPorEmail(auth.getName());
		ofertaService.desactivar(id, empresa.getId());
		return ResponseEntity.ok(Map.of("mensaje", "Oferta desctivada correctamente"));
	}
	

}
