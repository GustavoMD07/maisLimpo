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
    
    // 1. ATUALIZAMOS O "PACOTE" DE DADOS PARA INCLUIR O ID DO USUÁRIO
    public static record DenunciaRequest(String textoDenuncia, String nomePraia, Long usuarioId) {}

    @PostMapping
    public ResponseEntity<String> registrarDenuncia(@RequestBody DenunciaRequest request) {
        try {
            // 2. ADEUS, "JEITINHO"! AGORA PEGAMOS O ID QUE VEIO DO FRONTEND
            if (request.usuarioId() == null) {
                return ResponseEntity.badRequest().body("Erro: ID do usuário não foi enviado na requisição.");
            }
            Usuario usuarioAutor = usuarioService.buscarUsuarioPorId(request.usuarioId());

            // A verificação de usuário nulo já existe no seu service, mas é bom ter aqui também
            if (usuarioAutor == null) {
                 return ResponseEntity.status(404).body("Erro: Usuário com ID " + request.usuarioId() + " não encontrado no sistema.");
            }

            denunciaService.registrarNovaDenuncia(usuarioAutor, request.nomePraia(), request.textoDenuncia());

            return ResponseEntity.ok("Denúncia registrada com sucesso! Obrigado por sua colaboração!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar denúncia: " + e.getMessage());
        }
    }
}