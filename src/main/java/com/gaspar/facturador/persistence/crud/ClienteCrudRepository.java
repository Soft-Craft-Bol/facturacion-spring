package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;


public interface ClienteCrudRepository extends CrudRepository<ClienteEntity, Integer> {

}
