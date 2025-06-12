package com.maislimpo.controller;

import com.maislimpo.DTO.EsqueciSenhaDTO;
import com.maislimpo.DTO.RedefinirSenhaDTO;
import com.maislimpo.DTO.UsuarioDTO;
import com.maislimpo.entity.Usuario;
import com.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.time.Duration;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario") 
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000") 
public class UsuarioController {

    private final UsuarioService usuarioService;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody UsuarioDTO usuario) {

    Usuario usuarioLogado = usuarioService.verificarCredenciais(usuario.getEmail(), usuario.getSenha());
    return ResponseEntity.ok(usuarioLogado);
}

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioDTO novoUsuario) {
        // Graças ao @Valid, as validações do DTO (email, senha, etc.) rodam antes!
        try {
            Usuario usuarioParaSalvar = new Usuario();
            usuarioParaSalvar.setEmail(novoUsuario.getEmail());
            usuarioParaSalvar.setSenha(novoUsuario.getSenha());
            // Aqui a gente pegaria o tipo de usuário (voluntário/ong) que viria no DTO
            usuarioParaSalvar.setTipoUsuario("Voluntário"); // Exemplo fixo por enquanto

            usuarioService.registrarNovoUsuario(usuarioParaSalvar);

            return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro iniciado! Verifique seu e-mail para confirmar.");

        } catch (IllegalArgumentException e) {
            // Ex: Email já cadastrado
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor ao tentar cadastrar.");
        }
    }

    @PostMapping("/confirmar-token")
public ResponseEntity<?> confirmarToken(@RequestBody String token) {
    // O token chega como uma string simples, ex: "meu-token-aqui"
    
    // Removemos possíveis aspas que o JSON pode adicionar
    String tokenLimpo = token.replace("\"", "");

    // A gente já tem a lógica de validação no service!
    boolean sucesso = usuarioService.confirmarEmail(tokenLimpo);

    if (sucesso) {
        return ResponseEntity.ok("E-mail confirmado com sucesso! Você já pode fazer o login.");
    } else {
        return ResponseEntity.badRequest().body("Token inválido, expirado ou já utilizado.");
    }
}

@PostMapping("/esqueci-senha")
public ResponseEntity<String> solicitarRedefinicaoSenha(@RequestBody EsqueciSenhaDTO dto) {
    // Usamos o try-catch aqui pra sempre retornar uma resposta OK, por segurança
    try {
        usuarioService.processarPedidoRedefinicao(dto.getEmail());
    } catch (Exception e) {
        // Loga o erro no backend, mas não expõe para o usuário
        System.err.println("Erro ao processar pedido de redefinição: " + e.getMessage());
    }
    return ResponseEntity.ok("Se o e-mail estiver cadastrado, um link para redefinição de senha foi enviado.");
}

@PostMapping("/redefinir-senha")
public ResponseEntity<String> redefinirSenha(@RequestParam String token, @RequestBody RedefinirSenhaDTO dto) {
    try {
        // Validações básicas da senha antes de mandar pro service
        if (dto.getNovaSenha() == null || dto.getNovaSenha().length() < 6) {
            return ResponseEntity.badRequest().body("A senha deve ter no mínimo 6 caracteres.");
        }
        usuarioService.redefinirSenhaComToken(token, dto.getNovaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso! Você já pode fazer o login com a nova senha.");
    } catch (RuntimeException e) {
        // Erros como "Token inválido" ou "Token expirado" serão pegos aqui
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

@GetMapping("/login-com-token")
public ResponseEntity<?> loginComToken(@CookieValue(name = "lembrar-me-token", required = false) String token) {
    if (token != null) {
        Usuario usuario = usuarioService.loginComTokenLembrarMe(token);
        if (usuario != null) {
            // Token válido! Retornamos os dados do usuário (sem a senha!)
            // Isso é útil pro frontend saber quem está logado.
            UsuarioDTO usuarioInfo = new UsuarioDTO();
            usuarioInfo.setEmail(usuario.getEmail());
            // Adicione outros dados que o frontend precise saber
            return ResponseEntity.ok(usuarioInfo);
        }
    }
    // Se não tem token ou o token é inválido
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nenhuma sessão ativa.");
}

    @PostMapping("/validar-token-senha")
    public ResponseEntity<String> validarTokenSenha(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        boolean isTokenValid = usuarioService.isResetTokenValid(token);

        if (isTokenValid) {
            return ResponseEntity.ok("Token validado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
    }
}