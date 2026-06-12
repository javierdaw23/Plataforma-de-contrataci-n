package jobboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jobboard.entity.Oferta;
import jobboard.enums.Modalidad;

public interface OfertaRepository extends JpaRepository<Oferta,Long>{
	List<Oferta> findByActivaTrue();
	List<Oferta> findByEmpresaId(Long empresaId);
	List<Oferta> findByModalidadAndActivaTrue(Modalidad modalidad);
	List<Oferta> findByTecnologiasContainingIgnoreCaseAndActivaTrue(String tecnologia);
}
