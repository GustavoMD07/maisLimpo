package com.maislimpo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maislimpo.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByResetSenhaToken(String token);
	Optional<Usuario> findByTokenConfirmacao(String tokenConfirmacao);
}
