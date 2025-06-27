package com.gaspar.facturador.application.request;

import com.gaspar.facturador.persistence.entity.enums.TipoProveedor;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProveedorRequest {
    private String nombreRazonSocial;

    @NotNull(message = "El tipo de proveedor es obligatorio")
    private TipoProveedor tipoProveedor;

    @Length(max = 1024)
    private String direccion;

    private Long telefono;
    private String email;
}
