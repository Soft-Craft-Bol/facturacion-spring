package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;


public interface ItemCrudRepository extends CrudRepository<ItemEntity, Integer> {

}
