package com.gaspar.facturador.application.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
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

    private String nombreProducto; // Para mostrar en frontend

    @NotNull(message = "Los insumos son obligatorios")
    @Valid
    private List<InsumoRecetaDTO> insumos;

    private BigDecimal rendimiento;
    private String instrucciones;
    private Integer tiempoProduccionMinutos;

    // Campos de auditoría para mostrar
    private Date fechaCreacion;
    private Date fechaActualizacion;
}