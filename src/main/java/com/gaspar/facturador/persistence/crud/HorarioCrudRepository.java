package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.HorarioEntity;
import org.springframework.data.repository.CrudRepository;


public interface HorarioCrudRepository extends CrudRepository<HorarioEntity,Long> {
}
