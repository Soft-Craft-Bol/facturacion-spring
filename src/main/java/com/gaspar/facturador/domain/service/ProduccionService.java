package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ProduccionDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionFilterDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionPageDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionResponseDTO;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProduccionService {

    private final RecetasCrudRepository recetasRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final SucursalItemCrudRepository sucursalItemRepository;
    private final MovimientoProduccionRepository movimientoProduccionRepository;
    private final SucursalCrudRepository sucursalRepository;
    private final ProduccionCrudRepository produccionRepository;
    private final RecetaInsumoCrudRepository recetaInsumoCrudRepository;
    private final DetalleProduccionInsumoCrudRepository detalleProduccionInsumoRepository;

    @Transactional
    public void registrarProduccion(ProduccionDTO produccionDTO) {
        RecetasEntity receta = recetasRepository.findById(produccionDTO.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        Map<Long, InsumoEntity> insumosEspecificos = obtenerInsumosEspecificos(receta, produccionDTO.getSucursalId());
        validarStockInsumos(insumosEspecificos, produccionDTO);
        actualizarStockInsumos(insumosEspecificos, produccionDTO);
        actualizarStockProducto(receta, produccionDTO);

        MovimientoProduccionEntity movimiento = registrarMovimientoProduccion(receta, produccionDTO);
        registrarDetalleProduccion(movimiento, insumosEspecificos, produccionDTO);
    }

        public ProduccionResponseDTO obtenerProduccionPorId(Long id) {
        MovimientoProduccionEntity movimiento = movimientoProduccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));
        return toResponseDTO(movimiento);
    }

    public ProduccionPageDTO listarProduccionesPaginadas(Pageable pageable, ProduccionFilterDTO filtros) {
        LocalDateTime fechaInicio = filtros.getFechaInicio() != null ?
                filtros.getFechaInicio().atStartOfDay() : null;
        LocalDateTime fechaFin = filtros.getFechaFin() != null ?
                filtros.getFechaFin().atTime(23, 59, 59) : null;

        Page<MovimientoProduccionEntity> page = produccionRepository.findWithFilters(
                fechaInicio,
                fechaFin,
                filtros.getRecetaId(),
                filtros.getProductoId(),
                Long.valueOf(filtros.getSucursalId()),
                pageable
        );

        ProduccionPageDTO response = new ProduccionPageDTO();
        response.setProducciones(page.map(this::toResponseDTO));
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setCurrentPage(page.getNumber());

        return response;
    }

    @Transactional
    public ProduccionResponseDTO actualizarProduccion(Long id, ProduccionDTO produccionDTO) {
        MovimientoProduccionEntity movimiento = movimientoProduccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));

        // Revertir producción anterior
        revertirProduccion(movimiento);

        // Registrar nueva producción
        RecetasEntity receta = recetasRepository.findById(produccionDTO.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        Map<Long, InsumoEntity> insumosEspecificos = obtenerInsumosEspecificos(receta, produccionDTO.getSucursalId());
        validarStockInsumos(insumosEspecificos, produccionDTO);
        actualizarStockInsumos(insumosEspecificos, produccionDTO);
        actualizarStockProducto(receta, produccionDTO);

        // Actualizar movimiento
        movimiento.setReceta(receta);
        movimiento.setSucursalId(Long.valueOf(produccionDTO.getSucursalId()));
        movimiento.setCantidad(produccionDTO.getCantidad());
        movimiento.setUnidadesProduccidas(
                BigDecimal.valueOf(receta.getCantidadUnidades())
                        .multiply(produccionDTO.getCantidad())
        );
        movimiento.setObservaciones(produccionDTO.getObservaciones());
        movimiento.setFechaProduccion(LocalDateTime.now());

        // Eliminar detalles antiguos y crear nuevos
        detalleProduccionInsumoRepository.deleteByMovimientoProduccionId(movimiento.getId());
        registrarDetalleProduccion(movimiento, insumosEspecificos, produccionDTO);

        return toResponseDTO(movimientoProduccionRepository.save(movimiento));
    }

    @Transactional
    public void eliminarProduccion(Long id) {
        MovimientoProduccionEntity movimiento = movimientoProduccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));

        revertirProduccion(movimiento);
        detalleProduccionInsumoRepository.deleteByMovimientoProduccionId(id);
        movimientoProduccionRepository.delete(movimiento);
    }
    //Metodos auxiliares

    private ProduccionResponseDTO toResponseDTO(MovimientoProduccionEntity movimiento) {
        ProduccionResponseDTO dto = new ProduccionResponseDTO();
        dto.setId(movimiento.getId());
        dto.setRecetaNombre(movimiento.getReceta().getNombre());
        dto.setProductoNombre(movimiento.getReceta().getProducto().getDescripcion());
        dto.setSucursalNombre(
                sucursalRepository.findById(Math.toIntExact(movimiento.getSucursalId()))
                        .orElse(new SucursalEntity()).getNombre()
        );
        dto.setCantidadProducida(movimiento.getUnidadesProduccidas().intValue());
        dto.setFecha(movimiento.getFechaProduccion());
        dto.setObservaciones(movimiento.getObservaciones());
        return dto;
    }

    private Map<Long, InsumoEntity> obtenerInsumosEspecificos(RecetasEntity receta, Integer sucursalId) {
        Map<Long, InsumoEntity> insumosEspecificos = new HashMap<>();

        for (RecetaInsumoEntity recetaInsumo : receta.getRecetaInsumos()) {
            InsumoGenericoEntity insumoGenerico = recetaInsumo.getInsumoGenerico();

            // Obtener el insumo específico con mayor prioridad disponible en la sucursal
            InsumoEntity insumo = insumoGenerico.getInsumosAsociados().stream()
                    .filter(d -> sucursalInsumoRepository.existsBySucursalIdAndInsumoId(
                            Long.valueOf(sucursalId), d.getInsumo().getId()))
                    .sorted(Comparator.comparingInt(InsumoGenericoDetalleEntity::getPrioridad))
                    .map(InsumoGenericoDetalleEntity::getInsumo)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "No hay insumos disponibles para: " + insumoGenerico.getNombre()));

            insumosEspecificos.put(insumoGenerico.getId(), insumo);
        }

        return insumosEspecificos;
    }

    private void revertirProduccion(MovimientoProduccionEntity movimiento) {
        // Revertir insumos usando el detalle de producción
        List<DetalleProduccionInsumoEntity> detalles = detalleProduccionInsumoRepository.findByMovimientoProduccionId(movimiento.getId());

        for (DetalleProduccionInsumoEntity detalle : detalles) {
            SucursalInsumoEntity stockInsumo = detalle.getSucursalInsumo();
            stockInsumo.setCantidad(stockInsumo.getCantidad().add(detalle.getCantidadUsada()));
            sucursalInsumoRepository.save(stockInsumo);
        }

        // Revertir producto
        ItemEntity producto = movimiento.getReceta().getProducto();
        SucursalItemEntity stockProducto = sucursalItemRepository
                .findBySucursal_IdAndItem_Id(Math.toIntExact(movimiento.getSucursalId()), producto.getId())
                .orElseThrow(() -> new RuntimeException("Stock de producto no encontrado"));

        int unidadesRevertidas = movimiento.getUnidadesProduccidas().intValue();
        stockProducto.setCantidad(stockProducto.getCantidad() - unidadesRevertidas);
        sucursalItemRepository.save(stockProducto);
    }


    private void validarStockInsumos(Map<Long, InsumoEntity> insumosEspecificos, ProduccionDTO produccionDTO) {
        for (Map.Entry<Long, InsumoEntity> entry : insumosEspecificos.entrySet()) {
            Long insumoGenericoId = entry.getKey();
            InsumoEntity insumo = entry.getValue();

            SucursalInsumoEntity stock = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(Long.valueOf(produccionDTO.getSucursalId()), insumo.getId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado para insumo: " + insumo.getNombre()));

            BigDecimal cantidadNecesaria = obtenerCantidadNecesaria(produccionDTO.getRecetaId(), insumoGenericoId)
                    .multiply(produccionDTO.getCantidad());

            if (stock.getCantidad().compareTo(cantidadNecesaria) < 0) {
                throw new RuntimeException("Stock insuficiente para: " + insumo.getNombre() +
                        ". Disponible: " + stock.getCantidad() + ", Necesario: " + cantidadNecesaria);
            }
        }
    }

    private BigDecimal obtenerCantidadNecesaria(Integer recetaId, Long insumoGenericoId) {
        return recetaInsumoCrudRepository.findByRecetaIdAndInsumoGenericoId(recetaId, insumoGenericoId)
                .map(RecetaInsumoEntity::getCantidad)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado en receta"));
    }

    private void registrarDetalleProduccion(
            MovimientoProduccionEntity movimiento,
            Map<Long, InsumoEntity> insumosEspecificos,
            ProduccionDTO produccionDTO) {

        for (Map.Entry<Long, InsumoEntity> entry : insumosEspecificos.entrySet()) {
            Long insumoGenericoId = entry.getKey();
            InsumoEntity insumo = entry.getValue();

            DetalleProduccionInsumoEntity detalle = new DetalleProduccionInsumoEntity();
            detalle.setMovimientoProduccion(movimiento); // Cambiado de setProduccion a setMovimientoProduccion
            detalle.setSucursalInsumo(sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(Long.valueOf(produccionDTO.getSucursalId()), insumo.getId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado")));

            detalle.setCantidadUsada(
                    obtenerCantidadNecesaria(produccionDTO.getRecetaId(), insumoGenericoId)
                            .multiply(produccionDTO.getCantidad()));

            detalleProduccionInsumoRepository.save(detalle);
        }
    }


    private void actualizarStockInsumos(Map<Long, InsumoEntity> insumosEspecificos, ProduccionDTO produccionDTO) {
        for (Map.Entry<Long, InsumoEntity> entry : insumosEspecificos.entrySet()) {
            Long insumoGenericoId = entry.getKey();
            InsumoEntity insumo = entry.getValue();

            SucursalInsumoEntity stockInsumo = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(Long.valueOf(produccionDTO.getSucursalId()), insumo.getId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado para insumo: " + insumo.getNombre()));

            BigDecimal cantidadUtilizada = obtenerCantidadNecesaria(produccionDTO.getRecetaId(), insumoGenericoId)
                    .multiply(produccionDTO.getCantidad());

            stockInsumo.setCantidad(stockInsumo.getCantidad().subtract(cantidadUtilizada));
            sucursalInsumoRepository.save(stockInsumo);
        }
    }

    private void actualizarStockProducto(RecetasEntity receta, ProduccionDTO produccionDTO) {
        ItemEntity producto = receta.getProducto();

        BigDecimal unidadesProducidas = BigDecimal.valueOf(receta.getCantidadUnidades())
                .multiply(produccionDTO.getCantidad());

        SucursalItemEntity stockProducto = sucursalItemRepository
                .findBySucursal_IdAndItem_Id(produccionDTO.getSucursalId(), producto.getId())
                .orElseGet(() -> {
                    SucursalItemEntity nuevoStock = new SucursalItemEntity();
                    nuevoStock.setSucursalId(produccionDTO.getSucursalId());
                    nuevoStock.setItem(producto);
                    nuevoStock.setCantidad(0);
                    return nuevoStock;
                });

        int nuevasUnidades = unidadesProducidas.intValue();
        stockProducto.setCantidad(stockProducto.getCantidad() + nuevasUnidades);

        sucursalItemRepository.save(stockProducto);
    }

    private MovimientoProduccionEntity registrarMovimientoProduccion(RecetasEntity receta, ProduccionDTO produccionDTO) {
        MovimientoProduccionEntity movimiento = new MovimientoProduccionEntity();
        movimiento.setReceta(receta);
        movimiento.setSucursalId(Long.valueOf(produccionDTO.getSucursalId()));
        movimiento.setCantidad(produccionDTO.getCantidad());
        movimiento.setUnidadesProduccidas(
                BigDecimal.valueOf(receta.getCantidadUnidades())
                        .multiply(produccionDTO.getCantidad())
        );
        movimiento.setFechaProduccion(LocalDateTime.now());
        movimiento.setObservaciones(produccionDTO.getObservaciones());

        return movimientoProduccionRepository.save(movimiento); // Asegúrate de devolver el movimiento guardado
    }
}
