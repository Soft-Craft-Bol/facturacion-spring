package com.gaspar.facturador.application.rest.dto;

import java.time.LocalDateTime;

public class EventoSignificativoRequest {

    private Long idPuntoVenta;
    private String descripcion;
    private Integer codigoMotivoEvento;
    private String cufdEvento;
    private LocalDateTime fechaHoraInicioEvento;
    private LocalDateTime fechaHoraFinEvento;

    // Getters and setters
    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigoMotivoEvento() {
        return codigoMotivoEvento;
    }

    public void setCodigoMotivoEvento(Integer codigoMotivoEvento) {
        this.codigoMotivoEvento = codigoMotivoEvento;
    }

    public String getCufdEvento() {
        return cufdEvento;
    }

    public void setCufdEvento(String cufdEvento) {
        this.cufdEvento = cufdEvento;
    }

    public LocalDateTime getFechaHoraInicioEvento() {
        return fechaHoraInicioEvento;
    }

    public void setFechaHoraInicioEvento(LocalDateTime fechaHoraInicioEvento) {
        this.fechaHoraInicioEvento = fechaHoraInicioEvento;
    }

    public LocalDateTime getFechaHoraFinEvento() {
        return fechaHoraFinEvento;
    }

    public void setFechaHoraFinEvento(LocalDateTime fechaHoraFinEvento) {
        this.fechaHoraFinEvento = fechaHoraFinEvento;
    }
}
