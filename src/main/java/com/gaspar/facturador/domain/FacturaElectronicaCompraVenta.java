package com.gaspar.facturador.domain;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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
