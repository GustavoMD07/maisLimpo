package com.maislimpo.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maislimpo.DTO.EsqueciSenhaDTO;
import com.maislimpo.DTO.RedefinirSenhaDTO;
import com.maislimpo.DTO.UsuarioDTO;
import com.maislimpo.entity.Usuario;
import com.maislimpo.exception.TokenExpiradoException;
import com.maislimpo.exception.TokenInvalidoException;
import com.maislimpo.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuario") 
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000") 
public class UsuarioController {

    private final UsuarioService usuarioService;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody UsuarioDTO usuario) {
    // 1. A verificação de credenciais continua a mesma
    Usuario usuarioLogado = usuarioService.verificarCredenciais(usuario.getEmail(), usuario.getSenha());

    // 2. AGORA A GENTE VERIFICA A OPÇÃO "LEMBRAR"
    if (usuario.isLembrar()) {
        // Se a opção estiver marcada, a gente gera o token
        String tokenLembrarMe = usuarioService.gerarTokenLembrarMe(usuarioLogado);

        // 3. E cria um Cookie seguro para enviar ao navegador
        ResponseCookie cookie = ResponseCookie.from("lembrar-me-token", tokenLembrarMe)
                .httpOnly(true)       // O cookie não pode ser acessado por JavaScript no frontend (mais seguro)
                .secure(false)        // Em produção, mude para 'true' se usar HTTPS
                .path("/")            // O cookie estará disponível em todo o site
                .maxAge(Duration.ofDays(60)) //lembra por 60 dias
                .build();
        
        System.out.println("LOG: Cookie 'lembrar-me' gerado para o usuário: " + usuario.getEmail());

        // 4. Retorna a resposta de sucesso COM o cookie no cabeçalho
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(usuarioLogado); // Manda o corpo da resposta normalmente
    }

    // 5. Se "lembrar" não estiver marcado, retorna a resposta simples de antes
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
    try {
        String tokenLimpo = token.replace("\"", "");
        usuarioService.confirmarEmail(tokenLimpo);
        return ResponseEntity.ok("E-mail confirmado com sucesso! Você já pode fazer login.");
    } catch ( TokenExpiradoException | TokenInvalidoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
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
            usuarioInfo.setId(usuario.getId());
            usuarioInfo.setEmail(usuario.getEmail());
            usuarioInfo.setTipoUsuario(usuario.getTipoUsuario());
            // Adicione outros dados que o frontend precise saber
            return ResponseEntity.ok(usuarioInfo);
        }
    }
    // Se não tem token ou o token é inválido
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nenhuma sessão ativa.");
}

    @PostMapping("/validar-token-senha")
    public ResponseEntity<String> validarTokenSenha(@RequestBody Map<String, String> payload) {
        try {
            String token = payload.get("token");
            usuarioService.validarTokenSenha(token);
            return ResponseEntity.ok("Token de redefinição de senha válido.");
        } catch (Exception e) {
             // Captura nossas exceções e retorna a mensagem delas!
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @GetMapping("/logout") 
public ResponseEntity<Void> logout() {
    // Cria um cookie com o mesmo nome do cookie de login, mas com idade máxima de 0
    // Isso instrui o navegador a deletá-lo imediatamente.
    ResponseCookie cookie = ResponseCookie.from("lembrar-me-token", "") // valor pode ser vazio
            .httpOnly(true)
            .secure(false) // Deve corresponder à configuração do cookie de login
            .path("/")
            .maxAge(0) // A MÁGICA ESTÁ AQUI!
            .build();

    System.out.println("LOG: Usuário fez logout. Cookie 'lembrar-me' invalidado.");

    // Redireciona o usuário de volta para a página de login após o logout
    return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .header(HttpHeaders.LOCATION, "/index.html") // Redirecionamento
            .build();
}
}