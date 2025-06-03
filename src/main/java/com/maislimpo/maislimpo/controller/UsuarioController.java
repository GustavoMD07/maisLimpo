package com.maislimpo.maislimpo.controller;

import org.springframework.stereotype.Controller; 
import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.maislimpo.service.UsuarioService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor //lombok
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	public Usuario loginUsuario(String email, String senha) throws EmailNaoConfirmadoException {
		return usuarioService.verificarCredenciais(email, senha);
	}
	
	public Usuario iniciarCadastroUsuario(Usuario usuario) throws IllegalArgumentException {
		return usuarioService.registrarNovoUsuario(usuario); 
	}
	
	//eles retornam dizendo "true" or "false"
	public boolean processarConfirmacaoDeEmail(String token) {
		return usuarioService.confirmarEmail(token);
	}

	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioService.buscarUsuarioPorId(id);
	}
	
	public void removerUsuario(Long id) {
		usuarioService.deletarUsuario(id);
	}
}