package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_significativos")
@Getter @Setter
public class EventoSignificativoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "punto_venta_id", nullable = false)
    private PuntoVentaEntity puntoVenta;

    @Column(name = "codigo_recepcion_evento", nullable = false)
    private Long codigoRecepcionEvento;

    @Column(name = "codigo_motivo", nullable = false)
    private Integer codigoMotivo;

    @Column(name = "cufd_evento", nullable = false, length = 100)
    private String cufdEvento;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    // Datos del paquete (opcionales)
    @Column(name = "codigo_recepcion_paquete", length = 50)
    private String codigoRecepcionPaquete;

    @Column(name = "codigo_evento_paquete")
    private Long codigoEventoPaquete;

    @Column(name = "cantidad_facturas")
    private Integer cantidadFacturas;

    // Datos de validación (opcionales)
    @Column(name = "codigo_estado_validacion")
    private Integer codigoEstadoValidacion;

    @Column(name = "estado_validacion", length = 50)
    private String estadoValidacion;

    @Column(name = "fecha_procesamiento")
    private LocalDateTime fechaProcesamiento;

    // Datos comunes
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "vigente", nullable = false)
    private boolean vigente;

    @Column(name = "etapa", nullable = false, length = 20)
    private String etapa; // REGISTRADO, PAQUETE_ENVIADO, VALIDADO, ERROR

    // Método para actualizar con respuesta de paquete
    public void actualizarConRespuestaPaquete(String codigoRecepcion, Long codigoEvento, Integer cantidadFacturas) {
        this.codigoRecepcionPaquete = codigoRecepcion;
        this.codigoEventoPaquete = codigoEvento;
        this.cantidadFacturas = cantidadFacturas;
        this.etapa = "PAQUETE_ENVIADO";
    }

    // Método para actualizar con respuesta de validación
    public void actualizarConValidacion(Integer codigoEstado, String estado, LocalDateTime fechaProcesamiento) {
        this.codigoEstadoValidacion = codigoEstado;
        this.estadoValidacion = estado;
        this.fechaProcesamiento = fechaProcesamiento;
        this.etapa = codigoEstado == 908 ? "ERROR_VALIDACION" : "VALIDADO";
    }
}