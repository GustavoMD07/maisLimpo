package com.maislimpo.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import com.maislimpo.entity.Denuncia;
import com.maislimpo.entity.Usuario;
import com.maislimpo.repository.DenunciaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;

    @Transactional 
    public Denuncia registrarNovaDenuncia(Usuario usuarioAutor, String nomePraia, String textoDenuncia) {
        if (usuarioAutor == null) {
            throw new IllegalArgumentException("Usuário autor não pode ser nulo para registrar a denúncia.");
        }
        if (nomePraia == null || nomePraia.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da praia não pode ser vazio para registrar a denúncia.");
        }
        if (textoDenuncia == null || textoDenuncia.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto da denúncia não pode ser vazio.");
        }

        Denuncia novaDenuncia = new Denuncia();
        novaDenuncia.setUsuario(usuarioAutor);
        novaDenuncia.setNomePraia(nomePraia.trim());
        novaDenuncia.setTextoDenuncia(textoDenuncia.trim());
        novaDenuncia.setDataHoraDenuncia(LocalDateTime.now()); 

        Denuncia denunciaSalva = denunciaRepository.save(novaDenuncia);
        
        System.out.println("LOG DenunciaService: Nova denúncia registrada com ID: " + denunciaSalva.getId() +
                           " para a praia: " + denunciaSalva.getNomePraia() +
                           " pelo usuário ID: " + denunciaSalva.getUsuario().getId());

        return denunciaSalva;
    }
    
     public List<Denuncia> buscarTodasAsDenuncias() {
         return denunciaRepository.findAll();
     }

     public List<Denuncia> buscarDenunciasPorUsuario(Long usuarioId) {
          return denunciaRepository.findByUsuarioId(usuarioId);
     }
    
     public Denuncia buscarDenunciaPorId(Long id) {
         return denunciaRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Denúncia com ID " + id + " não encontrada."));
     }

     public void deletarDenuncia(Long id) { //ainda não implementado, mas seria apenas para admin's
         if (!denunciaRepository.existsById(id)) {
             throw new IllegalArgumentException("Não é possível deletar: Denúncia com ID " + id + " não encontrada.");
         }
         denunciaRepository.deleteById(id);
         System.out.println("LOG DenunciaService: Denúncia com ID " + id + " deletada.");
     }

}