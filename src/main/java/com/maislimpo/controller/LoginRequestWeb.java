package com.maislimpo.controller;

import com.maislimpo.entity.Usuario;
import com.maislimpo.exception.EmailNaoConfirmadoException;
import com.maislimpo.service.UsuarioService;
import com.maislimpo.DTO.UsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class LoginRequestWeb {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuario) {
        try {
            Usuario usuarioLogado = usuarioService.verificarCredenciais(usuario.getEmail(), usuario.getSenha());

            if(usuarioLogado != null) {
                return ResponseEntity.ok(usuarioLogado);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
        } catch(EmailNaoConfirmadoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email não confirmado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login: " + e.getMessage());
        }
    }

    

}
