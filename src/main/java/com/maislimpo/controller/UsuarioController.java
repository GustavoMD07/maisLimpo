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
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios") // Agora temos um prefixo padrão para todos os endpoints de usuário!
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // --- ENDPOINT DE LOGIN (O que já existia) ---
   @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) { // Mudei o nome do parâmetro pra clareza
    try {
        Usuario usuarioLogado = usuarioService.verificarCredenciais(usuarioDTO.getEmail(), usuarioDTO.getSenha());

        if (usuarioLogado != null) {
            // LOGIN DEU CERTO!
            if (usuarioDTO.isLembrar()) {
                // O usuário marcou "Lembrar de mim"!
                String token = usuarioService.gerarTokenLembrarMe(usuarioLogado);

                // Criando o cookie seguro!
                ResponseCookie cookie = ResponseCookie.from("lembrar-me-token", token)
                    .httpOnly(true)       // Essencial! Impede acesso via JS.
                    .secure(false)        // Em produção, mude para 'true' se usar HTTPS
                    .path("/")            // O cookie estará disponível em todo o site
                    .maxAge(Duration.ofDays(30)) // Duração do cookie
                    .build();

                // Retorna uma resposta OK, e no header vai o comando pra setar o cookie
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Login bem-sucedido!");
            }

            // Login normal sem "Lembrar de mim"
            return ResponseEntity.ok("Login bem-sucedido!");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    } catch (EmailNaoConfirmadoException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login.");
    }
}

    // --- ENDPOINT DE CADASTRO (O novo, no lugar certo!) ---
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
}