package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.EstadoContacto;
import com.gaspar.facturador.persistence.entity.enums.TipoAsunto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "contacto")
@Getter
@Setter
public class ContactoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String telefono;
    private String correo;

    @Enumerated(EnumType.STRING)
    private TipoAsunto asunto;

    @Column(length = 250)
    private String detalles;

    @Enumerated(EnumType.STRING)
    private EstadoContacto atendido;

}
