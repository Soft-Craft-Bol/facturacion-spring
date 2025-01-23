package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.FacturaDetalleEntity;
import org.springframework.data.repository.CrudRepository;


public interface FacturaDetalleCrudRepository extends CrudRepository<FacturaDetalleEntity, Long> {
}
