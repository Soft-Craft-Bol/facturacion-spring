package com.gaspar.facturador.domain;

import com.gaspar.facturador.commons.CodigoDocumentoSectorEnum;
import com.gaspar.facturador.commons.CodigoMetodoPagoEnum;
import com.gaspar.facturador.commons.CodigoMonedaEnum;
import com.gaspar.facturador.commons.CodigoTipoDocumentoIdentidadEnum;
import com.gaspar.facturador.persistence.entity.ClienteEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import lombok.Getter;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cabecera", propOrder = {
        "nitEmisor",
        "razonSocialEmisor",
        "municipio",
        "telefono",
        "numeroFactura",
        "cuf",
        "cufd",
        "codigoSucursal",
        "direccion",
        "codigoPuntoVenta",
        "fechaEmision",
        "nombreRazonSocial",
        "codigoTipoDocumentoIdentidad",
        "numeroDocumento",
        "complemento",
        "codigoCliente",
        "codigoMetodoPago",
        "numeroTarjeta",
        "montoTotal",
        "montoTotalSujetoIva",
        "codigoMoneda",
        "tipoCambio",
        "montoTotalMoneda",
        "montoGiftCard",
        "descuentoAdicional",
        "codigoExcepcion",
        "cafc",
        "leyenda",
        "usuario",
        "codigoDocumentoSector"
})
@XmlRootElement(name = "cabecera")
public class CabeceraCompraVenta {

    private CabeceraCompraVenta() {}

    private long nitEmisor;

    @XmlElement(required = true)
    private String razonSocialEmisor;

    @XmlElement(required = true)
    private String municipio;

    @XmlElement(required = true, nillable = true)
    private String telefono;

    private long numeroFactura;

    @XmlElement(required = true)
    private String cuf;

    @XmlElement(required = true)
    private String cufd;

    private int codigoSucursal;

    @XmlElement(required = true)
    private String direccion;

    @XmlElement(required = true, type = Integer.class, nillable = true)
    private Integer codigoPuntoVenta;

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaEmision;

    @XmlElement(required = true, nillable = true)
    private String nombreRazonSocial;

    private int codigoTipoDocumentoIdentidad;

    @XmlElement(required = true)
    private String numeroDocumento;

    @XmlElement(required = true, nillable = true)
    private String complemento;

    @XmlElement(required = true)
    private String codigoCliente;

    private int codigoMetodoPago;

    @XmlElement(required = true, type = Long.class, nillable = true)
    private Long numeroTarjeta;

    @XmlElement(required = true)
    private BigDecimal montoTotal;

    @XmlElement(required = true)
    private BigDecimal montoTotalSujetoIva;

    private int codigoMoneda;

    @XmlElement(required = true)
    private BigDecimal tipoCambio;

    @XmlElement(required = true)
    private BigDecimal montoTotalMoneda;

    @XmlElement(required = true, nillable = true)
    protected BigDecimal montoGiftCard;

    @XmlElement(required = true, nillable = true)
    private BigDecimal descuentoAdicional;

    @XmlElement(required = true, type = Integer.class, nillable = true)
    private Integer codigoExcepcion;

    @XmlElement(required = true, nillable = true)
    private String cafc;

    @XmlElement(required = true)
    private String leyenda;

    @XmlElement(required = true)
    private String usuario;

    @XmlElement(required = true)
    private Integer codigoDocumentoSector;

    public void setCuf(String cuf) {
        this.cuf = cuf;
    }

    public void setCufd(String cufd) {
        this.cufd = cufd;
    }

    public static class Builder {

        private final CabeceraCompraVenta cabeceraCompraVenta;

        public Builder() {
            cabeceraCompraVenta = new CabeceraCompraVenta();
        }

        public Builder buildEmpresa(PuntoVentaEntity puntoVenta) {
            cabeceraCompraVenta.nitEmisor = puntoVenta.getSucursal().getEmpresa().getNit();
            cabeceraCompraVenta.razonSocialEmisor = puntoVenta.getSucursal().getEmpresa().getRazonSocial();
            cabeceraCompraVenta.municipio = puntoVenta.getSucursal().getMunicipio();
            cabeceraCompraVenta.telefono = puntoVenta.getSucursal().getTelefono();
            cabeceraCompraVenta.codigoSucursal = puntoVenta.getSucursal().getCodigo();
            cabeceraCompraVenta.direccion = puntoVenta.getSucursal().getDireccion();
            cabeceraCompraVenta.codigoPuntoVenta = puntoVenta.getCodigo();

            return this;
        }

        public Builder buildCliente(ClienteEntity cliente) {
            cabeceraCompraVenta.nombreRazonSocial = cliente.getNombreRazonSocial();
            if (cliente.getComplemento() != null) {
                cabeceraCompraVenta.codigoTipoDocumentoIdentidad = CodigoTipoDocumentoIdentidadEnum.CI.getValue();
                cabeceraCompraVenta.complemento = cliente.getComplemento();
            } else {
                cabeceraCompraVenta.codigoTipoDocumentoIdentidad = CodigoTipoDocumentoIdentidadEnum.NIT.getValue();
            }
            cabeceraCompraVenta.numeroDocumento = cliente.getNumeroDocumento().toString();
            cabeceraCompraVenta.codigoCliente = cliente.getCodigoCliente();

            return this;
        }

        public Builder buildPago(BigDecimal total) {
            cabeceraCompraVenta.codigoMetodoPago = CodigoMetodoPagoEnum.EFECTIVO.getValue();
            cabeceraCompraVenta.numeroTarjeta = null;
            cabeceraCompraVenta.montoTotal = total;
            cabeceraCompraVenta.montoTotalSujetoIva = total;
            cabeceraCompraVenta.codigoMoneda = CodigoMonedaEnum.BOLIVIANO.getValue();
            cabeceraCompraVenta.tipoCambio = BigDecimal.ONE;
            cabeceraCompraVenta.montoTotalMoneda = total;
            cabeceraCompraVenta.descuentoAdicional = BigDecimal.ZERO;

            return this;
        }

        public Builder setUsuario(String usuario) {
            cabeceraCompraVenta.usuario = usuario;
            return this;
        }

        public Builder setNumeroFactura(long numeroFactura) {
            cabeceraCompraVenta.numeroFactura = numeroFactura;
            return this;
        }

        public Builder setCuf(String cuf) {
            cabeceraCompraVenta.cuf = cuf;
            return this;
        }

        public Builder setFechaEmision(XMLGregorianCalendar fechaEmision) {
            cabeceraCompraVenta.fechaEmision = fechaEmision;
            return this;
        }

        public Builder setLeyenda(String leyenda) {
            cabeceraCompraVenta.leyenda = leyenda;
            return this;
        }

        public Builder setNitClienteExcepcion(Boolean permitirNitInvalido) {
            cabeceraCompraVenta.codigoExcepcion = (permitirNitInvalido != null && permitirNitInvalido) ? 1 : 0;
            return this;
        }

        public CabeceraCompraVenta build() {
            cabeceraCompraVenta.codigoDocumentoSector = CodigoDocumentoSectorEnum.COMPRA_VENTA.getValue();
            return cabeceraCompraVenta;
        }
    }
}
