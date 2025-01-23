package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.ClienteEntity;

import java.util.Optional;



public interface IClienteRepository {

    Optional<ClienteEntity> findById(Integer id);
}
