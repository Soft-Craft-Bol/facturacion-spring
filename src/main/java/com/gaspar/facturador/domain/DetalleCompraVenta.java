package com.gaspar.facturador.domain;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detalle", propOrder = {
        "actividadEconomica",
        "codigoProductoSin",
        "codigoProducto",
        "descripcion",
        "cantidad",
        "unidadMedida",
        "precioUnitario",
        "montoDescuento",
        "subTotal",
        "numeroSerie",
        "numeroImei"
})
@XmlRootElement(name = "detalle")
public class DetalleCompraVenta {

    private DetalleCompraVenta() {
    }

    @XmlElement(required = true)
    private String actividadEconomica;

    private int codigoProductoSin;

    @XmlElement(required = true)
    private String codigoProducto;

    @XmlElement(required = true)
    private String descripcion;

    @XmlElement(required = true)
    private BigDecimal cantidad;

    private int unidadMedida;

    @XmlElement(required = true)
    private BigDecimal precioUnitario;

    @XmlElement(required = true, nillable = true)
    private BigDecimal montoDescuento;

    @XmlElement(required = true)
    private BigDecimal subTotal;

    @XmlElement(nillable = true)
    protected String numeroSerie;

    @XmlElement(nillable = true)
    protected String numeroImei;

    public static class Builder {

        private DetalleCompraVenta detalleCompraVenta;

        public Builder() {
            this.detalleCompraVenta = new DetalleCompraVenta();
        }

        public Builder buildItem(
            ItemEntity item,
            String codigoActividad,
            BigDecimal cantidad,
            BigDecimal montoDescuento
        ) {

            this.detalleCompraVenta.actividadEconomica = codigoActividad;
            this.detalleCompraVenta.codigoProductoSin = item.getCodigoProductoSin();
            this.detalleCompraVenta.codigoProducto = item.getCodigo();
            this.detalleCompraVenta.descripcion = item.getDescripcion();
            this.detalleCompraVenta.unidadMedida = item.getUnidadMedida();
            this.detalleCompraVenta.precioUnitario = item.getPrecioUnitario();
            this.detalleCompraVenta.cantidad = cantidad;
            this.detalleCompraVenta.montoDescuento = montoDescuento;

            BigDecimal subTotal = (item.getPrecioUnitario().subtract(montoDescuento)).multiply(cantidad);

            this.detalleCompraVenta.subTotal = subTotal;

            return this;
        }

        public Builder setNumeroSerie(String numeroSerie) {
            this.detalleCompraVenta.numeroSerie = numeroSerie;
            return this;
        }

        public Builder setNumeroImei(String numeroImei) {
            this.detalleCompraVenta.numeroImei = numeroImei;
            return this;
        }

        public DetalleCompraVenta build() {
            return this.detalleCompraVenta;
        }
    }
}
