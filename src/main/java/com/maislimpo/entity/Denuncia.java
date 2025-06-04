package com.maislimpo.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Lob    //lob por conta que o VARCHAR tem um tamanho máximo (255), e como vai ser um texto grande, talvez precise ultrapassar.
    @Column(name = "texto_denuncia", nullable = false, columnDefinition="TEXT")
    private String textoDenuncia;

    @Column(name = "data_hora_denuncia", nullable = false)
    private LocalDateTime dataHoraDenuncia;

    @Column(name = "nome_praia", nullable = false, length = 100) 
    private String nomePraia;

    @ManyToOne(fetch = FetchType.LAZY) 		//carregamento tarde pra economizar dados
    @JoinColumn(name = "usuario_id", nullable = false)           //fk
    private Usuario usuario; 

}