package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ProduccionDTO;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduccionService {

    private final RecetasCrudRepository recetasRepository;
    private final ItemCrudRepository itemRepository;
    private final InsumoCrudRepository insumoRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final SucursalItemCrudRepository sucursalItemRepository;
    private final MovimientoProduccionRepository movimientoProduccionRepository;



    @Transactional
    public void registrarProduccion(ProduccionDTO produccionDTO) {
        // 1. Obtener la receta
        RecetasEntity receta = recetasRepository.findById(produccionDTO.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // 2. Validar stock de insumos en la sucursal
        validarStockInsumos(receta, produccionDTO);

        // 3. Actualizar stock de insumos (restar)
        actualizarStockInsumos(receta, produccionDTO);

        // 4. Actualizar stock de producto terminado (sumar)
        actualizarStockProducto(receta, produccionDTO);

        // 5. Registrar el movimiento de producción
        registrarMovimientoProduccion(receta, produccionDTO);
    }

    private void validarStockInsumos(RecetasEntity receta, ProduccionDTO produccionDTO) {
        System.out.println("=== VALIDANDO STOCK ===");
        System.out.println("Receta ID: " + receta.getId() + ", Sucursal ID: " + produccionDTO.getSucursalId());

        for (RecetaInsumoEntity insumoReceta : receta.getRecetaInsumos()) {
            Long insumoId = insumoReceta.getInsumo().getId();
            System.out.println("Buscando stock para insumo ID: " + insumoId);

            Optional<SucursalInsumoEntity> stockOpt = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(produccionDTO.getSucursalId(), insumoId);

            if (stockOpt.isEmpty()) {
                System.out.println("NO EXISTE REGISTRO PARA: Sucursal=" + produccionDTO.getSucursalId() +
                        ", Insumo=" + insumoId);
                throw new RuntimeException("Stock de insumo no encontrado para: " +
                        insumoReceta.getInsumo().getNombre());
            }

            SucursalInsumoEntity stockInsumo = stockOpt.get();
            BigDecimal cantidadNecesaria = insumoReceta.getCantidad()
                    .multiply(BigDecimal.valueOf(produccionDTO.getCantidad()));

            System.out.println("Stock actual: " + stockInsumo.getCantidad() +
                    ", Necesario: " + cantidadNecesaria);

            if (stockInsumo.getCantidad().compareTo(cantidadNecesaria) < 0) {
                throw new RuntimeException("Stock insuficiente de: " +
                        insumoReceta.getInsumo().getNombre());
            }
        }
    }

    private void actualizarStockInsumos(RecetasEntity receta, ProduccionDTO produccionDTO) {
        System.out.println("Validando stock para sucursalId: " + produccionDTO.getSucursalId());

        for (RecetaInsumoEntity insumoReceta : receta.getRecetaInsumos()) {
            SucursalInsumoEntity stockInsumo = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(produccionDTO.getSucursalId(), insumoReceta.getInsumo().getId())
                    .orElseThrow(() -> new RuntimeException("Stock de insumo no encontrado"));

            BigDecimal cantidadUtilizada = insumoReceta.getCantidad().multiply(BigDecimal.valueOf(produccionDTO.getCantidad()));
            stockInsumo.setCantidad(stockInsumo.getCantidad().subtract(cantidadUtilizada));

            sucursalInsumoRepository.save(stockInsumo);
        }
    }

    private void actualizarStockProducto(RecetasEntity receta, ProduccionDTO produccionDTO) {
        ItemEntity producto = receta.getProducto();
        int unidadesProduccidas = receta.getCantidadUnidades() * produccionDTO.getCantidad();

        SucursalItemEntity stockProducto = sucursalItemRepository
                .findBySucursal_IdAndItem_Id(produccionDTO.getSucursalId(), producto.getId())
                .orElseGet(() -> {
                    SucursalItemEntity nuevoStock = new SucursalItemEntity();
                    nuevoStock.setSucursalId(produccionDTO.getSucursalId());
                    nuevoStock.setItem(producto);
                    nuevoStock.setCantidad(0);
                    return nuevoStock;
                });

        stockProducto.setCantidad(stockProducto.getCantidad() + unidadesProduccidas);
        sucursalItemRepository.save(stockProducto);
    }

    private void registrarMovimientoProduccion(RecetasEntity receta, ProduccionDTO produccionDTO) {
        MovimientoProduccionEntity movimiento = new MovimientoProduccionEntity();
        movimiento.setReceta(receta);
        movimiento.setSucursalId(Long.valueOf(produccionDTO.getSucursalId()));
        movimiento.setCantidad(produccionDTO.getCantidad());
        movimiento.setUnidadesProduccidas(receta.getCantidadUnidades() * produccionDTO.getCantidad());
        movimiento.setFechaProduccion(LocalDateTime.now());
        movimiento.setObservaciones(produccionDTO.getObservaciones());

        movimientoProduccionRepository.save(movimiento);
    }
}