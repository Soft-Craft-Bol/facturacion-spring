package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InsumoCrudRepository extends CrudRepository<InsumoEntity,Long>{
    //Optional<InsumoEntity> findByCodigo(String codigo);

    @Override
    long count();
}
