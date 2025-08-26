package com.gaspar.facturador.domain;

import jakarta.xml.bind.annotation.*;
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

    @XmlAttribute(namespace = "http://www.w3.org/2001/XMLSchema-instance")
    private final String noNamespaceSchemaLocation = "facturaElectronicaCompraVenta.xsd";

    private CabeceraCompraVenta cabecera;
    private List<DetalleCompraVenta> detalle;
}
