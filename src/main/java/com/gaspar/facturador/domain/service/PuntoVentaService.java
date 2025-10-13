package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.gaspar.facturador.persistence.dto.PuntoVentaDTO;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PuntoVentaService {

    private final IPuntoVentaRepository puntoVentaRepository;
    private final PuntoVentaCrudRepository puntoVentaCrudRepository;

    public List<PuntoVentaEntity> getPuntosVentaBySucursalId(Integer idSucursal) {
        return puntoVentaCrudRepository.findBySucursalId(idSucursal);
    }

}
