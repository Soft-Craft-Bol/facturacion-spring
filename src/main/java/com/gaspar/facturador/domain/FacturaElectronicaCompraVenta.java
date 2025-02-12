package com.gaspar.facturador.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "facturaElectronicaCompraVenta", propOrder = {
        "cabecera",
        "detalle"
})
@XmlRootElement(name = "facturaElectronicaCompraVenta")
public class FacturaElectronicaCompraVenta {

    private CabeceraCompraVenta cabecera;
    private List<DetalleCompraVenta> detalle;
}
