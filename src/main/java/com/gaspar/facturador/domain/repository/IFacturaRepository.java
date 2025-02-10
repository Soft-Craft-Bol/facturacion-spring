package com.gaspar.facturador.domain.repository;


import com.gaspar.facturador.persistence.entity.FacturaEntity;

import java.util.List;
import java.util.Optional;

public interface IFacturaRepository {

    Long obtenerNumeroFactura();
    FacturaEntity save(FacturaEntity factura);
    Optional<FacturaEntity> findById(Long id);
    Optional<FacturaEntity> findByCuf(String cuf);
    List<FacturaEntity> findAll();
    void updateEstado(Long id, String estado);
    void delete(Long id);
}
