package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.data.repository.CrudRepository;

public interface EgresosCrudRepository extends CrudRepository<EgresosEntity,Long> {
}
