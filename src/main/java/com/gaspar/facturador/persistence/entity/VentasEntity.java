package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ventas")
public class VentasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @Column(name = "metodo_pago")
    @Enumerated(EnumType.STRING)
    private TipoPagoEnum metodoPago;

    private BigDecimal monto;

    private String estado;

    @Column(name = "monto_recibido", precision = 10, scale = 2)
    private BigDecimal montoRecibido;

    @Column(name = "monto_devuelto", precision = 10, scale = 2)
    private BigDecimal montoDevuelto;

    @ManyToOne
    @JoinColumn(name = "caja_id")
    private CajasEntity caja;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VentasDetalleEntity> detalles;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VentaPagoEntity> metodosPago;

    @Column(name = "tipo_comprobante")
    @Enumerated(EnumType.STRING)
    private TipoComprobanteEnum tipoComprobante;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity vendedor;

    @ManyToOne
    @JoinColumn(name = "id_punto_venta", nullable = false)
    private PuntoVentaEntity puntoVenta;

    @OneToOne
    @JoinColumn(name = "id_factura", nullable = true)
    private FacturaEntity factura;

    @Column(name = "anulada")
    private Boolean anulada = false;

    @Column(name = "motivo_anulacion")
    private String motivoAnulacion;

    @Column(name = "fecha_anulacion")
    private Date fechaAnulacion;

    @Column(name = "usuario_anulacion")
    private String usuarioAnulacion;

    @Column(name = "es_credito")
    private Boolean esCredito = false;

    @Column(name = "dias_credito")
    private Integer diasCredito;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuenta_por_cobrar_id", referencedColumnName = "id")
    private CuentaPorCobrarEntity cuentaPorCobrar;
}