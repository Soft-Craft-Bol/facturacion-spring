package com.gaspar.facturador.persistence.dto;

public class StatDTO {
    private long numeroSucursales;
    private long inventario;
    private long facturasEmitidasHoy;

    // Constructor vacío
    public StatDTO() {}

    // Constructor con parámetros
    public StatDTO(long numeroSucursales, long inventario, long facturasEmitidasHoy) {
        this.numeroSucursales = numeroSucursales;
        this.inventario = inventario;
        this.facturasEmitidasHoy = facturasEmitidasHoy;
    }

    // Getters y Setters
    public long getNumeroSucursales() {
        return numeroSucursales;
    }

    public void setNumeroSucursales(long numeroSucursales) {
        this.numeroSucursales = numeroSucursales;
    }

    public long getInventario() {
        return inventario;
    }

    public void setInventario(long inventario) {
        this.inventario = inventario;
    }

    public long getFacturasEmitidasHoy() {
        return facturasEmitidasHoy;
    }

    public void setFacturasEmitidasHoy(long facturasEmitidasHoy) {
        this.facturasEmitidasHoy = facturasEmitidasHoy;
    }

    @Override
    public String toString() {
        return "StatDTO{" +
                "numeroSucursales=" + numeroSucursales +
                ", inventario=" + inventario +
                ", facturasEmitidasHoy=" + facturasEmitidasHoy +
                '}';
    }
}