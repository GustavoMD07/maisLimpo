package com.maislimpo.vidanaagua.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	@NotBlank(message = "Nome não pode ser vazio")
	@Email(message = "Email inválido")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "senha não pode estar vazia")
	@Column(nullable = false)
	private String senha;
}
