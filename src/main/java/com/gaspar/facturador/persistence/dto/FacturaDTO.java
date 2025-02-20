package com.gaspar.facturador.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String xmlContent;

    public FacturaDTO(Long id, String codigoCliente, String nombreRazonSocial, String cuf, LocalDateTime fechaEmision, String estado, List<FacturaDetalleDTO> detalles) {
        this.id = id;
        this.codigoCliente = codigoCliente;
        this.nombreRazonSocial = nombreRazonSocial;
        this.cuf = cuf;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.detalles = detalles;
    }
}

