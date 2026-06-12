package jobboard.entity;

import java.time.LocalDateTime;

import jobboard.enums.Modalidad;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ofertas")
public class Oferta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable=false, length = 2000)
	private String descripcion;
	
	@Column(nullable = false)
	private String tecnologias;
	
	@Column(nullable = false)
	private String ubicacion;
	
	@Enumerated(EnumType.STRING)
	private Modalidad modalidad;
	
	private String salario;
	
	@Column(nullable = false)
	private LocalDateTime fechaPublicacion;
	
	private boolean activa = true;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	private Usuario empresa;
}
