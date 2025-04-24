package com.gaspar.facturador.application.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalInfoDTO {
    private Integer id;
    private String nombre;
    private String departamento;
    private Integer cantidad;

}
