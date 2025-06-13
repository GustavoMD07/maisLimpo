package com.maislimpo.DTO;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class UsuarioDTO {

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*//d).{6,}$",
    message = "A senha deve ter no mínimo 6 caracteres, com pelo menos um número, uma letra maiúscula e uma minúscula.")
    private String senha;

    private boolean lembrar;

}