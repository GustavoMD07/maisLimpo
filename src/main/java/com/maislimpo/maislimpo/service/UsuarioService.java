package com.maislimpo.maislimpo.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.maislimpo.exception.TokenExpiradoException;
import com.maislimpo.maislimpo.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	private final EmailService emailService;

	@Transactional
	public Usuario registrarNovoUsuario(Usuario novoUsuario) {
		Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findByEmail(novoUsuario.getEmail());

		if (usuarioExistenteOpt.isPresent()) {
			
			Usuario usuarioExistente = usuarioExistenteOpt.get();
			
			if (usuarioExistente.isEmailConfirmado()) {
				throw new IllegalArgumentException("Este e-mail já está cadastrado e confirmado.");
			} 
			else {
				// email existe mas não está confirmado: gerar novo token e reenviar email
				String token = UUID.randomUUID().toString(); //Universaly Unique Identifier
				usuarioExistente.setTokenConfirmacao(token); //código único e de difícil repetição
				usuarioExistente.setDataExpiracaoToken(LocalDateTime.now().plusHours(24)); // token válido por 24h
				
				Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
				emailService.enviarEmailConfirmacaoCadastro(usuarioAtualizado.getEmail(), usuarioAtualizado.getEmail(),
						token);
				return usuarioAtualizado; 
			}
		}
		// email não cadastrado, novo usuário mesmo
		
		String senhaOriginal = novoUsuario.getEmail();
		String senhaHash = BCrypt.hashpw(senhaOriginal, BCrypt.gensalt());
		novoUsuario.setSenha(senhaHash);
		//senha Hash
		
		String token = UUID.randomUUID().toString();
		novoUsuario.setTokenConfirmacao(token);
		novoUsuario.setEmailConfirmado(false);
		novoUsuario.setDataExpiracaoToken(LocalDateTime.now().plusHours(24));

		Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

		emailService.enviarEmailConfirmacaoCadastro(usuarioSalvo.getEmail(), usuarioSalvo.getEmail(), token);

		return usuarioSalvo;
	}

	@Transactional
	public boolean confirmarEmail(String token) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenConfirmacao(token);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			if (usuario.getDataExpiracaoToken() != null
					&& usuario.getDataExpiracaoToken().isBefore(LocalDateTime.now())) {
				
				System.out.println("Tentativa de confirmação com token expirado: " + token);
				String novoToken = UUID.randomUUID().toString();
				usuario.setTokenConfirmacao(novoToken);
				usuario.setDataExpiracaoToken(LocalDateTime.now().plusHours(24)); //mais 24h
				
				usuarioRepository.save(usuario);
	           
	            try {
	                emailService.enviarEmailConfirmacaoCadastro(usuario.getEmail(), usuario.getEmail(), novoToken);
	                System.out.println("LOG: Novo email de confirmação enviado para " + usuario.getEmail() + " com token: " + novoToken);
	            } catch (Exception e) {
	                System.err.println("ERRO CRÍTICO: Falha ao reenviar email de confirmação para " + usuario.getEmail() + " após expiração do token. Erro: " + e.getMessage());
	            }
	            
	            throw new TokenExpiradoException(
	                "Seu token de confirmação expirou. Um novo token foi gerado e um novo e-mail de confirmação foi enviado para " +
	                usuario.getEmail() + ". Por favor, verifique sua caixa de entrada (e spam!) e utilize o novo token."
	            );
	        }

	        usuario.setEmailConfirmado(true);
	        usuario.setTokenConfirmacao(null); 
	        usuario.setDataExpiracaoToken(null); 
	        usuarioRepository.save(usuario);
	        System.out.println("LOG: Email confirmado com sucesso para o usuário " + usuario.getEmail() + " com token: " + token);
	        return true;
	    }
	    System.err.println("Tentativa de confirmação com token inválido ou não encontrado: " + token);
	    return false; // Token não encontrado
	}

	public Usuario verificarCredenciais(String email, String senha) throws EmailNaoConfirmadoException {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

		if (usuarioOpt.isEmpty()) {
			System.out.println("LOG: Tentativa de login com email não cadastrado: " + email);
			return null;
		}

		Usuario usuario = usuarioOpt.get();

		if (!usuario.isEmailConfirmado()) {
			throw new EmailNaoConfirmadoException(
					"Seu e-mail ainda não foi confirmado. Por favor, verifique sua caixa de entrada.");
		}

		if (BCrypt.checkpw(senha, usuario.getSenha())) { //o hash sempre volta o mesmo se a senha for a mesma
			System.out.println("LOG: Login bem-sucedido para: " + email);
			return usuario; 
		} else {
			System.out.println("LOG: Tentativa de login com senha incorreta para: " + email);
			return null; 
		}
	}

	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));
	}

	@Transactional
	public void deletarUsuario(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new IllegalArgumentException("Não é possível deletar: Usuário com ID " + id + " não encontrado.");
		}
		usuarioRepository.deleteById(id);
		System.out.println("LOG: Usuário com ID " + id + " deletado.");
	}

}
