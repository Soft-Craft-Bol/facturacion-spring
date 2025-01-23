package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ParametroEntity;
import org.springframework.data.repository.CrudRepository;


public interface ParametroCrudRepository extends CrudRepository<ParametroEntity, Long> {
}
