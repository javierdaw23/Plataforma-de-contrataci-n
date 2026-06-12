package jobboard.dto;

import java.time.LocalDateTime;

import jobboard.enums.EstadoCandidatura;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidaturaResponseDTO {
	private Long id;
	private String mensaje;
	private EstadoCandidatura estado;
	private LocalDateTime fechaAplicación;
	private Long ofertaId;
	private String ofertaTitulo;
	private String developerNombre;
	private String developerEmail;
}
