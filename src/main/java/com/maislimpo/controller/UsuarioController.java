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
  
    Usuario usuarioLogado = usuarioService.verificarCredenciais(usuario.getEmail(), usuario.getSenha());

    if (usuario.isLembrar()) {
        String tokenLembrarMe = usuarioService.gerarTokenLembrarMe(usuarioLogado);

        ResponseCookie cookie = ResponseCookie.from("lembrar-me-token", tokenLembrarMe)
                .httpOnly(true)       //cookie que manda um token para o navegador, e assim ele consegue lembrar
                .secure(false)        
                .path("/")            
                .maxAge(Duration.ofDays(60)) 
                .build();
        
        System.out.println("LOG: Cookie 'lembrar-me' gerado para o usuário: " + usuario.getEmail());
        //log só pra ver se tá certo mesmo

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(usuarioLogado); 
    }

    return ResponseEntity.ok(usuarioLogado);
}

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioDTO novoUsuario) {
    try {
        Usuario usuarioParaSalvar = new Usuario();
        usuarioParaSalvar.setEmail(novoUsuario.getEmail());
        usuarioParaSalvar.setSenha(novoUsuario.getSenha());
        usuarioParaSalvar.setTipoUsuario(novoUsuario.getTipoUsuario().toUpperCase());

        usuarioService.registrarNovoUsuario(usuarioParaSalvar);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro iniciado! Verifique seu e-mail para confirmar.");

    } catch (IllegalArgumentException e) {
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
        return ResponseEntity.badRequest().body(e.getMessage()); //substituição do token pra não ter espaço
    }
}

@PostMapping("/esqueci-senha")
public ResponseEntity<String> solicitarRedefinicaoSenha(@RequestBody EsqueciSenhaDTO dto) {
    try {
        usuarioService.processarPedidoRedefinicao(dto.getEmail());
    } catch (Exception e) {
        System.err.println("Erro ao processar pedido de redefinição: " + e.getMessage());
    }
    return ResponseEntity.ok("Se o e-mail estiver cadastrado, um link para redefinição de senha foi enviado.");
}

@PostMapping("/redefinir-senha")
public ResponseEntity<String> redefinirSenha(@RequestParam String token, @RequestBody RedefinirSenhaDTO dto) {
    try {
        if (dto.getNovaSenha() == null || dto.getNovaSenha().length() < 6) {
            return ResponseEntity.badRequest().body("A senha deve ter no mínimo 6 caracteres.");
        }
        usuarioService.redefinirSenhaComToken(token, dto.getNovaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso! Você já pode fazer o login com a nova senha.");
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

@GetMapping("/login-com-token")
public ResponseEntity<?> loginComToken(@CookieValue(name = "lembrar-me-token", required = false) String token) {
    if (token != null) {
        Usuario usuario = usuarioService.loginComTokenLembrarMe(token);
        if (usuario != null) {
            UsuarioDTO usuarioInfo = new UsuarioDTO();
            usuarioInfo.setId(usuario.getId());
            usuarioInfo.setEmail(usuario.getEmail());
            usuarioInfo.setTipoUsuario(usuario.getTipoUsuario());
            return ResponseEntity.ok(usuarioInfo);
        }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nenhuma sessão ativa.");
}

    @PostMapping("/validar-token-senha")
    public ResponseEntity<String> validarTokenSenha(@RequestBody Map<String, String> payload) {
        try {
            String token = payload.get("token");
            usuarioService.validarTokenSenha(token);
            return ResponseEntity.ok("Token de redefinição de senha válido.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @GetMapping("/logout") 
public ResponseEntity<Void> logout() {
    ResponseCookie cookie = ResponseCookie.from("lembrar-me-token", "") 
            .httpOnly(true)
            .secure(false) 
            .path("/")
            .maxAge(0) 
            .build();

    System.out.println("LOG: Usuário fez logout. Cookie 'lembrar-me' invalidado.");

    return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .header(HttpHeaders.LOCATION, "/index.html") 
            .build();
}
}