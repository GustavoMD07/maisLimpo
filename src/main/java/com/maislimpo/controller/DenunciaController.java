package com.maislimpo.controller;

import com.maislimpo.service.DenunciaService;
import com.maislimpo.service.UsuarioService; 
import com.maislimpo.entity.Usuario; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuario/denuncias") 
@AllArgsConstructor
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final UsuarioService usuarioService; 
    public static record DenunciaRequest(String textoDenuncia, String nomePraia) {}

    @PostMapping
    public ResponseEntity<String> registrarDenuncia(@RequestBody DenunciaRequest request) {
        try {
            // ================== ATENÇÃO: JEITINHO TEMPORÁRIO ==================
            // Como ainda não temos o login seguro, não sabemos QUEM está enviando a denúncia.
            // Para fazer funcionar, vamos pegar um usuário fixo (ex: o usuário de ID 1).
            // QUANDO a gente implementar o Spring Security, a gente vai pegar o usuário logado de verdade.
            Usuario usuarioFixo = usuarioService.buscarUsuarioPorId(1L);
            // ====================================================================

            if (usuarioFixo == null) {
                 return ResponseEntity.status(500).body("Erro: Usuário fixo para denúncia não encontrado no sistema.");
            }

            denunciaService.registrarNovaDenuncia(usuarioFixo, request.nomePraia(), request.textoDenuncia());

            return ResponseEntity.ok("Denúncia registrada com sucesso! Obrigado por sua colaboração!");

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Erro ao registrar denúncia: " + e.getMessage());
        }
    }
}