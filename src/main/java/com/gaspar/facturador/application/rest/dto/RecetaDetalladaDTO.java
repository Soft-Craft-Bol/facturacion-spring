package com.gaspar.facturador.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class RecetaDetalladaDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidadUnidades;
    private BigDecimal pesoUnitario;
    private String nombreProducto;
    private Integer productoId;
    private BigDecimal rendimiento;
    private String instrucciones;
    private Integer tiempoProduccionMinutos;
    private List<InsumoGenericoRecetaDTO> insumosGenericos;

    @Getter
    @Setter
    public static class InsumoGenericoRecetaDTO {
        private Long id;
        private String nombre;
        private String unidadMedida;
        private BigDecimal cantidad;
        private BigDecimal costo;
        private List<InsumoEspecificoDTO> opcionesEspecificas;
    }

    @Getter
    @Setter
    public static class InsumoEspecificoDTO {
        private Long id;
        private String nombre;
        private String marca;
        private Integer prioridad;
        private String unidades;
        private BigDecimal precioActual;
    }
}