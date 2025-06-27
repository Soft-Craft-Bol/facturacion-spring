package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.CompraInsumoRequest;
import com.gaspar.facturador.persistence.SucursalRepository;
import com.gaspar.facturador.persistence.crud.CompraInsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.GastoRepository;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.ProveedorRepository;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CompraInsumoService {
    private final CompraInsumoCrudRepository compraInsumoRepository;
    private final InsumoCrudRepository insumoRepository;
    private final SucursalRepository sucursalRepository;
    private final ProveedorRepository proveedorRepository;
    private final GastoRepository gastoRepository;
    private final SucursalInsumoService sucursalInsumoService;

    @Transactional
    public void registrarCompraInsumo(CompraInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        // Validar y obtener entidades relacionadas
        InsumoEntity insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        SucursalEntity sucursal = sucursalRepository.findById(Math.toIntExact(request.getSucursalId()))
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        ProveedorEntity proveedor = null;
        if(request.getProveedorId() != null) {
            proveedor = proveedorRepository.findById(request.getProveedorId())
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        }

        // Crear el gasto asociado
        GastoEntity gasto = new GastoEntity();
        gasto.setDescripcion("Compra de " + insumo.getNombre());
        gasto.setMonto(request.getPrecioUnitario().multiply(BigDecimal.valueOf(request.getCantidad())));
        gasto.setFecha(LocalDate.now());
        gasto.setCategoria(insumo.getTipo() == TipoInsumo.MATERIA_PRIMA ?
                GastoEnum.COMPRA_MATERIA_PRIMA : GastoEnum.COMPRA_PRODUCTOS_TERMINADOS);
        gasto.setProveedor(proveedor);
        gasto.setSucursal(sucursal);
        gasto.setNumeroFactura(request.getNumeroFactura());
        gasto.setNotas(request.getNotas());
        gasto = gastoRepository.save(gasto);

        // Crear la compra de insumo
        CompraInsumoEntity compra = new CompraInsumoEntity();
        compra.setCantidad(BigDecimal.valueOf(request.getCantidad()));
        compra.setPrecioUnitario(request.getPrecioUnitario());
        compra.setFecha(new Date(System.currentTimeMillis()));
        compra.setGasto(gasto);
        compra.setInsumo(insumo);
        compra.setSucursal(sucursal);
        compra.setProveedor(proveedor);
        compraInsumoRepository.save(compra);

        // Actualizar el stock en la sucursal
        sucursalInsumoService.updateStockInsumo(
                Long.valueOf(sucursal.getId()),
                insumo.getId(),
                BigDecimal.valueOf(request.getCantidad()));

        // Actualizar el precio actual del insumo si es diferente
        if(request.getPrecioUnitario().compareTo(insumo.getPrecioActual()) != 0) {
            insumo.setPrecioActual(request.getPrecioUnitario());
            insumoRepository.save(insumo);
        }
    }
}