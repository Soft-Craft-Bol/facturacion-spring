package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@Entity
@Table(name = "factura_cabecera")
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long nitEmisor;
    private String razonSocialEmisor;
    private String municipio;
    private String telefono;
    private Long numeroFactura;

    private String cuf;
    private String cufd;
    private Integer codigoSucursal;
    private String direccion;
    private Integer codigoPuntoVenta;

    private LocalDateTime fechaEmision;
    private String nombreRazonSocial;
    private Integer codigoTipoDocumentoIdentidad;
    private String numeroDocumento;
    private String complemento;
    private String codigoCliente;

    private Integer codigoMetodoPago;
    private Long numeroTarjeta;
    private BigDecimal montoTotal;
    private BigDecimal montoTotalSujetoIva;
    private BigDecimal montoGiftCard;
    private BigDecimal descuentoAdicional;
    private Integer codigoExcepcion;
    private String cafc;
    private Integer codigoMoneda;
    private BigDecimal tipoCambio;
    private BigDecimal montoTotalMoneda;
    private String leyenda;
    private String usuario;
    private Integer codigoDocumentoSector;

    private String estado;
    private String emailCliente;
    @Column(columnDefinition = "TEXT")
    private String xmlContent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_punto_venta")
    private PuntoVentaEntity puntoVenta;

    @OneToMany(
        mappedBy = "factura",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<FacturaDetalleEntity> detalleList;
    private String tipoVenta;
}
