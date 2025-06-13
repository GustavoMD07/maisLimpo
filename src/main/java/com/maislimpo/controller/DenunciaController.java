package com.maislimpo.controller;

import com.maislimpo.service.DenunciaService;
import com.maislimpo.service.UsuarioService; 
import com.maislimpo.entity.Usuario; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/denuncias") 
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final UsuarioService usuarioService; 
    
    public static record DenunciaRequest(String textoDenuncia, String nomePraia, Long usuarioId) {}

    @PostMapping
    public ResponseEntity<String> registrarDenuncia(@RequestBody DenunciaRequest request) {
        try {
            if (request.usuarioId() == null) {
                return ResponseEntity.badRequest().body("Erro: ID do usuário não foi enviado na requisição.");
            }
            Usuario usuario = usuarioService.buscarUsuarioPorId(request.usuarioId());

            if (usuario == null) {
                 return ResponseEntity.status(404).body("Erro: Usuário com ID " + request.usuarioId() + " não encontrado no sistema.");
            }

            // O resto do código continua como estava
            denunciaService.registrarNovaDenuncia(usuario, request.nomePraia(), request.textoDenuncia());

            return ResponseEntity.ok("Denúncia registrada com sucesso! Obrigado por sua colaboração!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar denúncia: " + e.getMessage());
        }
    }
}