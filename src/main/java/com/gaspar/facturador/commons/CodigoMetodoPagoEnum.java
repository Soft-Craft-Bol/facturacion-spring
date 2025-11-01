package com.gaspar.facturador.commons;

public enum CodigoMetodoPagoEnum {
    EFECTIVO(1, "EFECTIVO"),
    TARJETA(2, "TARJETA"),
    CHEQUE(3, "CHEQUE"),
    VALES(4, "VALES"),
    OTROS(5, "OTROS"),
    PAGO_POSTERIOR(6, "PAGO POSTERIOR"),
    TRANSFERENCIA_BANCARIA(7, "TRANSFERENCIA BANCARIA"),
    DEPOSITO_EN_CUENTA(8, "DEPOSITO EN CUENTA"),
    TRANSFERENCIA_SWIFT(9, "TRANSFERENCIA SWIFT"),
    GIFT_CARD(27, "GIFT-CARD"),
    CANAL_DE_PAGO(31, "CANAL DE PAGO"),
    BILLETERA_MOVIL(32, "BILLETERA MOVIL"),
    PAGO_ONLINE(33, "PAGO ONLINE"),
    DEBITO_AUTOMATICO(295, "DEBITO AUTOMATICO");

    private final int value;
    private final String descripcion;

    CodigoMetodoPagoEnum(int value, String descripcion) {
        this.value = value;
        this.descripcion = descripcion;
    }

    public int getValue() {
        return value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static CodigoMetodoPagoEnum fromValue(int value) {
        for (CodigoMetodoPagoEnum metodo : values()) {
            if (metodo.value == value) {
                return metodo;
            }
        }
        throw new IllegalArgumentException("Código de método de pago no válido: " + value);
    }

    public static CodigoMetodoPagoEnum fromDescripcion(String descripcion) {
        for (CodigoMetodoPagoEnum metodo : values()) {
            if (metodo.descripcion.equalsIgnoreCase(descripcion)) {
                return metodo;
            }
        }
        throw new IllegalArgumentException("Descripción de método de pago no válida: " + descripcion);
    }
}