package com.maislimpo.maislimpo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Lob; // Para textos mais longos
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "denuncias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id auto-incrementável pelo banco
    private Long id;

    @Lob // Indica que pode ser um texto longo (CLOB no banco)
    @Column(name = "texto_denuncia", nullable = false, columnDefinition="TEXT") // Garante que não seja nulo e define como TEXT
    private String textoDenuncia;

    @Column(name = "data_hora_denuncia", nullable = false)
    private LocalDateTime dataHoraDenuncia;

    @Column(name = "nome_praia", nullable = false, length = 100) // Define um tamanho máximo e não nulo
    private String nomePraia;

    @ManyToOne(fetch = FetchType.LAZY) // Muitas denúncias podem pertencer a um usuário. LAZY para performance.
    @JoinColumn(name = "usuario_id", nullable = false) // Nome da coluna da chave estrangeira no banco. Não pode ser nulo.
    private Usuario usuario; // Referência ao usuário que fez a denúncia

}