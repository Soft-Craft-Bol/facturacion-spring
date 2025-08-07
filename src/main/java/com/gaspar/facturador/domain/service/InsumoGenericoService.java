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

import java.util.List;
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

    @Transactional(readOnly = true)
    public Page<InsumoGenericoDTO> listarTodosPaginado(String nombre, Pageable pageable) {
        Page<InsumoGenericoEntity> page;

        if (nombre != null && !nombre.isBlank()) {
            page = insumoGenericoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            page = insumoGenericoRepository.findAll(pageable);
        }

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
    public void removerInsumo(Long genericoId, Long insumoId) {
        InsumoGenericoEntity generico = insumoGenericoRepository.findById(genericoId)
                .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

        generico.getInsumosAsociados().removeIf(d -> d.getInsumo().getId().equals(insumoId));
        insumoGenericoRepository.save(generico);
    }

    private InsumoGenericoDTO convertirADTO(InsumoGenericoEntity entity) {
        InsumoGenericoDTO dto = new InsumoGenericoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setUnidadMedida(entity.getUnidadMedida());
        dto.setDescripcion(entity.getDescripcion());

        dto.setInsumosAsociados(entity.getInsumosAsociados().stream()
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