package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.InsumoRecetaDTO;
import com.gaspar.facturador.application.response.RecetaDTO;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.RecetaInsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.RecetasCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.RecetaInsumoEntity;
import com.gaspar.facturador.persistence.entity.RecetasEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetasCrudRepository recetasCrudRepository;
    private final RecetaInsumoCrudRepository recetaInsumoCrudRepository;
    private final InsumoCrudRepository insumoCrudRepository;
    private final ItemCrudRepository itemCrudRepository;

    @Transactional
    public RecetasEntity crearReceta(RecetaDTO recetaDTO) {
        // Validar que el producto existe
        ItemEntity producto = itemCrudRepository.findById(recetaDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + recetaDTO.getProductoId()));

        // Crear la entidad de receta
        RecetasEntity receta = new RecetasEntity();
        receta.setNombre(recetaDTO.getNombre());
        receta.setDescripcion(recetaDTO.getDescripcion());
        receta.setCantidadUnidades(recetaDTO.getCantidadUnidades());
        receta.setPesoUnitario(recetaDTO.getPesoUnitario());
        receta.setProducto(producto);

        // Guardar la receta principal
        RecetasEntity recetaGuardada = recetasCrudRepository.save(receta);

        // Procesar cada insumo de la receta
        for (InsumoRecetaDTO insumoDTO : recetaDTO.getInsumos()) {
            // Validar que el insumo existe
            InsumoEntity insumo = insumoCrudRepository.findById(insumoDTO.getInsumoId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + insumoDTO.getInsumoId()));

            // Crear la relación receta-insumo
            RecetaInsumoEntity recetaInsumo = new RecetaInsumoEntity();
            recetaInsumo.setReceta(recetaGuardada);
            recetaInsumo.setInsumo(insumo);
            recetaInsumo.setCantidad(insumoDTO.getCantidad());
            recetaInsumo.setUnidadMedida(insumoDTO.getUnidadMedida());

            // Calcular el costo basado en el precio del insumo
            //recetaInsumo.setCosto(insumo.getPrecio().multiply(insumoDTO.getCantidad()));

            // Guardar la relación
            recetaInsumoCrudRepository.save(recetaInsumo);
        }

        return recetaGuardada;
    }

    // Método para convertir entidad a DTO (útil para las respuestas)
    public RecetaDTO convertirADTO(RecetasEntity receta) {
        RecetaDTO dto = new RecetaDTO();
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setDescripcion(receta.getDescripcion());
        dto.setCantidadUnidades(receta.getCantidadUnidades());
        dto.setPesoUnitario(receta.getPesoUnitario());
        dto.setProductoId(receta.getProducto().getId());

        // Convertir los insumos de la receta
        List<InsumoRecetaDTO> insumosDTO = receta.getRecetaInsumos().stream()
                .map(ri -> {
                    InsumoRecetaDTO insumoDTO = new InsumoRecetaDTO();
                    insumoDTO.setInsumoId(ri.getInsumo().getId());
                    insumoDTO.setCantidad(ri.getCantidad());
                    insumoDTO.setUnidadMedida(ri.getUnidadMedida());
                    insumoDTO.setNombreInsumo(ri.getInsumo().getNombre());
                    //insumoDTO.setMarcaInsumo(ri.getInsumo().getMarca());
                    //insumoDTO.setPrecioUnitario(ri.getInsumo().getPrecio());
                    return insumoDTO;
                })
                .collect(Collectors.toList());

        dto.setInsumos(insumosDTO);
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
    public Page<RecetaDTO> listarRecetasPaginadas(String nombre, Integer productoId, Pageable pageable) {
        Page<RecetasEntity> recetasPage = recetasCrudRepository.findByFilters(
                nombre,
                productoId,
                pageable
        );

        return recetasPage.map(this::convertirADTO);
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

        // Manejar insumos
        actualizarInsumosReceta(recetaExistente, recetaDTO.getInsumos());

        // Guardar cambios
        recetasCrudRepository.save(recetaExistente);
    }

    private void actualizarInsumosReceta(RecetasEntity receta, List<InsumoRecetaDTO> insumosDTO) {
        // Obtener IDs de insumos que vienen en el DTO
        Set<Long> insumosActualizadosIds = insumosDTO.stream()
                .map(InsumoRecetaDTO::getInsumoId)
                .collect(Collectors.toSet());

        // Eliminar insumos que no están en el DTO
        List<RecetaInsumoEntity> insumosAEliminar = receta.getRecetaInsumos().stream()
                .filter(ri -> !insumosActualizadosIds.contains(ri.getInsumo().getId()))
                .collect(Collectors.toList());

        insumosAEliminar.forEach(ri -> {
            receta.getRecetaInsumos().remove(ri);
            recetaInsumoCrudRepository.delete(ri);
        });

        // Actualizar o agregar insumos
        for (InsumoRecetaDTO insumoDTO : insumosDTO) {
            // Buscar si el insumo ya existe en la receta
            Optional<RecetaInsumoEntity> insumoExistenteOpt = receta.getRecetaInsumos().stream()
                    .filter(ri -> ri.getInsumo().getId().equals(insumoDTO.getInsumoId()))
                    .findFirst();

            if (insumoExistenteOpt.isPresent()) {
                // Actualizar insumo existente
                RecetaInsumoEntity insumoExistente = insumoExistenteOpt.get();
                insumoExistente.setCantidad(insumoDTO.getCantidad());
                insumoExistente.setUnidadMedida(insumoDTO.getUnidadMedida());
                // Recalcular costo si es necesario
                // insumoExistente.setCosto(insumoExistente.getInsumo().getPrecioActual().multiply(insumoDTO.getCantidad()));
            } else {
                // Agregar nuevo insumo
                InsumoEntity insumo = insumoCrudRepository.findById(insumoDTO.getInsumoId())
                        .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + insumoDTO.getInsumoId()));

                RecetaInsumoEntity nuevoInsumo = new RecetaInsumoEntity();
                nuevoInsumo.setReceta(receta);
                nuevoInsumo.setInsumo(insumo);
                nuevoInsumo.setCantidad(insumoDTO.getCantidad());
                nuevoInsumo.setUnidadMedida(insumoDTO.getUnidadMedida());
                // Calcular costo inicial
                // nuevoInsumo.setCosto(insumo.getPrecioActual().multiply(insumoDTO.getCantidad()));

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
