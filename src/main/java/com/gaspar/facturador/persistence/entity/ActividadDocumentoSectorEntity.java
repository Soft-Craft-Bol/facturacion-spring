package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "actividad_documento_sector")
public class ActividadDocumentoSectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String codigoActividad;

    private Integer codigoDocumentoSector;

    @Column(length = 10)
    private String tipoDocumentoSector;
}
