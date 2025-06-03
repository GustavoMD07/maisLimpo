package com.maislimpo.maislimpo.controller;

import org.springframework.stereotype.Controller; // Mantido
import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.maislimpo.service.UsuarioService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	/**
	 * Tenta realizar o login do usuário.
	 * @param email O email fornecido.
	 * @param senha A senha fornecida.
	 * @return O objeto Usuario se o login for bem-sucedido, null caso contrário.
	 * @throws EmailNaoConfirmadoException Se o email do usuário ainda não foi confirmado.
	 * @throws IllegalArgumentException    Se houver outros problemas (delegado do service, embora agora o service retorne null para usuário/senha errados)
	 */
	public Usuario loginUsuario(String email, String senha) throws EmailNaoConfirmadoException { // Mudança no tipo de retorno
		// A IllegalArgumentException por usuário não encontrado foi removida do service,
		// então não precisa mais ser declarada aqui para esse caso específico.
		// O service agora retorna null para falha de autenticação (email/senha errados).
		return usuarioService.verificarCredenciais(email, senha);
	}
	
	/**
	 * Inicia o processo de cadastro de um novo usuário.
	 * O usuário é salvo como não confirmado e um email de confirmação é enviado.
	 * @param usuario O objeto Usuario com os dados para cadastro.
	 * @return O Usuario salvo (com token de confirmação, etc.).
	 * @throws IllegalArgumentException Se o email já estiver cadastrado e confirmado, ou outros erros de validação do service.
	 */
	public Usuario iniciarCadastroUsuario(Usuario usuario) throws IllegalArgumentException {
		return usuarioService.registrarNovoUsuario(usuario); 
	}
	
	/**
	 * Processa a confirmação de email usando o token fornecido.
	 * @param token O token de confirmação.
	 * @return true se o email for confirmado com sucesso, false caso contrário (token inválido, expirado, etc.).
	 */
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