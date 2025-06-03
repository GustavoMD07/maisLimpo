package com.maislimpo.maislimpo.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.maislimpo.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	private final EmailService emailService; 
	
	
	@Transactional // Garante que todas as operações de banco de dados aconteçam ou nenhuma (atomicidade)
	public Usuario registrarNovoUsuario(Usuario novoUsuario) {
		Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findByEmail(novoUsuario.getEmail());

		if (usuarioExistenteOpt.isPresent()) {
			Usuario usuarioExistente = usuarioExistenteOpt.get();
			if (usuarioExistente.isEmailConfirmado()) {
				throw new IllegalArgumentException("Este e-mail já está cadastrado e confirmado.");
			} else {
				// Email existe mas não está confirmado: gerar novo token e reenviar email
				String token = UUID.randomUUID().toString();
				usuarioExistente.setTokenConfirmacao(token);
				usuarioExistente.setDataExpiracaoToken(LocalDateTime.now().plusHours(24)); // Token válido por 24h
				// novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha())); // Se usar encoder
				// Não precisamos setar a senha aqui se o usuário já existe, apenas o token.
				// Se quiser permitir atualização de senha nessa etapa, adicione: usuarioExistente.setSenha(novoUsuario.getSenha());
				Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
				emailService.enviarEmailConfirmacaoCadastro(usuarioAtualizado.getEmail(), usuarioAtualizado.getEmail(), token);
				return usuarioAtualizado; // Retorna o usuário existente com novo token
			}
		}

		// Email não existe: criar novo usuário
		String token = UUID.randomUUID().toString();
		novoUsuario.setTokenConfirmacao(token);
		novoUsuario.setEmailConfirmado(false);
		novoUsuario.setDataExpiracaoToken(LocalDateTime.now().plusHours(24)); // Token válido por 24h
		// novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha())); // IMPORTANTE: Criptografar a senha antes de salvar!
		
		Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
		
		// Envia o email de confirmação
		// Passando o email como "nome" por simplicidade. Poderia ser um campo "nome" do usuário.
		emailService.enviarEmailConfirmacaoCadastro(usuarioSalvo.getEmail(), usuarioSalvo.getEmail(), token);
		
		return usuarioSalvo;
	}

	/**
	 * Confirma o email de um usuário baseado no token.
	 * @param token O token de confirmação.
	 * @return true se a confirmação for bem-sucedida, false caso contrário.
	 */
	@Transactional
	public boolean confirmarEmail(String token) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenConfirmacao(token);
		
		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			// Opcional: Verificar se o token expirou
			if (usuario.getDataExpiracaoToken() != null && usuario.getDataExpiracaoToken().isBefore(LocalDateTime.now())) {
				// Token expirado. Você pode querer deletar o usuário ou permitir que ele peça um novo token.
				// Por agora, apenas não confirmamos.
				// Poderia lançar uma exceção: throw new TokenExpiradoException("Token de confirmação expirado.");
				System.err.println("Tentativa de confirmação com token expirado: " + token);
				return false; 
			}
			
			usuario.setEmailConfirmado(true);
			usuario.setTokenConfirmacao(null); // Limpa o token para não ser usado novamente
			usuario.setDataExpiracaoToken(null); // Limpa a data de expiração
			usuarioRepository.save(usuario);
			return true;
		}
		return false; // Token não encontrado
	}
	
	/**
	 * Verifica as credenciais do usuário para login.
	 * @param email O email do usuário.
	 * @param senha A senha do usuário (em texto plano, para comparação ou para ser criptografada aqui).
	 * @return true se as credenciais forem válidas e o email confirmado, false caso contrário.
	 * @throws EmailNaoConfirmadoException se o email não estiver confirmado.
	 * @throws IllegalArgumentException se o usuário não for encontrado.
	 */
	public boolean verificarCredenciais(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
        	// Lançar uma exceção ou retornar false, dependendo de como o controller/view trata isso.
            // throw new IllegalArgumentException("Usuário não encontrado com o e-mail: " + email);
            return false; // Usuário não existe
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (!usuario.isEmailConfirmado()) {
            throw new EmailNaoConfirmadoException("Seu e-mail ainda não foi confirmado. Por favor, verifique sua caixa de entrada.");
        }
        
        // Compara a senha fornecida com a senha armazenada.
        // Se estiver usando PasswordEncoder: return passwordEncoder.matches(senha, usuario.getSenha());
        return usuario.getSenha().equals(senha); // Comparação de senha em texto plano (NÃO RECOMENDADO PARA PRODUÇÃO)
    }

	// Métodos buscarUsuarioPorId e deletarUsuario permanecem os mesmos por enquanto.
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

	// O método salvarUsuario original pode ser renomeado ou removido se 'registrarNovoUsuario' o substitui.
	// Se ele ainda for usado para outras coisas (como atualização de perfil), mantenha-o com a lógica apropriada.
	// Por hora, vou comentar o antigo 'salvarUsuario' para evitar confusão.
	/*
	public Usuario salvarUsuario(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent()) {
			throw new IllegalArgumentException("Email já cadastrado");
		}
		// IMPORTANTE: Criptografar a senha aqui também se for um cadastro direto
		// usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	*/
}
