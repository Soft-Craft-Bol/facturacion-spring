package com.gaspar.facturador.commons;


public enum CodigoMetodoPagoEnum {

    EFECTIVO(1);

    private final int value;

    CodigoMetodoPagoEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
