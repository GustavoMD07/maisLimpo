package com.maislimpo.vidanaagua.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maislimpo.vidanaagua.entity.Usuario;
import com.maislimpo.vidanaagua.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvarUsuario(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent()) {
			throw new IllegalArgumentException("Email já cadastrado");
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isEmpty()) {
			throw new IllegalArgumentException("Id não encontrado");
		}
		
		return usuario.get();
	}
	
	public void deletarUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

	
}
