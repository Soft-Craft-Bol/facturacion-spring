package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ProduccionDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionFilterDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionPageDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionResponseDTO;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.specification.ProduccionSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduccionService {

    private final RecetasCrudRepository recetasRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final InsumoGenericoCrudRepository insumoGenericoRepository;
    private final SucursalItemCrudRepository sucursalItemRepository;
    private final SucursalCrudRepository sucursalRepository;
    private final ProduccionCrudRepository produccionRepository;
    private final RecetaInsumoCrudRepository recetaInsumoCrudRepository;
    private final DetalleProduccionInsumoCrudRepository detalleProduccionInsumoRepository;

    @Transactional
    public void registrarProduccion(ProduccionDTO produccionDTO) {
        // 1. Validar y obtener receta
        RecetasEntity receta = recetasRepository.findById(produccionDTO.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // 2. Crear entidad de producción
        ProduccionEntity produccion = crearProduccionEntity(receta, produccionDTO);

        // 3. Procesar insumos y actualizar stocks
        List<DetalleProduccionInsumoEntity> detalles = procesarInsumos(produccion, receta, produccionDTO);
        produccion.setInsumosConsumidos(detalles);

        // 4. Actualizar stock del producto final
        actualizarStockProducto(receta, produccionDTO);

        // 5. Guardar
        produccionRepository.save(produccion);
    }

    private ProduccionEntity crearProduccionEntity(RecetasEntity receta, ProduccionDTO dto) {
        ProduccionEntity produccion = new ProduccionEntity();
        produccion.setReceta(receta);
        produccion.setProducto(receta.getProducto());
        produccion.setSucursal(sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada")));
        double unidadesProducidas = receta.getCantidadUnidades() * dto.getCantidad().doubleValue();
        produccion.setCantidadProducida((int) Math.round(unidadesProducidas));
        produccion.setFecha(LocalDateTime.now());
        produccion.setObservaciones(dto.getObservaciones());
        return produccion;
    }

    private List<DetalleProduccionInsumoEntity> procesarInsumos(
            ProduccionEntity produccion,
            RecetasEntity receta,
            ProduccionDTO produccionDTO) {

        List<DetalleProduccionInsumoEntity> detalles = new ArrayList<>();

        for (RecetaInsumoEntity recetaInsumo : receta.getRecetaInsumos()) {
            BigDecimal cantidadTotalNecesaria = recetaInsumo.getCantidad().multiply(produccionDTO.getCantidad());

            List<ProduccionDTO.InsumoPorcentajeDTO> porcentajes = determinarPorcentajes(recetaInsumo, produccionDTO);

            for (ProduccionDTO.InsumoPorcentajeDTO porcentajeDTO : porcentajes) {
                BigDecimal cantidadUsada = calcularCantidadUsada(cantidadTotalNecesaria, porcentajeDTO.getPorcentaje());

                DetalleProduccionInsumoEntity detalle = crearDetalleInsumo(
                        produccion,
                        recetaInsumo.getInsumoGenerico(),
                        porcentajeDTO.getInsumoId(),
                        produccionDTO.getSucursalId(),
                        cantidadUsada,
                        porcentajeDTO.getPorcentaje()
                );

                validarYActualizarStock(detalle.getSucursalInsumo(), cantidadUsada);
                detalles.add(detalle);
            }
        }
        return detalles;
    }

    private List<ProduccionDTO.InsumoPorcentajeDTO> determinarPorcentajes(
            RecetaInsumoEntity recetaInsumo,
            ProduccionDTO produccionDTO) {

        if (produccionDTO.getPorcentajesInsumos() != null && !produccionDTO.getPorcentajesInsumos().isEmpty()) {
            // Usar porcentajes personalizados del DTO
            return filtrarPorcentajesParaInsumoGenerico(
                    produccionDTO.getPorcentajesInsumos(),
                    recetaInsumo.getInsumoGenerico().getId()
            );
        } else {
            // Usar porcentajes por defecto (insumo con mayor prioridad al 100%)
            return getPorcentajeDefault(recetaInsumo, produccionDTO.getSucursalId());
        }
    }

    private List<ProduccionDTO.InsumoPorcentajeDTO> filtrarPorcentajesParaInsumoGenerico(
            List<ProduccionDTO.InsumoPorcentajeDTO> porcentajes,
            Long insumoGenericoId) {

        return porcentajes.stream()
                .filter(p -> p.getInsumoGenericoId().equals(insumoGenericoId))
                .collect(Collectors.toList());
    }

    private List<ProduccionDTO.InsumoPorcentajeDTO> getPorcentajeDefault(
            RecetaInsumoEntity recetaInsumo,
            Integer sucursalId) {

        // Obtener el insumo con mayor prioridad disponible
        InsumoEntity insumo = insumoGenericoRepository.findById(recetaInsumo.getInsumoGenerico().getId())
                .orElseThrow()
                .getInsumosAsociados().stream()
                .filter(d -> sucursalInsumoRepository.existsBySucursalIdAndInsumoId(
                        Long.valueOf(sucursalId),
                        d.getInsumo().getId()))
                .min(Comparator.comparingInt(InsumoGenericoDetalleEntity::getPrioridad))
                .map(InsumoGenericoDetalleEntity::getInsumo)
                .orElseThrow(() -> new RuntimeException("No hay insumos disponibles para: "
                        + recetaInsumo.getInsumoGenerico().getNombre()));

        // Crear DTO con 100% para este insumo
        ProduccionDTO.InsumoPorcentajeDTO porcentajeDTO = new ProduccionDTO.InsumoPorcentajeDTO();
        porcentajeDTO.setInsumoGenericoId(recetaInsumo.getInsumoGenerico().getId());
        porcentajeDTO.setInsumoId(insumo.getId());
        porcentajeDTO.setPorcentaje(BigDecimal.valueOf(100));

        return List.of(porcentajeDTO);
    }

    private DetalleProduccionInsumoEntity crearDetalleInsumo(
            ProduccionEntity produccion,
            InsumoGenericoEntity insumoGenerico,
            Long insumoId,
            Integer sucursalId,
            BigDecimal cantidadUsada,
            BigDecimal porcentaje) {

        DetalleProduccionInsumoEntity detalle = new DetalleProduccionInsumoEntity();
        detalle.setProduccion(produccion);
        detalle.setInsumoGenerico(insumoGenerico);
        detalle.setSucursalInsumo(
                sucursalInsumoRepository.findBySucursalIdAndInsumoId(
                        sucursalId,
                        insumoId
                ).orElseThrow(() -> new RuntimeException("Stock no encontrado para insumo ID: " + insumoId))
        );
        detalle.setCantidadUsada(cantidadUsada);
        detalle.setPorcentajeUsado(porcentaje);
        return detalle;
    }

    private BigDecimal calcularCantidadUsada(BigDecimal cantidadTotal, BigDecimal porcentaje) {
        return cantidadTotal.multiply(porcentaje)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private void validarYActualizarStock(SucursalInsumoEntity stockInsumo, BigDecimal cantidadUsada) {
        if (stockInsumo.getCantidad().compareTo(cantidadUsada) < 0) {
            throw new RuntimeException("Stock insuficiente para: "
                    + stockInsumo.getInsumo().getNombre()
                    + ". Disponible: " + stockInsumo.getCantidad()
                    + ", Necesario: " + cantidadUsada);
        }
        stockInsumo.setCantidad(stockInsumo.getCantidad().subtract(cantidadUsada));
    }

    private void actualizarStockProducto(RecetasEntity receta, ProduccionDTO produccionDTO) {
        double unidadesProducidas = receta.getCantidadUnidades() * produccionDTO.getCantidad().doubleValue();
        int unidadesEnteras = (int) Math.round(unidadesProducidas);
        ItemEntity producto = receta.getProducto();

        SucursalItemEntity stockProducto = sucursalItemRepository
                .findBySucursal_IdAndItem_Id(produccionDTO.getSucursalId(), producto.getId())
                .orElseGet(() -> {
                    SucursalItemEntity nuevoStock = new SucursalItemEntity();
                    nuevoStock.setSucursal(sucursalRepository.findById(produccionDTO.getSucursalId()).orElseThrow());
                    nuevoStock.setItem(producto);
                    nuevoStock.setCantidad(0);
                    return nuevoStock;
                });

        stockProducto.setCantidad(stockProducto.getCantidad() + unidadesEnteras);
        sucursalItemRepository.save(stockProducto);
    }

    public ProduccionResponseDTO obtenerProduccionPorId(Long id) {
        ProduccionEntity produccion = produccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));

        return convertirAProduccionResponseDTO(produccion);
    }

    public ProduccionPageDTO listarProduccionesPaginadas(Pageable pageable, ProduccionFilterDTO filtros) {
        LocalDateTime fechaInicio = filtros.getFechaInicio() != null ?
                filtros.getFechaInicio().atStartOfDay() : null;
        LocalDateTime fechaFin = filtros.getFechaFin() != null ?
                filtros.getFechaFin().atTime(23, 59, 59) : null;

        Long recetaId = filtros.getRecetaId() != null ?
                filtros.getRecetaId().longValue() : null;
        Long productoId = filtros.getProductoId() != null ?
                filtros.getProductoId().longValue() : null;
        Long sucursalId = filtros.getSucursalId() != null ?
                filtros.getSucursalId().longValue() : null;

        Specification<ProduccionEntity> spec = ProduccionSpecifications.withFilters(
                fechaInicio, fechaFin, recetaId, productoId, sucursalId);

        Page<ProduccionEntity> page = produccionRepository.findAll(spec, pageable);

        ProduccionPageDTO response = new ProduccionPageDTO();
        response.setProducciones(page.map(this::convertirAProduccionResponseDTO));
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setCurrentPage(page.getNumber());

        return response;
    }

    private ProduccionResponseDTO convertirAProduccionResponseDTO(ProduccionEntity produccion) {
        ProduccionResponseDTO dto = new ProduccionResponseDTO();
        dto.setId(produccion.getId());
        dto.setRecetaNombre(produccion.getReceta().getNombre());
        dto.setProductoNombre(produccion.getProducto().getDescripcion());
        dto.setSucursalNombre(produccion.getSucursal().getNombre());
        dto.setCantidadProducida(produccion.getCantidadProducida());
        dto.setFecha(produccion.getFecha());
        dto.setObservaciones(produccion.getObservaciones());

        // Opcional: agregar detalles de insumos usados
         dto.setInsumosUsados(convertirInsumosUsados(produccion.getInsumosConsumidos()));

        return dto;
    }

    // Opcional: método para convertir detalles de insumos

    private List<ProduccionResponseDTO.DetalleInsumoResponseDTO> convertirInsumosUsados(List<DetalleProduccionInsumoEntity> detalles) {
        return detalles.stream().map(detalle -> {
            ProduccionResponseDTO.DetalleInsumoResponseDTO dto = new ProduccionResponseDTO.DetalleInsumoResponseDTO();
            dto.setInsumoGenericoNombre(detalle.getInsumoGenerico().getNombre());
            dto.setInsumoNombre(detalle.getSucursalInsumo().getInsumo().getNombre());
            dto.setCantidadUsada(detalle.getCantidadUsada());
            dto.setUnidadMedida(detalle.getSucursalInsumo().getInsumo().getUnidades());
            dto.setPorcentajeUsado(detalle.getPorcentajeUsado());
            return dto;
        }).collect(Collectors.toList());
    }


}
