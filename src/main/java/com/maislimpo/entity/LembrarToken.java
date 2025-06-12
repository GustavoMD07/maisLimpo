package com.maislimpo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lembrar_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LembrarToken {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;

    // public void setDataExpiracao(LocalDateTime plusDays) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }

    // public void setUsuario(Usuario usuario) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }

    // public void setToken(String tokenValue) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // } //VSCODE bugando...

    public void setDataExpiracao(LocalDateTime plusDays) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Usuario getUsuario() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getDataExpiracao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setToken(String tokenValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
