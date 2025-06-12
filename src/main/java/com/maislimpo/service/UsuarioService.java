package com.maislimpo.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maislimpo.entity.LembrarToken;
import com.maislimpo.entity.Usuario;
import com.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.exception.SenhaInvalidaException;
import com.maislimpo.exception.TokenExpiradoException;
import com.maislimpo.exception.UsuarioNaoEncontradoException;
import com.maislimpo.repository.UsuarioRepository;
import com.maislimpo.repository.LembrarTokenRepository;
import org.mindrot.jbcrypt.BCrypt;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final EmailService emailService;
	private final LembrarTokenRepository lembrarTokenRepository; 

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
		
		String senhaOriginal = novoUsuario.getSenha();
		String senhaHash = BCrypt.hashpw(senhaOriginal, BCrypt.gensalt());
		//salt pra usuarios com a mesma senha terem hashs diferentes
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
	    return false; 
	}

	public Usuario verificarCredenciais(String email, String senha) throws EmailNaoConfirmadoException {
    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new UsuarioNaoEncontradoException("E-mail não cadastrado no sistema."));

    if (!usuario.isEmailConfirmado()) {
        throw new EmailNaoConfirmadoException(
            "Seu e-mail ainda não foi confirmado. Por favor, verifique sua caixa de entrada."
        );
    }

    if (BCrypt.checkpw(senha, usuario.getSenha())) {
        System.out.println("LOG: Login bem-sucedido para: " + email);
        return usuario; 
    } else {
        // Se a senha estiver errada, lança a exceção de senha inválida
        System.out.println("LOG: Tentativa de login com senha incorreta para: " + email);
        throw new SenhaInvalidaException("Senha incorreta.");
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

	@Transactional
public void processarPedidoRedefinicao(String email) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

    // Medida de segurança: não informamos se o e-mail existe ou não.
    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();

        String token = UUID.randomUUID().toString();
        usuario.setResetSenhaToken(token); // Usando o campo que você já criou!
        usuario.setResetTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Expira em 1 hora

        usuarioRepository.save(usuario);
        
        // Chama nosso novo método no EmailService!
        emailService.enviarEmailRedefinicaoSenha(usuario.getEmail(), token);
    } else {
         // Log para nosso controle, mas o frontend não saberá a diferença
        System.out.println("LOG: Tentativa de redefinição de senha para e-mail não cadastrado: " + email);
    }
}

@Transactional
public void redefinirSenhaComToken(String token, String novaSenha) {
    // Usando o método que você já criou no repositório!
    Optional<Usuario> usuarioOpt = usuarioRepository.findByResetSenhaToken(token);

    if (usuarioOpt.isEmpty()) {
        throw new RuntimeException("Token de redefinição inválido ou não encontrado!");
    }

    Usuario usuario = usuarioOpt.get();

    // Verifica se o token expirou
    if (usuario.getResetTokenExpiryDate().isBefore(LocalDateTime.now())) {
        throw new RuntimeException("Token de redefinição expirado! Por favor, solicite um novo.");
    }

    // Hasheia a nova senha com o BCrypt que você já usa!
    String senhaHash = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
    usuario.setSenha(senhaHash);
    
    // Limpa o token para que ele não possa ser usado novamente
    usuario.setResetSenhaToken(null);
    usuario.setResetTokenExpiryDate(null);

    usuarioRepository.save(usuario);
    System.out.println("LOG: Senha redefinida com sucesso para o usuário: " + usuario.getEmail());
}

@Transactional
public String gerarTokenLembrarMe(Usuario usuario) {
    String tokenValue = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();

    LembrarToken novoToken = new LembrarToken();
    novoToken.setToken(tokenValue);
    novoToken.setUsuario(usuario);
    novoToken.setDataExpiracao(LocalDateTime.now().plusDays(30)); // Token válido por 30 dias

    lembrarTokenRepository.save(novoToken);
    return tokenValue;
}

// Adicione este método dentro do UsuarioService
public Usuario loginComTokenLembrarMe(String token) {
    Optional<LembrarToken> tokenOpt = lembrarTokenRepository.findByToken(token);

    if (tokenOpt.isPresent()) {
        LembrarToken lembrarToken = tokenOpt.get();
        if (((LocalDateTime) lembrarToken.getDataExpiracao()).isAfter(LocalDateTime.now())) {
            // Token válido e não expirado! Retorna o usuário.
            return lembrarToken.getUsuario();
        } else {
            // Token expirou, vamos deletar ele do banco
            lembrarTokenRepository.delete(lembrarToken);
        }
    }
    return null; // Token não encontrado ou expirado
}

}
