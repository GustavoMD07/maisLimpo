package com.maislimpo.maislimpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.service.UsuarioService;


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	public boolean loginUsuario(String email, String senha) {
		return usuarioService.verificarCredenciais(email, senha);
	}
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		return usuarioService.salvarUsuario(usuario);
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioService.buscarUsuarioPorId(id);
	}
	
	public void removerUsuario(Long id) {
		usuarioService.deletarUsuario(id);
	}
}
