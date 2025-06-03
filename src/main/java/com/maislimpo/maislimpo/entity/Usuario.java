package com.maislimpo.maislimpo.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
	
	//mapeando pro JPA
	
	@Id //pk
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(name = "email_confirmado", nullable = false)
	private boolean emailConfirmado = false;  // false = 0, true = 1

	@Column(name = "token_confirmacao", unique = true) 
	private String tokenConfirmacao; 

	@Column(name = "data_expiracao_token")
	private LocalDateTime dataExpiracaoToken; 
	
	@Column(name = "tipo_usuario", length = 20)
	private String tipoUsuario;

}
