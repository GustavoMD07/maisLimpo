package com.maislimpo.maislimpo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import com.maislimpo.maislimpo.entity.Denuncia;
import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.repository.DenunciaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    // Poderíamos injetar o UsuarioRepository se precisássemos validar o usuário aqui,
    // mas como o objeto Usuario já virá da tela (e foi autenticado),
    // podemos confiar nele por enquanto para o ato de registrar a denúncia.

    /**
     * Registra uma nova denúncia no sistema.
     *
     * @param usuarioAutor O usuário que está fazendo a denúncia.
     * @param nomePraia O nome da praia referente à denúncia.
     * @param textoDenuncia O conteúdo da denúncia.
     * @return A entidade Denuncia que foi salva no banco de dados.
     * @throws IllegalArgumentException Se algum dos parâmetros obrigatórios for nulo ou inválido.
     */
    @Transactional // Garante que a operação de salvar seja atômica
    public Denuncia registrarNovaDenuncia(Usuario usuarioAutor, String nomePraia, String textoDenuncia) {
        // Validações básicas dos parâmetros
        if (usuarioAutor == null) {
            throw new IllegalArgumentException("Usuário autor não pode ser nulo para registrar a denúncia.");
        }
        if (nomePraia == null || nomePraia.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da praia não pode ser vazio para registrar a denúncia.");
        }
        if (textoDenuncia == null || textoDenuncia.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto da denúncia não pode ser vazio.");
        }

        // Cria a nova entidade Denuncia
        Denuncia novaDenuncia = new Denuncia();
        novaDenuncia.setUsuario(usuarioAutor);
        novaDenuncia.setNomePraia(nomePraia.trim());
        novaDenuncia.setTextoDenuncia(textoDenuncia.trim());
        novaDenuncia.setDataHoraDenuncia(LocalDateTime.now()); // Define a data e hora atuais

        // Salva a denúncia usando o repositório
        Denuncia denunciaSalva = denunciaRepository.save(novaDenuncia);
        
        System.out.println("LOG DenunciaService: Nova denúncia registrada com ID: " + denunciaSalva.getId() +
                           " para a praia: " + denunciaSalva.getNomePraia() +
                           " pelo usuário ID: " + denunciaSalva.getUsuario().getId());

        return denunciaSalva;
    }

    // --- Outros métodos que podem ser úteis no futuro ---

    /**
     * Busca todas as denúncias registradas.
     * @return Uma lista de todas as denúncias.
     */
    // public List<Denuncia> buscarTodasAsDenuncias() {
    //     return denunciaRepository.findAll();
    // }

    /**
     * Busca denúncias por ID do usuário.
     * @param usuarioId O ID do usuário.
     * @return Uma lista de denúncias feitas pelo usuário.
     */
     public List<Denuncia> buscarDenunciasPorUsuario(Long usuarioId) {
         // Se tivéssemos o método findByUsuarioId no DenunciaRepository:
         // return denunciaRepository.findByUsuarioId(usuarioId);
         // Senão, teríamos que buscar todas e filtrar, ou criar o método no repo.
         // Por agora, vamos deixar comentado.
         throw new UnsupportedOperationException("Método ainda não implementado.");
     }
    
    /**
     * Busca uma denúncia pelo seu ID.
     * @param id O ID da denúncia.
     * @return A denúncia encontrada, ou lança uma exceção se não encontrar.
     */
     public Denuncia buscarDenunciaPorId(Long id) {
         return denunciaRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Denúncia com ID " + id + " não encontrada."));
     }

    /**
     * Deleta uma denúncia pelo seu ID.
     * @param id O ID da denúncia a ser deletada.
     */
     @Transactional
     public void deletarDenuncia(Long id) {
         if (!denunciaRepository.existsById(id)) {
             throw new IllegalArgumentException("Não é possível deletar: Denúncia com ID " + id + " não encontrada.");
         }
         denunciaRepository.deleteById(id);
         System.out.println("LOG DenunciaService: Denúncia com ID " + id + " deletada.");
     }

}