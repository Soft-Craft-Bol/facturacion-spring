package com.gaspar.facturador.persistence.dto;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuntoVentaDTO {
    private Integer id;
    private String nombre;
}
