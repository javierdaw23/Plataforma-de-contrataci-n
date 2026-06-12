package jobboard.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobboard.dto.CandidaturaResponseDTO;
import jobboard.entity.Usuario;
import jobboard.enums.EstadoCandidatura;
import jobboard.service.CandidaturaService;
import jobboard.service.UsuarioService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidaturas")
public class CandidaturaController {
	private final CandidaturaService candidaturaService;
	private final UsuarioService usuarioService;
	
	@PostMapping("/aplicar/{ofertaId}")
	public ResponseEntity<CandidaturaResponseDTO> aplicar(
			@PathVariable Long ofertaId,
			@RequestBody Map<String, String> body,
			Authentication auth) {
		Usuario developer = usuarioService.buscarPorEmail(auth.getName());
		return ResponseEntity.ok(
			candidaturaService.convertirADTO(candidaturaService.aplicar(ofertaId, developer, body.get("mensaje")))
		);
	}
	
	@GetMapping("/mis-candidaturas")
	public ResponseEntity<List<CandidaturaResponseDTO>> misCandidaturas(Authentication auth) {
		Usuario developer = usuarioService.buscarPorEmail(auth.getName());
		
		List<CandidaturaResponseDTO> dtos = candidaturaService.listarPorDeveloper(developer.getId())
				.stream()
				.map(candidaturaService::convertirADTO)
				.collect(Collectors.toList()); 
		return ResponseEntity.ok(dtos);
	}
	
	@GetMapping("/oferta/{ofertaId}")
	public ResponseEntity<List<CandidaturaResponseDTO>> porOferta(@PathVariable Long ofertaId){
		List<CandidaturaResponseDTO> dtos = candidaturaService.listarPorOferta(ofertaId)
				.stream()
				.map(candidaturaService::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}
	
	@PatchMapping("/{id}/estado")
	public ResponseEntity<CandidaturaResponseDTO> cambiarEstado(
			@PathVariable Long id,
			@RequestBody Map<String, String> body) {
		EstadoCandidatura estado = EstadoCandidatura.valueOf(body.get("estado"));
		return ResponseEntity.ok(candidaturaService.convertirADTO(candidaturaService.cambiarEstado(id, estado)));
	}
}
