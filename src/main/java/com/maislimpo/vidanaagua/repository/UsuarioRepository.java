package com.maislimpo.vidanaagua.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maislimpo.vidanaagua.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}
