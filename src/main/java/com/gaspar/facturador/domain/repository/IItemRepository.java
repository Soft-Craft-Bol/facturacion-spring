package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.ItemEntity;

import java.util.List;
import java.util.Optional;



public interface IItemRepository {

    Optional<ItemEntity> findById(Integer id);
    List<ItemEntity> findAll();
    ItemEntity save(ItemEntity item);
    void deleteById(Integer id);
    long count();
}
