package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.AjusteInventarioInsumoDTO;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.AjusteInventarioInsumoRepository;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AjusteInventariosInsumosService {

    private final SucursalInsumoCrudRepository sucursalInsumoRepo;
    private final AjusteInventarioInsumoRepository ajusteRepo;
    private final InsumoCrudRepository insumoRepo;
    private final SucursalCrudRepository sucursalRepo;

    public AjusteInventariosInsumosService(
            SucursalInsumoCrudRepository sucursalInsumoRepo,
            AjusteInventarioInsumoRepository ajusteRepo,
            InsumoCrudRepository insumoRepo,
            SucursalCrudRepository sucursalRepo) {
        this.sucursalInsumoRepo = sucursalInsumoRepo;
        this.ajusteRepo = ajusteRepo;
        this.insumoRepo = insumoRepo;
        this.sucursalRepo = sucursalRepo;
    }

    @Transactional
    public void ajustarInventarioInsumoMasivo(List<AjusteInventarioInsumoDTO> ajustes) {
        for (AjusteInventarioInsumoDTO a : ajustes) {
            ajustarInventarioInsumo(
                    a.sucursalId(),
                    a.insumoId(),
                    a.cantidadAjuste(),
                    a.motivo(),
                    a.usuarioResponsable()
            );
        }
    }

    @Transactional
    public void ajustarInventarioInsumo(Integer sucursalId, Long insumoId, BigDecimal cantidadAjuste, String motivo, String usuario) {
        // Buscar las entidades completas desde la base de datos
        SucursalEntity sucursal = sucursalRepo.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + sucursalId));

        InsumoEntity insumo = insumoRepo.findById(insumoId)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + insumoId));

        // Buscar stock de insumo en la sucursal
        Optional<SucursalInsumoEntity> existingSucursalInsumo = sucursalInsumoRepo
                .findBySucursal_IdAndInsumo_Id(sucursalId, insumoId);

        SucursalInsumoEntity sucursalInsumo;
        BigDecimal cantidadAnterior;

        if (existingSucursalInsumo.isPresent()) {
            sucursalInsumo = existingSucursalInsumo.get();
            cantidadAnterior = sucursalInsumo.getCantidad();
        } else {
            sucursalInsumo = new SucursalInsumoEntity();
            sucursalInsumo.setSucursal(sucursal); // Entidad gestionada
            sucursalInsumo.setInsumo(insumo); // Entidad gestionada
            sucursalInsumo.setCantidad(BigDecimal.ZERO);
            cantidadAnterior = BigDecimal.ZERO;
        }

        BigDecimal cantidadNueva = cantidadAnterior.add(cantidadAjuste);
        sucursalInsumo.setCantidad(cantidadNueva);
        sucursalInsumoRepo.save(sucursalInsumo);

        // Registrar ajuste
        AjusteInventarioInsumoEntity ajuste = new AjusteInventarioInsumoEntity();
        ajuste.setSucursalInsumo(sucursalInsumo);
        ajuste.setCantidadAnterior(cantidadAnterior);
        ajuste.setCantidadNueva(cantidadNueva);
        ajuste.setDiferencia(cantidadAjuste);
        ajuste.setTipoAjuste(cantidadAjuste.compareTo(BigDecimal.ZERO) >= 0 ? "ENTRADA" : "SALIDA");
        ajuste.setMotivo(motivo);
        ajuste.setUsuarioResponsable(usuario);

        ajusteRepo.save(ajuste);
    }
}