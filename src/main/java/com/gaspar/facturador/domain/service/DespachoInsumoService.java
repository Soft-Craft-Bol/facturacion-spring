package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.DespachoInsumoRequest;
import com.gaspar.facturador.persistence.crud.DespachoInsumoRepository;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DespachoInsumoService {
    private final DespachoInsumoRepository despachoInsumoRepository;
    private final SucursalCrudRepository sucursalRepository;
    private final InsumoCrudRepository insumoCrudRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoCrudRepository;

    public List<DespachoInsumoEntity> findAll() {
        return despachoInsumoRepository.findAll();
    }

    public Optional<DespachoInsumoEntity> findById(Long id) {
        return despachoInsumoRepository.findById(id);
    }

    @Transactional
    public DespachoInsumoEntity save(DespachoInsumoRequest request) {
        // Obtener sucursales
        SucursalEntity origen = sucursalRepository.findById(request.getSucursalOrigen())
                .orElseThrow(() -> new RuntimeException("Sucursal origen no encontrada"));
        SucursalEntity destino = sucursalRepository.findById(request.getSucursalDestino())
                .orElseThrow(() -> new RuntimeException("Sucursal destino no encontrada"));

        // Crear entidad de despacho
        DespachoInsumoEntity despacho = DespachoInsumoEntity.builder()
                .sucursalOrigen(origen)
                .sucursalDestino(destino)
                .responsable(request.getResponsable())
                .observaciones(request.getObservaciones())
                .build();

        List<DespachoInsumoItemEntity> items = request.getItems().stream()
                .map(itemRequest -> {
                    InsumoEntity insumo = insumoCrudRepository.findById(itemRequest.getInsumo())
                            .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

                    return DespachoInsumoItemEntity.builder()
                            .despacho(despacho)
                            .insumo(insumo)
                            .cantidadEnviada(itemRequest.getCantidadEnviada())
                            .build();
                })
                .collect(Collectors.toList());

        despacho.setItems(items);

        // Validar stock y actualizar
        validarYActualizarStock(origen, destino, items);

        return despachoInsumoRepository.save(despacho);
    }

    private void validarYActualizarStock(SucursalEntity origen, SucursalEntity destino,
                                         List<DespachoInsumoItemEntity> items) {
        for (DespachoInsumoItemEntity item : items) {
            // Obtener el stock en la sucursal origen
            SucursalInsumoEntity stockOrigen = sucursalInsumoCrudRepository
                    .findBySucursalAndInsumo(origen, item.getInsumo())
                    .orElseThrow(() -> new RuntimeException(
                            "No existe stock del insumo " + item.getInsumo().getNombre() +
                                    " en la sucursal " + origen.getNombre()));

            // Validar que haya suficiente stock
            if (stockOrigen.getCantidad().compareTo(item.getCantidadEnviada()) < 0) {
                throw new RuntimeException("Stock insuficiente para el insumo: " +
                        item.getInsumo().getNombre() + ". Stock actual: " +
                        stockOrigen.getCantidad() + ", cantidad solicitada: " +
                        item.getCantidadEnviada());
            }

            // Disminuir stock en origen
            stockOrigen.setCantidad(stockOrigen.getCantidad().subtract(item.getCantidadEnviada()));
            sucursalInsumoCrudRepository.save(stockOrigen);

            // Aumentar stock en destino (o crear registro si no existe)
            SucursalInsumoEntity stockDestino = sucursalInsumoCrudRepository
                    .findBySucursalAndInsumo(destino, item.getInsumo())
                    .orElse(SucursalInsumoEntity.builder()
                            .sucursal(destino)
                            .insumo(item.getInsumo())
                            .cantidad(BigDecimal.ZERO)
                            .build());

            stockDestino.setCantidad(stockDestino.getCantidad().add(item.getCantidadEnviada()));
            sucursalInsumoCrudRepository.save(stockDestino);
        }
    }


    @Transactional
    public void delete(Long id) {
        despachoInsumoRepository.deleteById(id);
    }
}