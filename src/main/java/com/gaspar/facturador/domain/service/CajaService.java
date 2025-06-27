package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.CajaResponse;
import com.gaspar.facturador.persistence.SucursalRepository;
import com.gaspar.facturador.persistence.crud.CajaRepository;
import com.gaspar.facturador.persistence.entity.CajasEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.application.request.CajaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository cajaRepository;
    private final SucursalRepository sucursalRepository;

    @Transactional
    public CajaResponse crearCaja(CajaRequest request) throws ChangeSetPersister.NotFoundException {
        SucursalEntity sucursal = sucursalRepository.findById(Math.toIntExact(request.getSucursalId()))
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        if (cajaRepository.existsByNombreAndSucursalId(request.getNombre(), request.getSucursalId())) {
            throw new IllegalArgumentException("Ya existe una caja con ese nombre en esta sucursal");
        }

        CajasEntity caja = new CajasEntity();
        caja.setNombre(request.getNombre());
        caja.setEstado(request.getEstado());
        caja.setSucursal(sucursal);

        caja = cajaRepository.save(caja);
        return mapToResponse(caja);
    }

    public List<CajaResponse> obtenerCajasPorSucursal(Long sucursalId) {
        return cajaRepository.findBySucursalId(sucursalId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CajaResponse obtenerCajaPorId(Long id) throws ChangeSetPersister.NotFoundException {
        CajasEntity caja = cajaRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return mapToResponse(caja);
    }

    private CajaResponse mapToResponse(CajasEntity caja) {
        CajaResponse response = new CajaResponse();
        response.setId(caja.getId());
        response.setNombre(caja.getNombre());
        response.setEstado(caja.getEstado());
        response.setSucursalId(Long.valueOf(caja.getSucursal().getId()));
        response.setNombreSucursal(caja.getSucursal().getNombre());
        return response;
    }
}