package com.gaspar.facturador.application.request;

import javax.validation.constraints.NotNull;

public class ReversionFacturaRequest {

    @NotNull
    private Long idPuntoVenta;

    @NotNull
    private String cuf;

    @NotNull
    private String codigoMotivo;

    // Getters y Setters

    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    public String getCuf() {
        return cuf;
    }

    public void setCuf(String cuf) {
        this.cuf = cuf;
    }

    public String getCodigoMotivo() {
        return codigoMotivo;
    }

    public void setCodigoMotivo(String codigoMotivo) {
        this.codigoMotivo = codigoMotivo;
    }
}
