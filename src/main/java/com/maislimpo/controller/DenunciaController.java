package com.maislimpo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maislimpo.DTO.DenunciaDTO;
import com.maislimpo.entity.Denuncia;
import com.maislimpo.entity.Usuario;
import com.maislimpo.service.DenunciaService;
import com.maislimpo.service.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/denuncias") 
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
//CrossOrigin serve pra liberar a colaboração do front + back
//Same-Origin Policy bloqueia naturalmente, mas o crossOrigin permite
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final UsuarioService usuarioService; 
    public static record DenunciaRequest(String textoDenuncia, String nomePraia, Long usuarioId) {}
    //atalho de criação de classe, transporta dados do frontend pro backend, um DTO mas sem precisar criar uma classe
    //pacote de dados

    @PostMapping
    public ResponseEntity<String> registrarDenuncia(@RequestBody DenunciaRequest request) {
        try {
    
            if (request.usuarioId() == null) {
                return ResponseEntity.badRequest().body("Erro: ID do usuário não foi enviado na requisição.");
            }
            Usuario usuarioAutor = usuarioService.buscarUsuarioPorId(request.usuarioId());

            if (usuarioAutor == null) {
                 return ResponseEntity.status(404).body("Erro: Usuário com ID " + request.usuarioId() + " não encontrado no sistema.");
            }

            denunciaService.registrarNovaDenuncia(usuarioAutor, request.nomePraia(), request.textoDenuncia());

            return ResponseEntity.ok("Denúncia registrada com sucesso! Obrigado por sua colaboração!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar denúncia: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DenunciaDTO>> buscarTodasAsDenuncias() {
    try {
        
        List<Denuncia> denuncias = denunciaService.buscarTodasAsDenuncias();

        //stream e map usados pra fazer essa conversão de entity para DTO
        List<DenunciaDTO> denunciasDTO = denuncias.stream()
            .map(denuncia -> new DenunciaDTO(
                denuncia.getId(),
                denuncia.getTextoDenuncia(),
                denuncia.getDataHoraDenuncia(),
                denuncia.getNomePraia(),
                denuncia.getUsuario().getEmail() 
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(denunciasDTO);

    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
    }
}
}