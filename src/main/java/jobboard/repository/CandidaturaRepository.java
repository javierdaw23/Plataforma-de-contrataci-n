package jobboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jobboard.entity.Candidatura;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long>{
	List<Candidatura> findByDeveloperId(Long developerId);
	List<Candidatura> findByOfertaId(Long ofertaId);
	boolean existsByOfertaIdAndDeveloperId(Long ofertaId, Long developerId);

}
