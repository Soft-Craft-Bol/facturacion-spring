package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.domain.repository.IClienteRepository;
import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClienteService {

    private final IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<ClienteEntity> buscarPorDocumento(Long numeroDocumento) {
        if(numeroDocumento == null || numeroDocumento <= 0) {
            return Optional.empty();
        }
        return clienteRepository.findByNumeroDocumento(numeroDocumento);
    }
}