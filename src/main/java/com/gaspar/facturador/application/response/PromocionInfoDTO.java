package com.gaspar.facturador.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromocionInfoDTO {
    private Integer itemId;
    private Integer sucursalId;
    private Double descuento;
}