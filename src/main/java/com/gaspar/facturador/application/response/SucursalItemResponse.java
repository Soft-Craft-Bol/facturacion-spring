package com.gaspar.facturador.application.response;

import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import lombok.Data;

@Data
public class SucursalItemResponse {
    private Integer id;
    private Integer sucursalId;
    private Integer itemId;
    private Integer cantidad;

    public SucursalItemResponse(SucursalItemEntity entity) {
        this.id = entity.getId();
        this.sucursalId = entity.getSucursal().getId();
        this.itemId = entity.getItem().getId();
        this.cantidad = entity.getCantidad();
    }
}