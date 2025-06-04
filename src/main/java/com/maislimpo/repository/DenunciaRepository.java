package com.maislimpo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.maislimpo.entity.Denuncia;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

	List<Denuncia> findByUsuarioId(Long usuarioId);
}
