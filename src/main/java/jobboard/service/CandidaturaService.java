package jobboard.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jobboard.entity.Candidatura;
import jobboard.entity.Oferta;
import jobboard.entity.Usuario;
import jobboard.enums.EstadoCandidatura;
import jobboard.repository.CandidaturaRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CandidaturaService {
	
	private final CandidaturaRepository candidaturaRepository;
	private final OfertaService ofertaService;
	
	public Candidatura aplicar(Long ofertaId, Usuario developer, String mensaje){
		if (candidaturaRepository.existsByOfertaIdAndDeveloperId(ofertaId,  developer.getId())) {
			throw new RuntimeException("Ya has aplicado a esta oferta");
		}
		Oferta oferta = ofertaService.buscarPorId(ofertaId);
		Candidatura candidatura = new Candidatura();
		candidatura.setOferta(oferta);
		candidatura.setDeveloper(developer);
		candidatura.setMensaje(mensaje);
		candidatura.setFechaAplicacion(LocalDateTime.now());
		candidatura.setEstado(EstadoCandidatura.PENDIENTE);
		return candidaturaRepository.save(candidatura);
	}
	
	public List<Candidatura> listarPorDeveloper(Long developerId) {
		return candidaturaRepository.findByDeveloperId(developerId);
	}
	
	public List<Candidatura> listarPorOfeta(Long ofertaId) {
		return candidaturaRepository.findByOfertaId(ofertaId);
	}
	
	public Candidatura cambiarEstado(Long candidaturaId, EstadoCandidatura nuevoEstado) {
		Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
				.orElseThrow(() -> new RuntimeException("Candidatura no encontrada"));
		candidatura.setEstado(nuevoEstado);
		return candidaturaRepository.save(candidatura);
	}
}
