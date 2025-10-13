package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.domain.repository.IClienteRepository;
import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteEntity> sugerirPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return List.of();
        return clienteRepository.findByNombreRazonSocialLike(nombre);
    }
    public List<ClienteEntity> sugerirPorDocumento(String prefijoDocumento) {
        if (prefijoDocumento == null || prefijoDocumento.trim().isEmpty()) return List.of();
        return clienteRepository.findByNumeroDocumentoLike(prefijoDocumento);
    }
}