package com.gaspar.facturador.commons;

public enum CodigoModalidadEmisionEnum {

    ELECTRONICA_EN_LINEA(1);
    private final int value;

    CodigoModalidadEmisionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
