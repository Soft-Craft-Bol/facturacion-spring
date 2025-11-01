package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.InsumoGenericoDTO;
import com.gaspar.facturador.application.rest.dto.InsumoGenericoDetalleDTO;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.InsumoGenericoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.InsumoGenericoDetalleEntity;
import com.gaspar.facturador.persistence.entity.InsumoGenericoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumoGenericoService {

    private final InsumoGenericoCrudRepository insumoGenericoRepository;
    private final InsumoCrudRepository insumoRepository;

    @Transactional
    public InsumoGenericoDTO crearInsumoGenerico(InsumoGenericoDTO dto) {
        InsumoGenericoEntity entity = new InsumoGenericoEntity();
        entity.setNombre(dto.getNombre());
        entity.setUnidadMedida(dto.getUnidadMedida());
        entity.setDescripcion(dto.getDescripcion());

        InsumoGenericoEntity saved = insumoGenericoRepository.save(entity);
        return convertirADTO(saved);
    }

    @Transactional
    public InsumoGenericoDTO actualizarInsumoGenerico(Long id, InsumoGenericoDTO dto) {
        InsumoGenericoEntity entity = insumoGenericoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        entity.setNombre(dto.getNombre());
        entity.setUnidadMedida(dto.getUnidadMedida());
        entity.setDescripcion(dto.getDescripcion());

        return convertirADTO(insumoGenericoRepository.save(entity));
    }

    @Transactional
    public void eliminarInsumoGenerico(Long id) {
        InsumoGenericoEntity entity = insumoGenericoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        if (!entity.getInsumosAsociados().isEmpty()) {
            throw new RuntimeException("No se puede eliminar un insumo genérico con asignaciones");
        }

        insumoGenericoRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public Page<InsumoGenericoDTO> listarTodosPaginado(String nombre, Pageable pageable) {
        Page<InsumoGenericoEntity> page = (nombre != null && !nombre.isBlank())
                ? insumoGenericoRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                : insumoGenericoRepository.findAll(pageable);
        return page.map(this::convertirADTO);
    }

    @Transactional(readOnly = true)
    public InsumoGenericoDTO obtenerPorId(Long id) {
        return insumoGenericoRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));
    }

    @Transactional
    public InsumoGenericoDTO asignarInsumo(Long id, InsumoGenericoDetalleDTO detalleDTO) {
        InsumoGenericoEntity generico = insumoGenericoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        validarPrioridadUnica(generico, detalleDTO.getPrioridad());

        InsumoEntity insumo = insumoRepository.findById(detalleDTO.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        InsumoGenericoDetalleEntity detalle = new InsumoGenericoDetalleEntity();
        detalle.setInsumoGenerico(generico);
        detalle.setInsumo(insumo);
        detalle.setPrioridad(detalleDTO.getPrioridad());

        generico.getInsumosAsociados().add(detalle);
        return convertirADTO(insumoGenericoRepository.save(generico));
    }

    @Transactional
    public InsumoGenericoDTO asignarInsumos(Long id, List<InsumoGenericoDetalleDTO> detallesDTO) {
        InsumoGenericoEntity generico = insumoGenericoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        // Validar prioridades únicas primero
        detallesDTO.forEach(dto -> validarPrioridadUnica(generico, dto.getPrioridad()));

        for (InsumoGenericoDetalleDTO detalleDTO : detallesDTO) {
            InsumoEntity insumo = insumoRepository.findById(detalleDTO.getInsumoId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + detalleDTO.getInsumoId()));

            InsumoGenericoDetalleEntity detalle = new InsumoGenericoDetalleEntity();
            detalle.setInsumoGenerico(generico);
            detalle.setInsumo(insumo);
            detalle.setPrioridad(detalleDTO.getPrioridad());

            generico.getInsumosAsociados().add(detalle);
        }

        return convertirADTO(insumoGenericoRepository.save(generico));
    }

    @Transactional
    public InsumoGenericoDTO actualizarAsignaciones(Long genericoId, List<InsumoGenericoDetalleDTO> detallesDTO) {
        InsumoGenericoEntity generico = insumoGenericoRepository.findById(genericoId)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        Set<Integer> prioridades = new HashSet<>();
        for (InsumoGenericoDetalleDTO dto : detallesDTO) {
            if (!prioridades.add(dto.getPrioridad())) {
                throw new RuntimeException("Prioridad duplicada: " + dto.getPrioridad());
            }
        }

        Map<Long, InsumoGenericoDetalleEntity> existentesMap = generico.getInsumosAsociados().stream()
                .collect(Collectors.toMap(InsumoGenericoDetalleEntity::getId, d -> d));

        for (InsumoGenericoDetalleDTO dto : detallesDTO) {
            if (dto.getId() != null && existentesMap.containsKey(dto.getId())) {
                InsumoGenericoDetalleEntity detalle = existentesMap.get(dto.getId());
                detalle.setPrioridad(dto.getPrioridad());

                if (!detalle.getInsumo().getId().equals(dto.getInsumoId())) {
                    InsumoEntity nuevoInsumo = insumoRepository.findById(dto.getInsumoId())
                            .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + dto.getInsumoId()));
                    detalle.setInsumo(nuevoInsumo);
                }

                existentesMap.remove(dto.getId());
            } else {
                InsumoEntity insumo = insumoRepository.findById(dto.getInsumoId())
                        .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + dto.getInsumoId()));

                InsumoGenericoDetalleEntity nuevoDetalle = new InsumoGenericoDetalleEntity();
                nuevoDetalle.setInsumoGenerico(generico);
                nuevoDetalle.setInsumo(insumo);
                nuevoDetalle.setPrioridad(dto.getPrioridad());

                generico.getInsumosAsociados().add(nuevoDetalle);
            }
        }
        generico.getInsumosAsociados().removeAll(existentesMap.values());

        return convertirADTO(insumoGenericoRepository.save(generico));
    }

    @Transactional
    public void removerInsumo(Long genericoId, Long insumoId) {
        InsumoGenericoEntity generico = insumoGenericoRepository.findById(genericoId)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        generico.getInsumosAsociados().removeIf(d -> d.getInsumo().getId().equals(insumoId));
        insumoGenericoRepository.save(generico);
    }

    private void validarPrioridadUnica(InsumoGenericoEntity generico, Integer prioridad) {
        boolean prioridadExistente = generico.getInsumosAsociados().stream()
                .anyMatch(d -> d.getPrioridad().equals(prioridad));

        if (prioridadExistente) {
            throw new RuntimeException("La prioridad " + prioridad + " ya está asignada a otro insumo");
        }
    }

    private InsumoGenericoDTO convertirADTO(InsumoGenericoEntity entity) {
        InsumoGenericoDTO dto = new InsumoGenericoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setUnidadMedida(entity.getUnidadMedida());
        dto.setDescripcion(entity.getDescripcion());

        dto.setInsumosAsociados(entity.getInsumosAsociados().stream()
                .sorted(Comparator.comparing(InsumoGenericoDetalleEntity::getPrioridad))
                .map(this::convertirDetalleADTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private InsumoGenericoDetalleDTO convertirDetalleADTO(InsumoGenericoDetalleEntity entity) {
        InsumoGenericoDetalleDTO dto = new InsumoGenericoDetalleDTO();
        dto.setId(entity.getId());
        dto.setInsumoId(entity.getInsumo().getId());
        dto.setNombreInsumo(entity.getInsumo().getNombre());
        dto.setPrioridad(entity.getPrioridad());
        return dto;
    }
}