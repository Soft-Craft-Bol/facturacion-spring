package com.gaspar.facturador.commons;

public enum CodigoAmbienteEnum {

    PRODUCCION(1), PRUEBAS_PILOTO(2);
    private final int value;

    private CodigoAmbienteEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
