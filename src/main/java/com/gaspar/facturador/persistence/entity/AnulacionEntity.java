package com.gaspar.facturador.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class AnulacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuntoVenta;
    private String cuf;
    private String codigoMotivo;
}
