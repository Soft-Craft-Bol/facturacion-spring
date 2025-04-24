package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.dto.ProduccionDTO;
import com.gaspar.facturador.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProduccionService {

    private final RecetasCrudRepository recetasCrudRepository;
    private final RecetaInsumoCrudRepository recetaInsumoCrudRepository;
    private final InsumoCrudRepository insumoCrudRepository;
    private final ItemCrudRepository itemCrudRepository;
    private final ProduccionCrudRepository produccionCrudRepository;

    @Transactional
    public void producir(ProduccionDTO dto) {
        RecetasEntity receta = recetasCrudRepository.findById(dto.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        ItemEntity productoFinal = receta.getProducto();
        Integer cantidadTotal = dto.getCantidadDeseada();

        // Verificamos y descontamos insumos
        for (RecetaInsumoEntity recetaInsumo : receta.getRecetaInsumos()) {
            InsumoEntity insumo = recetaInsumo.getInsumo();

            BigDecimal cantidadPorUnidad = recetaInsumo.getCantidad();
            BigDecimal cantidadRequerida = cantidadPorUnidad.multiply(BigDecimal.valueOf(cantidadTotal));

            BigDecimal cantidadActual = BigDecimal.valueOf(insumo.getCantidad());

            if (cantidadActual.compareTo(cantidadRequerida) < 0) {
                throw new RuntimeException("No hay suficiente insumo: " + insumo.getNombre());
            }

            insumo.setCantidad(cantidadActual.subtract(cantidadRequerida).intValue());
            insumoCrudRepository.save(insumo);
        }

        // Aumentamos el stock del producto final
        BigDecimal stockActual = productoFinal.getCantidad() != null ? productoFinal.getCantidad() : BigDecimal.ZERO;
        productoFinal.setCantidad(stockActual.add(BigDecimal.valueOf(cantidadTotal)));
        itemCrudRepository.save(productoFinal);

        // Registramos producción
        ProduccionEntity produccion = new ProduccionEntity();
        produccion.setFecha(new Date());
        produccion.setCantidadProducida(cantidadTotal);
        produccion.setReceta(receta);
        produccion.setProducto(productoFinal);
        produccionCrudRepository.save(produccion);
    }
}
