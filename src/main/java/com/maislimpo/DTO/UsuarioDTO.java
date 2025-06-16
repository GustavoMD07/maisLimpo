package com.maislimpo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    private String tipoUsuario;

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
    message = "A senha deve ter no mínimo 6 caracteres, com pelo menos um número, uma letra maiúscula e uma minúscula.")
    private String senha;

    private boolean lembrar;

}