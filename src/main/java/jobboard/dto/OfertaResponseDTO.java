package jobboard.dto;

import java.time.LocalDateTime;

import jobboard.enums.Modalidad;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfertaResponseDTO {
	
	private Long id;
	private String titulo;
	private String descripcion;
	private String tecnologias;
	private String ubicacion;
	private Modalidad modalidad;
	private String salario;
	private LocalDateTime fechaPublicacion;
	private boolean activa;
	private String empresaNombre;
	private Long empresaId;
}
