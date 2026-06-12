package jobboard.entity;

import java.time.LocalDateTime;

import jobboard.enums.EstadoCandidatura;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "candidaturas")
public class Candidatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "oferta_id, nullable = false")
	private Oferta oferta;
	
	@ManyToOne
	@JoinColumn(name="developer_id", nullable = false)
	private Usuario developer;
	
	@Enumerated(EnumType.STRING)
	private EstadoCandidatura estado = EstadoCandidatura.PENDIENTE;
	
	private String mensaje;
	
	private LocalDateTime fechaAplicacion;
}
