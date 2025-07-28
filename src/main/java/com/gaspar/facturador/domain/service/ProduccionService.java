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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduccionService {

    private final RecetasCrudRepository recetasRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final SucursalItemCrudRepository sucursalItemRepository;
    private final MovimientoProduccionRepository movimientoProduccionRepository;
    private final SucursalCrudRepository sucursalRepository;
    private final ProduccionCrudRepository produccionRepository;

    @Transactional
    public void registrarProduccion(ProduccionDTO produccionDTO) {
        System.out.println("Iniciando registro de producción con DTO: " + produccionDTO);

        if (produccionDTO.getSucursalId() == null) {
            throw new RuntimeException("El ID de sucursal no puede ser nulo");
        }

        RecetasEntity receta = recetasRepository.findById(produccionDTO.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        System.out.println("Receta encontrada - ID: " + receta.getId() + ", Producto: " );

        validarStockInsumos(receta, produccionDTO);
        actualizarStockInsumos(receta, produccionDTO);
        actualizarStockProducto(receta, produccionDTO);
        registrarMovimientoProduccion(receta, produccionDTO);

        System.out.println("Producción registrada exitosamente");
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

        validarStockInsumos(receta, produccionDTO);
        actualizarStockInsumos(receta, produccionDTO);
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

        MovimientoProduccionEntity updated = movimientoProduccionRepository.save(movimiento);
        return toResponseDTO(updated);
    }

    @Transactional
    public void eliminarProduccion(Long id) {
        MovimientoProduccionEntity movimiento = movimientoProduccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));

        revertirProduccion(movimiento);
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

    private void revertirProduccion(MovimientoProduccionEntity movimiento) {
        // Revertir insumos
        for (RecetaInsumoEntity insumoReceta : movimiento.getReceta().getRecetaInsumos()) {
            SucursalInsumoEntity stockInsumo = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(Long.valueOf(movimiento.getSucursalId()), insumoReceta.getInsumo().getId())
                    .orElseThrow(() -> new RuntimeException("Stock de insumo no encontrado"));

            BigDecimal cantidadDevuelta = insumoReceta.getCantidad().multiply(movimiento.getCantidad());
            stockInsumo.setCantidad(stockInsumo.getCantidad().add(cantidadDevuelta));
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


    private void validarStockInsumos(RecetasEntity receta, ProduccionDTO produccionDTO) {
        System.out.println("Validando stock de insumos para receta ID: " + receta.getId());
        System.out.println("Sucursal ID: " + produccionDTO.getSucursalId());
        System.out.println("Cantidad a producir: " + produccionDTO.getCantidad());
        Long sucursalId = produccionDTO.getSucursalId() != null
                ? Long.valueOf(produccionDTO.getSucursalId())
                : null;

        if (sucursalId == null) {
            throw new RuntimeException("ID de sucursal no proporcionado");
        }
        for (RecetaInsumoEntity insumoReceta : receta.getRecetaInsumos()) {
            Long insumoId = insumoReceta.getInsumo().getId();
            String insumoNombre = insumoReceta.getInsumo().getNombre();

            System.out.println("Buscando stock para insumo - ID: " + insumoId + ", Nombre: " + insumoNombre);

            SucursalInsumoEntity stockInsumo = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(Long.valueOf(produccionDTO.getSucursalId()), insumoId)
                    .orElseThrow(() -> {
                        System.out.println("ERROR: No se encontró stock para insumo ID: " + insumoId + " en sucursal ID: " + produccionDTO.getSucursalId());
                        return new RuntimeException("Stock de insumo no encontrado: " + insumoNombre);
                    });

            System.out.println("Stock encontrado - Cantidad actual: " + stockInsumo.getCantidad());

            BigDecimal cantidadNecesaria = insumoReceta.getCantidad().multiply(produccionDTO.getCantidad());
            System.out.println("Cantidad necesaria: " + cantidadNecesaria);

            if (stockInsumo.getCantidad().compareTo(cantidadNecesaria) < 0) {
                System.out.println("ERROR: Stock insuficiente. Disponible: " + stockInsumo.getCantidad() + ", Necesario: " + cantidadNecesaria);
                throw new RuntimeException("Stock insuficiente de: " + insumoNombre);
            }

            System.out.println("Stock validado correctamente para insumo: " + insumoNombre);
        }
    }

    private void actualizarStockInsumos(RecetasEntity receta, ProduccionDTO produccionDTO) {
        System.out.println("Actualizando stock de insumos...");

        for (RecetaInsumoEntity insumoReceta : receta.getRecetaInsumos()) {
            Long insumoId = insumoReceta.getInsumo().getId();
            System.out.println("Procesando insumo ID: " + insumoId);

            SucursalInsumoEntity stockInsumo = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(Long.valueOf(produccionDTO.getSucursalId()), insumoId)
                    .orElseThrow(() -> {
                        System.out.println("ERROR: No se encontró stock para actualizar - Insumo ID: " + insumoId);
                        return new RuntimeException("Stock de insumo no encontrado");
                    });

            BigDecimal cantidadUtilizada = insumoReceta.getCantidad().multiply(produccionDTO.getCantidad());
            System.out.println("Cantidad a descontar: " + cantidadUtilizada);

            stockInsumo.setCantidad(stockInsumo.getCantidad().subtract(cantidadUtilizada));
            sucursalInsumoRepository.save(stockInsumo);

            System.out.println("Stock actualizado - Nueva cantidad: " + stockInsumo.getCantidad());
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

    private void registrarMovimientoProduccion(RecetasEntity receta, ProduccionDTO produccionDTO) {
        MovimientoProduccionEntity movimiento = new MovimientoProduccionEntity();
        movimiento.setReceta(receta);
        movimiento.setSucursalId(Long.valueOf(produccionDTO.getSucursalId()));
        movimiento.setCantidad(produccionDTO.getCantidad());

        BigDecimal unidadesProducidas = BigDecimal.valueOf(receta.getCantidadUnidades())
                .multiply(produccionDTO.getCantidad());
        movimiento.setUnidadesProduccidas(unidadesProducidas);

        movimiento.setFechaProduccion(LocalDateTime.now());
        movimiento.setObservaciones(produccionDTO.getObservaciones());

        movimientoProduccionRepository.save(movimiento);
    }
}
