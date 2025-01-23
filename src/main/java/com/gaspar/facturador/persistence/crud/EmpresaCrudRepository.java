package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.EmpresaEntity;
import org.springframework.data.repository.CrudRepository;


public interface EmpresaCrudRepository extends CrudRepository<EmpresaEntity, Integer> {
}
