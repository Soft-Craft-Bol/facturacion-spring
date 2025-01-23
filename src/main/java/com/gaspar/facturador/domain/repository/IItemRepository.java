package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.ItemEntity;

import java.util.Optional;



public interface IItemRepository {

    Optional<ItemEntity> findById(Integer id);
}
