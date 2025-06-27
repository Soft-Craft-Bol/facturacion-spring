package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.IClienteRepository;
import com.gaspar.facturador.domain.service.ClienteService;
import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final IClienteRepository clienteRepository;
    private final ClienteService clienteService;


    public ClienteController(IClienteRepository clienteRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<ClienteEntity> buscarPorDocumento(
            @RequestParam("documento") Long numeroDocumento) {
        if(numeroDocumento == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ClienteEntity> cliente = clienteService.buscarPorDocumento(numeroDocumento);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

            // Validar que el celular no sea nulo (opcional)
            if(cliente.getCelular() == null || cliente.getCelular().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClienteEntity savedCliente = clienteRepository.save(cliente);
        return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/limited")
    public ResponseEntity<List<ClienteEntity>> getLimitedClientes() {
        List<ClienteEntity> clientes = clienteRepository.findAll().stream().limit(5).collect(Collectors.toList());
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
}