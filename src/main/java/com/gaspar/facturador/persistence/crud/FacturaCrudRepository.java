package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.FacturaEntity;
import org.springframework.data.repository.CrudRepository;


public interface FacturaCrudRepository extends CrudRepository<FacturaEntity, Long> {
}
