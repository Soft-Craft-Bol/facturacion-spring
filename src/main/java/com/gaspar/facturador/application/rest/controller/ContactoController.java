package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.crud.ContactoCrudRepository;
import com.gaspar.facturador.persistence.entity.ContactoEntity;
import com.gaspar.facturador.persistence.entity.enums.EstadoContacto;
import com.gaspar.facturador.persistence.entity.enums.TipoAsunto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contactos")
public class ContactoController {

    private final ContactoCrudRepository contactoCrudRepository;

    public ContactoController(ContactoCrudRepository contactoCrudRepository) {
        this.contactoCrudRepository = contactoCrudRepository;
    }

    // Registrar contacto
    @PostMapping
    public ResponseEntity<ContactoEntity> registrarContacto(@RequestBody ContactoEntity request) {
        request.setAtendido(EstadoContacto.PENDIENTE); // por defecto
        return ResponseEntity.ok(contactoCrudRepository.save(request));
    }

    // Listar contactos con filtros opcionales y paginación
    @GetMapping
    public ResponseEntity<Page<ContactoEntity>> listarContactos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) EstadoContacto atendido,
            @RequestParam(required = false) TipoAsunto asunto
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactoEntity> contactos;

        if (atendido != null && asunto != null) {
            contactos = contactoCrudRepository.findByAtendidoAndAsunto(atendido, asunto, pageable);
        } else if (atendido != null) {
            contactos = contactoCrudRepository.findByAtendido(atendido, pageable);
        } else if (asunto != null) {
            contactos = contactoCrudRepository.findByAsunto(asunto, pageable);
        } else {
            contactos = contactoCrudRepository.findAll(pageable);
        }

        return ResponseEntity.ok(contactos);
    }

    // Obtener contacto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactoEntity> obtenerPorId(@PathVariable Long id) {
        Optional<ContactoEntity> contacto = contactoCrudRepository.findById(id);
        return contacto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Actualizar estado del contacto
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ContactoEntity> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoContacto estado
    ) {
        Optional<ContactoEntity> optionalContacto = contactoCrudRepository.findById(id);
        if (optionalContacto.isEmpty()) return ResponseEntity.notFound().build();

        ContactoEntity contacto = optionalContacto.get();
        contacto.setAtendido(estado);
        contactoCrudRepository.save(contacto);

        return ResponseEntity.ok(contacto);
    }

    // Eliminar contacto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContacto(@PathVariable Long id) {
        if (!contactoCrudRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contactoCrudRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
