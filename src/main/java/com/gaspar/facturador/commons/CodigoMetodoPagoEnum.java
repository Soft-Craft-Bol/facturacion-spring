package com.gaspar.facturador.commons;

public enum CodigoMetodoPagoEnum {

    EFECTIVO(1),
    TARJETA(2),
    TRANSFERENCIA_BANCARIA(7),
    BILLETERA_MOVIL(32),  // Para QR (ej: Yape, Mercado Pago)
    CANAL_DE_PAGO(31),    // QR genérico
    PAGO_POSTERIOR(6),    // Si aplica a crédito
    OTROS(5);             // Para casos no cubiertos

    private final int value;

    CodigoMetodoPagoEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Método opcional para buscar un enum por su valor numérico
    public static CodigoMetodoPagoEnum fromValue(int value) {
        for (CodigoMetodoPagoEnum metodo : CodigoMetodoPagoEnum.values()) {
            if (metodo.getValue() == value) {
                return metodo;
            }
        }
        throw new IllegalArgumentException("Código de método de pago no válido: " + value);
    }
}