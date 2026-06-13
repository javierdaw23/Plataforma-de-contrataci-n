package jobboard.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jobboard.dto.OfertaResponseDTO;
import jobboard.entity.Oferta;
import jobboard.entity.Usuario;
import jobboard.enums.Modalidad;
import jobboard.repository.OfertaRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OfertaService {
	
	private final OfertaRepository ofertaRepository;
	
	public Oferta crear(Oferta oferta, Usuario empresa) {
		oferta.setEmpresa(empresa);
		oferta.setFechaPublicacion(LocalDateTime.now());
		oferta.setActiva(true);
		return ofertaRepository.save(oferta);
	}
	
	public List<Oferta> listarActivas(){
		return ofertaRepository.findByActivaTrue();
	}
	
	public List<Oferta> listarPorEmpresa(Long empresaId){
		return ofertaRepository.findByEmpresaId(empresaId);
	}
	
	public List<Oferta> filtrarPorModalidad(Modalidad modalidad){
		return ofertaRepository.findByModalidadAndActivaTrue(modalidad);
	}
	
	public List<Oferta> filtrarPorTecnologia(String tecnologia){
		return ofertaRepository.findByTecnologiasContainingIgnoreCaseAndActivaTrue(tecnologia);
	}
	
	public Oferta buscarPorId(Long id) {
		return ofertaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
	}
	
	public void desactivar(Long id, Long empresaId) {
		Oferta oferta = buscarPorId(id);
		if (!oferta.getEmpresa().getId().equals(empresaId)) {
			throw new RuntimeException ("No tienes permiso para desactivar esta oferta");
		}
		oferta.setActiva(false);
		ofertaRepository.save(oferta);
	}
	
	public OfertaResponseDTO convertirADTO(Oferta oferta) {
		return new OfertaResponseDTO(
				oferta.getId(),
				oferta.getTitulo(),
				oferta.getDescripcion(),
				oferta.getTecnologias(),
				oferta.getUbicacion(),
				oferta.getModalidad(),
				oferta.getSalario(),
				oferta.getFechaPublicacion(),
				oferta.isActiva(),
				oferta.getEmpresa().getNombre(),
				oferta.getEmpresa().getId()
				);
	}
}
