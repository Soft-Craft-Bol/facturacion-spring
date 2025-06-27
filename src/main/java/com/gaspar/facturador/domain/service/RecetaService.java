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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        // Actualizar campos básicos
        recetaExistente.setNombre(recetaDTO.getNombre());
        recetaExistente.setDescripcion(recetaDTO.getDescripcion());
        recetaExistente.setCantidadUnidades(recetaDTO.getCantidadUnidades());
        recetaExistente.setPesoUnitario(recetaDTO.getPesoUnitario());

        // Actualizar producto si cambió
        if (!recetaExistente.getProducto().getId().equals(recetaDTO.getProductoId())) {
            ItemEntity nuevoProducto = itemCrudRepository.findById(recetaDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            recetaExistente.setProducto(nuevoProducto);
        }

        // Actualizar insumos
        actualizarInsumosReceta(recetaExistente, recetaDTO.getInsumos());

        recetasCrudRepository.save(recetaExistente);
    }

    private void actualizarInsumosReceta(RecetasEntity receta, List<InsumoRecetaDTO> insumosDTO) {
        // Eliminar insumos que ya no están en la receta
        receta.getRecetaInsumos().removeIf(ri ->
                insumosDTO.stream().noneMatch(i -> i.getInsumoId().equals(ri.getInsumo().getId()))
        );

        // Actualizar o agregar nuevos insumos
        for (InsumoRecetaDTO insumoDTO : insumosDTO) {
            RecetaInsumoEntity recetaInsumo = receta.getRecetaInsumos().stream()
                    .filter(ri -> ri.getInsumo().getId().equals(insumoDTO.getInsumoId()))
                    .findFirst()
                    .orElseGet(() -> {
                        RecetaInsumoEntity nuevo = new RecetaInsumoEntity();
                        nuevo.setReceta(receta);
                        nuevo.setInsumo(insumoCrudRepository.findById(insumoDTO.getInsumoId())
                                .orElseThrow(() -> new RuntimeException("Insumo no encontrado")));
                        return nuevo;
                    });

            recetaInsumo.setCantidad(insumoDTO.getCantidad());
            recetaInsumo.setUnidadMedida(insumoDTO.getUnidadMedida());
            //recetaInsumo.setCosto(recetaInsumo.getInsumo().getPrecio().multiply(insumoDTO.getCantidad()));

            if (recetaInsumo.getId() == null) {
                receta.getRecetaInsumos().add(recetaInsumo);
            }
        }
    }

    @Transactional
    public void eliminarReceta(Integer id) {
        RecetasEntity receta = obtenerRecetaConInsumos(id);
        recetasCrudRepository.delete(receta);
    }
}
