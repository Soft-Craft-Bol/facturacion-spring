package com.gaspar.facturador.application.response;

import java.math.BigDecimal;

public class ProductoMasVendidoDTO {
    private Integer id;
    private String descripcion;
    private String imagen;
    private BigDecimal precioUnitario;
    private Long totalCantidadVendida;

    public ProductoMasVendidoDTO(Integer id, String descripcion, String imagen, BigDecimal precioUnitario, BigDecimal totalCantidadVendida) {
        this.id = id;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precioUnitario = precioUnitario;
        this.totalCantidadVendida = totalCantidadVendida.longValue();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getTotalCantidadVendida() {
        return totalCantidadVendida;
    }

    public void setTotalCantidadVendida(Long totalCantidadVendida) {
        this.totalCantidadVendida = totalCantidadVendida;
    }
}
