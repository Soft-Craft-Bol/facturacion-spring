package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.CierreCaja2Request;
import com.gaspar.facturador.application.request.CierreCajaRequest;
import com.gaspar.facturador.application.response.CierreCajaResponse;
import com.gaspar.facturador.application.rest.exception.CierreCajaException;
import com.gaspar.facturador.persistence.crud.CajaRepository;
import com.gaspar.facturador.persistence.crud.CierreCajaRepository;
import com.gaspar.facturador.persistence.entity.CajasEntity;
import com.gaspar.facturador.persistence.entity.CierreCajasEnity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CierreCajaService {

    private final CierreCajaRepository cierreCajaRepository;
    private final CajaRepository cajaRepository;

    @Transactional
    public CierreCajaResponse registrarCierre(CierreCajaRequest request) throws ChangeSetPersister.NotFoundException {
        CajasEntity caja = cajaRepository.findById(request.getCajaId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Validar que no haya un cierre abierto
        if (cierreCajaRepository.existsByCajaIdAndFechaCierreIsNull(request.getCajaId())) {
            throw new CierreCajaException("Ya existe un cierre abierto para esta caja");
        }

        CierreCajasEnity cierre = new CierreCajasEnity();
        cierre.setFechaApertura(LocalDateTime.now());
        cierre.setMontoInicial(request.getMontoInicial());
        cierre.setCaja(caja);

        cierre = cierreCajaRepository.save(cierre);
        return mapToResponse(cierre);
    }

    @Transactional
    public CierreCajaResponse finalizarCierre(Long cierreId, CierreCaja2Request request) {
        // Validar request
        if (request.getTotalVentas() == null || request.getTotalGastos() == null || request.getTotalEfectivo() == null) {
            throw new IllegalArgumentException("Campos obligatorios no pueden ser nulos");
        }

        // Obtener el cierre
        CierreCajasEnity cierre = cierreCajaRepository.findById(cierreId)
                .orElseThrow(() -> new EntityNotFoundException("Cierre no encontrado con ID: " + cierreId));

        // Validar estado
        if (cierre.getFechaCierre() != null) {
            throw new IllegalStateException("Este cierre ya ha sido finalizado");
        }

        // Calcular valores
        BigDecimal totalTarjeta = request.getTotalTarjeta() != null ? request.getTotalTarjeta() : BigDecimal.ZERO;
        BigDecimal totalQr = request.getTotalQr() != null ? request.getTotalQr() : BigDecimal.ZERO;

        BigDecimal totalContado = request.getTotalEfectivo()
                .add(totalTarjeta)
                .add(totalQr);

        BigDecimal totalEsperado = request.getTotalVentas()
                .subtract(request.getTotalGastos());

        BigDecimal diferencia = totalContado.subtract(totalEsperado);

        // Actualizar entidad
        cierre.setFechaCierre(LocalDateTime.now());
        cierre.setTotalVentas(request.getTotalVentas());
        cierre.setTotalGastos(request.getTotalGastos());
        cierre.setTotalEsperados(totalEsperado);
        cierre.setTotalContados(totalContado);
        cierre.setDiferencia(diferencia);
        cierre.setTotalEfectivo(request.getTotalEfectivo());
        cierre.setTotalTarjeta(totalTarjeta);
        cierre.setTotalQr(totalQr);
        cierre.setObservaciones(request.getObservaciones());

        // Guardar
        cierre = cierreCajaRepository.save(cierre);
        return mapToResponse(cierre);
    }


    public List<CierreCajaResponse> obtenerCierresPorCaja(Long cajaId) {
        return cierreCajaRepository.findByCajaIdOrderByFechaCierreDesc(cajaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CierreCajaResponse obtenerCierreAbierto(Long cajaId) throws ChangeSetPersister.NotFoundException {
        CierreCajasEnity cierre = cierreCajaRepository.findByCajaIdAndFechaCierreIsNull(cajaId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return mapToResponse(cierre);
    }

    private CierreCajaResponse mapToResponse(CierreCajasEnity cierre) {
        CierreCajaResponse response = new CierreCajaResponse();
        response.setId(cierre.getId());
        response.setFechaApertura(cierre.getFechaApertura());
        response.setFechaCierre(cierre.getFechaCierre());
        response.setMontoInicial(cierre.getMontoInicial());
        response.setTotalVentas(cierre.getTotalVentas());
        response.setTotalGastos(cierre.getTotalGastos());
        response.setTotalEsperado(cierre.getTotalEsperados());
        response.setTotalContado(cierre.getTotalContados());
        response.setDiferencia(cierre.getDiferencia());
        response.setTotalEfectivo(cierre.getTotalEfectivo());
        response.setTotalTarjeta(cierre.getTotalTarjeta());
        response.setTotalQr(cierre.getTotalQr());
        response.setObservaciones(cierre.getObservaciones());
        response.setCajaId(cierre.getCaja().getId());
        response.setNombreCaja(cierre.getCaja().getNombre());
        return response;
    }
}