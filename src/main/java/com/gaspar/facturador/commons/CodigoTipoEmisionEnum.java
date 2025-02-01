package com.gaspar.facturador.commons;

public enum CodigoTipoEmisionEnum {

    ONLINE(1), OFFLINE(2);
    private final int value;

    CodigoTipoEmisionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
