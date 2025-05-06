package com.gaspar.facturador.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PromocionDTO {
    private Integer id;
    private Integer itemId;
    private Integer sucursalId;
    private Double descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;

    // Constructor para consulta JPA
    public PromocionDTO(Long id, Long itemId, Long sucursalId, Double descuento,
                        LocalDate fechaInicio, LocalDate fechaFin, boolean activa) {
        this.id = id.intValue();
        this.itemId = itemId.intValue();
        this.sucursalId = sucursalId.intValue();
        this.descuento = descuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = activa;
    }
}