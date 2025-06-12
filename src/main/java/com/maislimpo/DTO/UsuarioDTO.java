package com.maislimpo.DTO;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UsuarioDTO {

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Pattern.List({
        @Pattern(regexp = ".*[a-z].*", message = "A senha deve conter pelo menos uma letra minúscula."),
        @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula.")
    })
    private String senha;

    // Se quisermos validar a confirmação de senha no backend,
    // precisaríamos adicionar um campo aqui e criar uma anotação customizada
    // para comparar os dois. Mas essa verificação é mais simples e crucial no frontend.
}