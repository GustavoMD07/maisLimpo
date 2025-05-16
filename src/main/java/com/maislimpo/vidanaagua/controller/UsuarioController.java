package com.maislimpo.vidanaagua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.maislimpo.vidanaagua.entity.Usuario;
import com.maislimpo.vidanaagua.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	public void loginUsuario(Usuario usuario) {
		usuarioService.salvarUsuario(usuario);
	}
}
