package com.maislimpo.controller;

import com.maislimpo.DTO.UsuarioDTO;
import com.maislimpo.entity.Usuario;
import com.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.service.UsuarioService;
import jakarta.validation.Valid; // Importante
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuario) {
        try {
            Usuario usuarioLogado = usuarioService.verificarCredenciais(usuario.getEmail(), usuario.getSenha());

            if(usuarioLogado != null) {
                // Em vez de retornar o objeto inteiro com a senha hasheada,
                // é uma boa prática não expor isso. Mas por enquanto, tudo bem.
                return ResponseEntity.ok(usuarioLogado);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
        } catch(EmailNaoConfirmadoException e) {
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
}