package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.TipoProovedor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@Table(name = "proovedores")
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre_razon_social")
    private String nombreRazonSocial;

    @NotNull
    @Column(name="tipo_proovedor")
    @Enumerated(EnumType.STRING)
    private TipoProovedor tipoProovedor;

    @Length(max = 1024)
    private String direccion;

    private long telefono;

    private String email;
}
