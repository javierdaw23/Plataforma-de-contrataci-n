package jobboard.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobboard.entity.Usuario;
import jobboard.enums.Role;
import jobboard.security.JwtService;
import jobboard.service.UsuarioService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UsuarioService usuarioService;
	private final JwtService jwtService;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@RequestBody Map<String,String> body) {
		Usuario usuario = new Usuario();
		usuario.setEmail(body.get("email"));
		usuario.setPassword(body.get("password"));
		usuario.setNombre(body.get("nombre"));
		usuario.setRole(Role.valueOf(body.get("role")));
		usuarioService.registrar(usuario);
		return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado correctamente"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> body){
		Usuario usuario = usuarioService.buscarPorEmail(body.get("email"));
		return ResponseEntity.ok(Map.of(
			"token", jwtService.generarToken(usuario.getEmail(), usuario.getRole().name()),
			"role", usuario.getRole().name(),
			"nombre", usuario.getNombre()
		));
	}
}
