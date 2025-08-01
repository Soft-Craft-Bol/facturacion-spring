package com.gaspar.facturador.persistence.entity;

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

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VentasDetalleEntity> detalles;

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

}