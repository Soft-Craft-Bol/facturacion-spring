package com.gaspar.facturador.persistence;

import com.gaspar.facturador.persistence.crud.EgresosCrudRepository;
import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EgresosRepository {
    @Autowired
    private EgresosCrudRepository egresosCrudRepository;

    public List<EgresosEntity> getAll() {
        return (List<EgresosEntity>) egresosCrudRepository.findAll();
    }

    public Optional<EgresosEntity> getById(Long id) {
        return egresosCrudRepository.findById(id);
    }

    public EgresosEntity save(EgresosEntity egreso) {
        return egresosCrudRepository.save(egreso);
    }

    public EgresosEntity update(Long id, EgresosEntity egresoDetails) {
        Optional<EgresosEntity> optionalEgreso = egresosCrudRepository.findById(id);
        if (optionalEgreso.isPresent()) {
            EgresosEntity egreso = optionalEgreso.get();
            egreso.setFechaDePago(egresoDetails.getFechaDePago());
            egreso.setDescripcion(egresoDetails.getDescripcion());
            egreso.setGastoEnum(egresoDetails.getGastoEnum());
            egreso.setMonto(egresoDetails.getMonto());
            egreso.setTipoPagoEnum(egresoDetails.getTipoPagoEnum());
            egreso.setPagadoA(egresoDetails.getPagadoA());
            egreso.setNumFacturaComprobante(egresoDetails.getNumFacturaComprobante());
            egreso.setObservaciones(egresoDetails.getObservaciones());
            return egresosCrudRepository.save(egreso);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        egresosCrudRepository.deleteById(id);
    }

    public List<EgresosEntity> getEgresosByCaja(Long cajaId) {
        return egresosCrudRepository.findByCajaId(cajaId);
    }
}
