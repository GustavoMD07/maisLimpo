package com.maislimpo.maislimpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.service.UsuarioService;


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Realiza login do usuário
	 * @param email email do usuário
	 * @param senha senha do usuário
	 * @return true se o login for bem-sucedido, false caso contrário
	 */
	public boolean loginUsuario(String email, String senha) {
		return usuarioService.verificarCredenciais(email, senha);
	}
	
	/**
	 * Cadastra um novo usuário
	 * @param usuario objeto Usuario com os dados a serem cadastrados
	 * @return o usuário cadastrado
	 * @throws IllegalArgumentException se o email já estiver cadastrado
	 */
	public Usuario cadastrarUsuario(Usuario usuario) {
		return usuarioService.salvarUsuario(usuario);
	}
	
	/**
	 * Busca um usuário pelo ID
	 * @param id ID do usuário
	 * @return o usuário encontrado
	 * @throws IllegalArgumentException se o usuário não for encontrado
	 */
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioService.buscarUsuarioPorId(id);
	}
	
	/**
	 * Remove um usuário pelo ID
	 * @param id ID do usuário a ser removido
	 */
	public void removerUsuario(Long id) {
		usuarioService.deletarUsuario(id);
	}
}
