package jobboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jobboard.entity.Usuario;
import lombok.RequiredArgsConstructor;
import jobboard.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Usuario registrar(Usuario usuario) {
		if(usuarioRepository.existsByEmail(usuario.getEmail())){
			throw new RuntimeException("El email ya está registrado");
		}
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarPorEmail (String email) {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
	}
}
