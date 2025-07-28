package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.dto.PuntoVentaDTO;

public class PuntoVentaService {

    private final IPuntoVentaRepository puntoVentaRepository;

    public PuntoVentaService(IPuntoVentaRepository puntoVentaRepository) {
        this.puntoVentaRepository = puntoVentaRepository;
    }

    public void createPuntoVenta(PuntoVentaDTO puntoVentaDTO) {
        // Convert DTO to Entity

    }
}
