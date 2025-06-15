package com.maislimpo.DTO;

import lombok.Getter;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class DenunciaDTO {

    private final Long id;
    private final String textoDenuncia;
    private final LocalDateTime dataHoraDenuncia;
    private final String nomePraia;
    private final String emailUsuario;

}
