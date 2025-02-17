package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.HorarioRepository;
import com.gaspar.facturador.persistence.entity.HorarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping
    public List<HorarioEntity> getAllHorario(){
        return horarioRepository.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<HorarioEntity> getHorarioById(@PathVariable Long id) {
        Optional<HorarioEntity> horario = horarioRepository.getById(id);
        return horario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public HorarioEntity createHorario(@RequestBody HorarioEntity horario) {
        return horarioRepository.save(horario);
    }
    @PutMapping("/{id}")
    public ResponseEntity<HorarioEntity> updateHorario(@PathVariable Long id, @RequestBody HorarioEntity horarioDetails) {
        Optional<HorarioEntity> optionalHorario = horarioRepository.getById(id);
        if (optionalHorario.isPresent()) {
            HorarioEntity updatedHorario = horarioRepository.update(id, horarioDetails);
            return ResponseEntity.ok(updatedHorario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        if (horarioRepository.getById(id).isPresent()) {
            horarioRepository.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
