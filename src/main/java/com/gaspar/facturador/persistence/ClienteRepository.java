package com.gaspar.facturador.persistence;

import com.gaspar.facturador.domain.repository.IClienteRepository;
import com.gaspar.facturador.persistence.crud.ClienteCrudRepository;
import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ClienteRepository implements IClienteRepository {

    private final ClienteCrudRepository clienteCrudRepository;

    public ClienteRepository(ClienteCrudRepository clienteCrudRepository) {
        this.clienteCrudRepository = clienteCrudRepository;
    }

    @Override
    public Optional<ClienteEntity> findById(Integer id) {
        return this.clienteCrudRepository.findById(id);
    }

    @Override
    public List<ClienteEntity> findAll() {
        return (List<ClienteEntity>) this.clienteCrudRepository.findAll();
    }
    @Override
    public ClienteEntity save(ClienteEntity cliente) {
        return this.clienteCrudRepository.save(cliente);
    }
}
