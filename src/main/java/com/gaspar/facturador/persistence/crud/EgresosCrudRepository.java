package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EgresosCrudRepository extends CrudRepository<EgresosEntity,Long> {
    List<EgresosEntity> findByCajaId(Long cajaId);
}
