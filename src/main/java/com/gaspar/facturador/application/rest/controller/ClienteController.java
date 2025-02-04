package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.IClienteRepository;
import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final IClienteRepository clienteRepository;

    public ClienteController(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> getAllClientes() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ClienteEntity> createCliente(@RequestBody ClienteEntity cliente) {
        try {
            Long numeroDocumento = Long.parseLong(cliente.getNumeroDocumento().toString());
            cliente.setNumeroDocumento(numeroDocumento);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClienteEntity savedCliente = clienteRepository.save(cliente);
        return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
    }
}