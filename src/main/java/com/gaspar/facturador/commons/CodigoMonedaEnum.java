package com.gaspar.facturador.commons;



public enum CodigoMonedaEnum {

    BOLIVIANO(1);

    private final int value;

    CodigoMonedaEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
