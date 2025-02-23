package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ItemCrudRepository extends CrudRepository<ItemEntity, Integer> {
    Optional<ItemEntity> findByCodigo(String codigo);
}
