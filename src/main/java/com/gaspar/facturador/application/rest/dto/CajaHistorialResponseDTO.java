package com.gaspar.facturador.application.rest.dto;

import com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CajaHistorialResponseDTO {
    private Long id;
    private String nombre;
    private String estado;
    private TurnoTrabajo turno;
    private BigDecimal montoInicial;
    private String sucursal;
    private String usuarioApertura;
    private String usuarioCierre;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private CierreCajaResponseDTO cierre;
}
