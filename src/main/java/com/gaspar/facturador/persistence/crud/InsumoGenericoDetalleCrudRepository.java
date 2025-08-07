package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoGenericoDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoGenericoDetalleCrudRepository extends JpaRepository<InsumoGenericoDetalleEntity, Long> {
}