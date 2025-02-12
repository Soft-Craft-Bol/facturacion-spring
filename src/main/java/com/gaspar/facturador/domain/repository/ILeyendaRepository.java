package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ParametricaLeyendasDto;
import com.gaspar.facturador.persistence.view.LeyendaView;

import java.util.Optional;


public interface ILeyendaRepository {

    void save(ParametricaLeyendasDto parametricaLeyendasDto);
    void deleteAll();
    Optional<LeyendaView> obtenerLeyenda(String codigoActividad);
}
