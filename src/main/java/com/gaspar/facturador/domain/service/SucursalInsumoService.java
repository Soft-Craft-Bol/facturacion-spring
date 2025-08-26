package com.gaspar.facturador.domain.service;
// SucursalInsumoService

import com.gaspar.facturador.application.request.SucursalInsumoRequest;
import com.gaspar.facturador.persistence.SucursalRepository;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SucursalInsumoService {
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final InsumoCrudRepository insumoRepository;
    private final SucursalRepository sucursalRepository;

    @Transactional
    public void addInsumoToSucursal(Long insumoId, SucursalInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        // Verificar si la sucursal existe
        SucursalEntity sucursal = sucursalRepository.findById(Math.toIntExact(request.getSucursalId()))
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Verificar si el insumo existe
        InsumoEntity insumo = insumoRepository.findById(insumoId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Verificar si ya existe la relación
        if(sucursalInsumoRepository.existsBySucursalIdAndInsumoId(Long.valueOf(sucursal.getId()), insumo.getId())) {
            throw new IllegalStateException("El insumo ya está registrado en esta sucursal");
        }

        // Crear y guardar la relación
        SucursalInsumoEntity sucursalInsumo = new SucursalInsumoEntity();
        sucursalInsumo.setSucursal(sucursal);
        sucursalInsumo.setInsumo(insumo);
        sucursalInsumo.setCantidad(request.getCantidad());
        sucursalInsumo.setStockMinimo(request.getStockMinimo());
        sucursalInsumo.setFechaIngreso(new Date());
        sucursalInsumo.setFechaVencimiento(request.getFechaVencimiento());

        sucursalInsumoRepository.save(sucursalInsumo);
    }

    @Transactional
    public void updateStockInsumo(Integer sucursalId, Long insumoId, BigDecimal cantidad) throws ChangeSetPersister.NotFoundException {
        SucursalInsumoEntity sucursalInsumo = sucursalInsumoRepository.findBySucursalIdAndInsumoId(sucursalId, insumoId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        BigDecimal nuevaCantidad = sucursalInsumo.getCantidad().add(cantidad);
        if(nuevaCantidad.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("No hay suficiente stock para realizar esta operación");
        }

        sucursalInsumo.setCantidad(nuevaCantidad);
        sucursalInsumoRepository.save(sucursalInsumo);
    }
}