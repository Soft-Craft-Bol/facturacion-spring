package com.gaspar.facturador.persistence.entity.enums;

public enum TipoPagoEnum {
    EFECTIVO(1),
    BILLETERA_MOVIL(37);

    private final int id;

    TipoPagoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}