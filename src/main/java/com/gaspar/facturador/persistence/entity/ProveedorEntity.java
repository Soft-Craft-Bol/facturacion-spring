package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.TipoProveedor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedores")
public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_razon_social")
    private String nombreRazonSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proveedor")
    private TipoProveedor tipoProveedor;

    @Length(max = 1024)
    private String direccion;

    private Long telefono;

    private String email;
}
