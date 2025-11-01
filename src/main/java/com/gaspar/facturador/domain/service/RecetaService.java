package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.InsumoRecetaDTO;
import com.gaspar.facturador.application.response.RecetaDTO;
import com.gaspar.facturador.application.rest.dto.RecetaDetalladaDTO;
import com.gaspar.facturador.application.rest.dto.RecetaInsumoGenericoDTO;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetasCrudRepository recetasCrudRepository;
    private final InsumoGenericoCrudRepository insumoGenericoCrudRepository;
    private final RecetaInsumoCrudRepository recetaInsumoCrudRepository;
    private final InsumoCrudRepository insumoCrudRepository;
    private final ItemCrudRepository itemCrudRepository;

    @Transactional
    public RecetasEntity crearReceta(RecetaDTO recetaDTO) {
        ItemEntity producto = itemCrudRepository.findById(recetaDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        RecetasEntity receta = new RecetasEntity();
        receta.setNombre(recetaDTO.getNombre());
        receta.setDescripcion(recetaDTO.getDescripcion());
        receta.setCantidadUnidades(recetaDTO.getCantidadUnidades());
        receta.setPesoUnitario(recetaDTO.getPesoUnitario());
        receta.setProducto(producto);
        receta.setRendimiento(recetaDTO.getRendimiento());
        receta.setInstrucciones(recetaDTO.getInstrucciones());
        receta.setTiempoProduccionMinutos(recetaDTO.getTiempoProduccionMinutos());

        RecetasEntity recetaGuardada = recetasCrudRepository.save(receta);

        for (RecetaInsumoGenericoDTO insumoDTO : recetaDTO.getInsumosGenericos()) {
            InsumoGenericoEntity insumoGenerico = insumoGenericoCrudRepository.findById(insumoDTO.getInsumoGenericoId())
                    .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado"));

            RecetaInsumoEntity recetaInsumo = new RecetaInsumoEntity();
            recetaInsumo.setReceta(recetaGuardada);
            recetaInsumo.setInsumoGenerico(insumoGenerico);
            recetaInsumo.setCantidad(insumoDTO.getCantidad());
            recetaInsumo.setUnidadMedida(insumoDTO.getUnidadMedida());
            recetaInsumo.setCosto(insumoDTO.getCosto());

            recetaInsumoCrudRepository.save(recetaInsumo);
        }

        return recetaGuardada;
    }

    public RecetaDTO convertirADTO(RecetasEntity receta) {
        RecetaDTO dto = new RecetaDTO();
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setDescripcion(receta.getDescripcion());
        dto.setCantidadUnidades(receta.getCantidadUnidades());
        dto.setPesoUnitario(receta.getPesoUnitario());
        dto.setProductoId(receta.getProducto().getId());
        dto.setNombreProducto(receta.getProducto().getDescripcion());
        dto.setRendimiento(receta.getRendimiento());
        dto.setInstrucciones(receta.getInstrucciones());
        dto.setTiempoProduccionMinutos(receta.getTiempoProduccionMinutos());
        dto.setFechaCreacion(receta.getFechaCreacion());
        dto.setFechaActualizacion(receta.getFechaActualizacion());

        dto.setInsumosGenericos(receta.getRecetaInsumos().stream()
                .map(ri -> {
                    RecetaInsumoGenericoDTO insumoDTO = new RecetaInsumoGenericoDTO();
                    insumoDTO.setInsumoGenericoId(ri.getInsumoGenerico().getId());
                    insumoDTO.setCantidad(ri.getCantidad());
                    insumoDTO.setUnidadMedida(ri.getUnidadMedida());
                    insumoDTO.setCosto(ri.getCosto());
                    return insumoDTO;
                })
                .collect(Collectors.toList()));

        return dto;
    }

    @Transactional(readOnly = true)
    public List<RecetasEntity> obtenerTodasLasRecetas() {
        return recetasCrudRepository.findAllWithInsumos();
    }


    @Transactional(readOnly = true)
    public RecetasEntity obtenerRecetaConInsumos(Integer id) {
        return recetasCrudRepository.findByIdWithInsumos(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public RecetaDetalladaDTO obtenerRecetaDetallada(Integer id) {
        RecetasEntity receta = recetasCrudRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + id));

        if (receta.getRecetaInsumos() != null && !receta.getRecetaInsumos().isEmpty()) {
            List<InsumoGenericoEntity> insumosGenericos = receta.getRecetaInsumos().stream()
                    .map(RecetaInsumoEntity::getInsumoGenerico)
                    .collect(Collectors.toList());

            List<InsumoGenericoEntity> insumosConDetalles = recetasCrudRepository.findInsumosEspecificos(insumosGenericos);

            // Actualizar las referencias
            Map<Long, InsumoGenericoEntity> detallesMap = insumosConDetalles.stream()
                    .collect(Collectors.toMap(InsumoGenericoEntity::getId, Function.identity()));

            receta.getRecetaInsumos().forEach(ri -> {
                InsumoGenericoEntity detalleCompleto = detallesMap.get(ri.getInsumoGenerico().getId());
                if (detalleCompleto != null) {
                    ri.setInsumoGenerico(detalleCompleto);
                }
            });
        }

        return convertirADetalladoDTO(receta);
    }

    private RecetaDetalladaDTO convertirADetalladoDTO(RecetasEntity receta) {
        RecetaDetalladaDTO dto = new RecetaDetalladaDTO();
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setDescripcion(receta.getDescripcion());
        dto.setCantidadUnidades(receta.getCantidadUnidades());
        dto.setPesoUnitario(receta.getPesoUnitario());
        dto.setRendimiento(receta.getRendimiento());
        dto.setInstrucciones(receta.getInstrucciones());
        dto.setTiempoProduccionMinutos(receta.getTiempoProduccionMinutos());

        if(receta.getProducto() != null) {
            dto.setNombreProducto(receta.getProducto().getDescripcion());
            dto.setProductoId(receta.getProducto().getId());
        }

        dto.setInsumosGenericos(receta.getRecetaInsumos().stream()
                .map(this::convertirInsumoGenericoDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private RecetaDetalladaDTO.InsumoGenericoRecetaDTO convertirInsumoGenericoDTO(RecetaInsumoEntity ri) {
        RecetaDetalladaDTO.InsumoGenericoRecetaDTO dto = new RecetaDetalladaDTO.InsumoGenericoRecetaDTO();
        dto.setId(ri.getInsumoGenerico().getId());
        dto.setNombre(ri.getInsumoGenerico().getNombre());
        dto.setUnidadMedida(ri.getUnidadMedida());
        dto.setCantidad(ri.getCantidad());
        dto.setCosto(ri.getCosto());

        dto.setOpcionesEspecificas(ri.getInsumoGenerico().getInsumosAsociados().stream()
                .map(this::convertirInsumoEspecificoDTO)
                .sorted(Comparator.comparing(RecetaDetalladaDTO.InsumoEspecificoDTO::getPrioridad))
                .collect(Collectors.toList()));

        return dto;
    }

    private RecetaDetalladaDTO.InsumoEspecificoDTO convertirInsumoEspecificoDTO(InsumoGenericoDetalleEntity iad) {
        RecetaDetalladaDTO.InsumoEspecificoDTO dto = new RecetaDetalladaDTO.InsumoEspecificoDTO();
        dto.setId(iad.getInsumo().getId());
        dto.setNombre(iad.getInsumo().getNombre());
        dto.setPrioridad(iad.getPrioridad());
        dto.setUnidades(iad.getInsumo().getUnidades());
        dto.setPrecioActual(iad.getInsumo().getPrecioActual());
        return dto;
    }

    @Transactional(readOnly = true)
    public Page<RecetaDetalladaDTO> listarRecetasDetalladasPaginadas(String nombre, Integer productoId, Pageable pageable) {
        // Primero obtén las recetas básicas paginadas
        Page<RecetasEntity> recetasPage = recetasCrudRepository.findByFilters(
                nombre,
                productoId,
                pageable
        );

        // Obtén los IDs de las recetas
        List<Integer> recetaIds = recetasPage.getContent().stream()
                .map(RecetasEntity::getId)
                .collect(Collectors.toList());

        // Carga todas las recetas con sus insumos en una sola consulta
        List<RecetasEntity> recetasConInsumos = recetasCrudRepository.findByIdInWithInsumos(recetaIds);

        // Carga todos los insumos genéricos necesarios
        List<InsumoGenericoEntity> insumosGenericos = recetasConInsumos.stream()
                .flatMap(r -> r.getRecetaInsumos().stream())
                .map(RecetaInsumoEntity::getInsumoGenerico)
                .distinct()
                .collect(Collectors.toList());

        // Carga los detalles específicos de los insumos
        List<InsumoGenericoEntity> insumosConDetalles = recetasCrudRepository.findInsumosEspecificos(insumosGenericos);

        // Crea un mapa para actualizar las referencias
        Map<Long, InsumoGenericoEntity> detallesMap = insumosConDetalles.stream()
                .collect(Collectors.toMap(InsumoGenericoEntity::getId, Function.identity()));

        // Actualiza las referencias en las recetas
        recetasConInsumos.forEach(receta -> {
            receta.getRecetaInsumos().forEach(ri -> {
                InsumoGenericoEntity detalleCompleto = detallesMap.get(ri.getInsumoGenerico().getId());
                if (detalleCompleto != null) {
                    ri.setInsumoGenerico(detalleCompleto);
                }
            });
        });

        // Convierte a DTO
        return recetasPage.map(receta -> {
            RecetasEntity recetaCompleta = recetasConInsumos.stream()
                    .filter(r -> r.getId().equals(receta.getId()))
                    .findFirst()
                    .orElse(receta);
            return convertirADetalladoDTO(recetaCompleta);
        });
    }

    @Transactional(readOnly = true)
    public List<RecetasEntity> buscarRecetasPorProducto(Integer productoId) {
        return recetasCrudRepository.findByProductoId(productoId);
    }

    @Transactional(readOnly = true)
    public List<RecetasEntity> buscarRecetasPorNombre(String nombre) {
        return recetasCrudRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public void actualizarReceta(Integer id, RecetaDTO recetaDTO) {
        RecetasEntity recetaExistente = obtenerRecetaConInsumos(id);

        // Validar que el producto existe
        ItemEntity producto = itemCrudRepository.findById(recetaDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + recetaDTO.getProductoId()));

        // Actualizar campos básicos
        recetaExistente.setNombre(recetaDTO.getNombre());
        recetaExistente.setDescripcion(recetaDTO.getDescripcion());
        recetaExistente.setCantidadUnidades(recetaDTO.getCantidadUnidades());
        recetaExistente.setPesoUnitario(recetaDTO.getPesoUnitario());
        recetaExistente.setProducto(producto);
        recetaExistente.setRendimiento(recetaDTO.getRendimiento());
        recetaExistente.setInstrucciones(recetaDTO.getInstrucciones());
        recetaExistente.setTiempoProduccionMinutos(recetaDTO.getTiempoProduccionMinutos());

        // Manejar insumos genéricos
        actualizarInsumosReceta(recetaExistente, recetaDTO.getInsumosGenericos());

        // Guardar cambios
        recetasCrudRepository.save(recetaExistente);
    }

    private void actualizarInsumosReceta(RecetasEntity receta, List<RecetaInsumoGenericoDTO> insumosGenericosDTO) {
        // Obtener IDs de insumos genéricos que vienen en el DTO
        Set<Long> insumosGenericosActualizadosIds = insumosGenericosDTO.stream()
                .map(RecetaInsumoGenericoDTO::getInsumoGenericoId)
                .collect(Collectors.toSet());

        // Eliminar insumos que no están en el DTO
        List<RecetaInsumoEntity> insumosAEliminar = receta.getRecetaInsumos().stream()
                .filter(ri -> !insumosGenericosActualizadosIds.contains(ri.getInsumoGenerico().getId()))
                .collect(Collectors.toList());

        insumosAEliminar.forEach(ri -> {
            receta.getRecetaInsumos().remove(ri);
            recetaInsumoCrudRepository.delete(ri);
        });

        // Actualizar o agregar insumos genéricos
        for (RecetaInsumoGenericoDTO insumoDTO : insumosGenericosDTO) {
            // Buscar si el insumo genérico ya existe en la receta
            Optional<RecetaInsumoEntity> insumoExistenteOpt = receta.getRecetaInsumos().stream()
                    .filter(ri -> ri.getInsumoGenerico().getId().equals(insumoDTO.getInsumoGenericoId()))
                    .findFirst();

            if (insumoExistenteOpt.isPresent()) {
                // Actualizar insumo genérico existente
                RecetaInsumoEntity insumoExistente = insumoExistenteOpt.get();
                insumoExistente.setCantidad(insumoDTO.getCantidad());
                insumoExistente.setUnidadMedida(insumoDTO.getUnidadMedida());
                insumoExistente.setCosto(insumoDTO.getCosto());
            } else {
                // Agregar nuevo insumo genérico
                InsumoGenericoEntity insumoGenerico = insumoGenericoCrudRepository.findById(insumoDTO.getInsumoGenericoId())
                        .orElseThrow(() -> new RuntimeException("Insumo genérico no encontrado con ID: " + insumoDTO.getInsumoGenericoId()));

                RecetaInsumoEntity nuevoInsumo = new RecetaInsumoEntity();
                nuevoInsumo.setReceta(receta);
                nuevoInsumo.setInsumoGenerico(insumoGenerico);
                nuevoInsumo.setCantidad(insumoDTO.getCantidad());
                nuevoInsumo.setUnidadMedida(insumoDTO.getUnidadMedida());
                nuevoInsumo.setCosto(insumoDTO.getCosto());

                receta.getRecetaInsumos().add(nuevoInsumo);
            }
        }
    }

    @Transactional
    public void eliminarReceta(Integer id) {
        RecetasEntity receta = obtenerRecetaConInsumos(id);
        recetasCrudRepository.delete(receta);
    }
}
