package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ClienteCrudRepository extends CrudRepository<ClienteEntity, Integer> {
    long count();
    Optional<ClienteEntity> findByNumeroDocumento(Long numeroDocumento);
}
