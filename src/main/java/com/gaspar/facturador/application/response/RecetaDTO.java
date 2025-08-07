package com.gaspar.facturador.application.response;

import com.gaspar.facturador.application.rest.dto.RecetaInsumoGenericoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecetaDTO {
    private Integer id;

    @NotBlank(message = "El nombre de la receta es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Positive(message = "La cantidad de unidades debe ser positiva")
    private Integer cantidadUnidades;

    @Positive(message = "El peso unitario debe ser positivo")
    private BigDecimal pesoUnitario;

    @NotNull(message = "El producto es obligatorio")
    private Integer productoId;
    private String nombreProducto;

    @NotNull(message = "Los insumos genéricos son obligatorios")
    @Valid
    private List<RecetaInsumoGenericoDTO> insumosGenericos;

    private BigDecimal rendimiento;
    private String instrucciones;
    private Integer tiempoProduccionMinutos;
    private Date fechaCreacion;
    private Date fechaActualizacion;
}