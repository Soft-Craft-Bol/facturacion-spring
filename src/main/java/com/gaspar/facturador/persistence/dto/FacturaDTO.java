package com.gaspar.facturador.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {
    private Long id;
    private String codigoCliente;
    private String nombreRazonSocial;
    private String cuf;
    private LocalDateTime fechaEmision;
    private String estado;
    private List<FacturaDetalleDTO> detalles;
}

