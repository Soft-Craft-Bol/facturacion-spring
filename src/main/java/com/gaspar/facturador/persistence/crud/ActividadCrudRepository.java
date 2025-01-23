package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ActividadEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActividadCrudRepository extends CrudRepository<ActividadEntity, Long> {
}
