package com.maislimpo.repository;

import com.maislimpo.entity.LembrarToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LembrarTokenRepository extends JpaRepository<LembrarToken, Long> {
    Optional<LembrarToken> findByToken(String token);
}
