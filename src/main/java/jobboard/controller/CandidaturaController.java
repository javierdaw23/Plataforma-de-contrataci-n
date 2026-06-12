package jobboard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobboard.entity.Candidatura;
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
	public ResponseEntity<Candidatura> aplicar(
			@PathVariable Long ofertaId,
			@RequestBody Map<String, String> body,
			Authentication auth) {
		Usuario developer = usuarioService.buscarPorEmail(auth.getName());
		return ResponseEntity.ok(
			candidaturaService.aplicar(ofertaId, developer, body.get("mensaje"))
		);
	}
	
	@GetMapping("/mis-candidaturas")
	public ResponseEntity<List<Candidatura>> misCandidaturas(Authentication auth) {
		Usuario developer = usuarioService.buscarPorEmail(auth.getName());
		return ResponseEntity.ok(candidaturaService.listarPorDeveloper(developer.getId()));
	}
	
	@GetMapping("/oferta/{ofertaId}")
	public ResponseEntity<List<Candidatura>> porOferta(@PathVariable Long ofertaId){
		return ResponseEntity.ok(candidaturaService.listarPorOfeta(ofertaId));
	}
	
	@PatchMapping("{id}/estado")
	public ResponseEntity<Candidatura> cambiarEstado(
			@PathVariable Long id,
			@RequestBody Map<String, String> body) {
		EstadoCandidatura estado = EstadoCandidatura.valueOf(body.get("estado"));
		return ResponseEntity.ok(candidaturaService.cambiarEstado(id, estado));
	}
}
