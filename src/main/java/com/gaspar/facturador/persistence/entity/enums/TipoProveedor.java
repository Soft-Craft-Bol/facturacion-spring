package com.gaspar.facturador.persistence.entity.enums;

public enum TipoProveedor {
    INDIVIDUAL("Individual"),
    EMPRESA("Empresa");

    private final String descripcion;

    TipoProveedor(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
