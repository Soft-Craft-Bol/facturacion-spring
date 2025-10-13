package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.AjusteInventarioDTO;
import com.gaspar.facturador.persistence.crud.AjusteInventarioRepository;
import com.gaspar.facturador.persistence.crud.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.entity.AjusteInventarioEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AjusteInventarioService {

    private final SucursalItemCrudRepository sucursalItemRepo;
    private final AjusteInventarioRepository ajusteRepo;

    public AjusteInventarioService(SucursalItemCrudRepository sucursalItemRepo, AjusteInventarioRepository ajusteRepo) {
        this.sucursalItemRepo = sucursalItemRepo;
        this.ajusteRepo = ajusteRepo;
    }

    @Transactional
    public void ajustarInventarioMasivo(List<AjusteInventarioDTO> ajustes) {
        for (AjusteInventarioDTO a : ajustes) {
            ajustarInventario(
                    a.sucursalId(),
                    a.itemId(),
                    a.cantidadAjuste(),
                    a.observacion(),
                    a.usuario()
            );
        }
    }


    @Transactional
    public void ajustarInventario(Integer sucursalId, Integer itemId, Integer cantidad, String observacion, String usuario) {
        // Buscar si ya existe el stock para ese producto en esa sucursal
        SucursalItemEntity sucursalItem = sucursalItemRepo
                .findBySucursal_IdAndItem_Id(sucursalId, itemId)
                .orElseGet(() -> {
                    // Si no existe, lo creamos en 0
                    SucursalItemEntity nuevo = new SucursalItemEntity();
                    nuevo.setSucursal(new SucursalEntity(sucursalId));
                    nuevo.setItem(new ItemEntity());
                    nuevo.getItem().setId(itemId);
                    nuevo.setCantidad(0);
                    return sucursalItemRepo.save(nuevo);
                });

        // Actualizamos stock
        sucursalItem.setCantidad(sucursalItem.getCantidad() + cantidad);
        sucursalItemRepo.save(sucursalItem);

        // Registramos movimiento
        AjusteInventarioEntity ajuste = new AjusteInventarioEntity();
        ajuste.setSucursalItem(sucursalItem);
        ajuste.setCantidadAjuste(cantidad);
        ajuste.setObservacion(observacion);
        ajuste.setUsuario(usuario);
        ajusteRepo.save(ajuste);
    }

}
