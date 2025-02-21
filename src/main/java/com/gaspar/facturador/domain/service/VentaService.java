package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.persistence.crud.VentaCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaCrudRepository ventaCrudRepository;

    @Autowired
    public VentaService(VentaCrudRepository ventaCrudRepository) {
        this.ventaCrudRepository = ventaCrudRepository;
    }

    public List<VentasEntity> getAllVentas() {
        return ventaCrudRepository.findAll();
    }

    public Optional<VentasEntity> getVentaById(Long id) {
        return ventaCrudRepository.findById(id);
    }

    public VentasEntity saveVenta(VentasEntity venta) {
        return ventaCrudRepository.save(venta);
    }

    public void deleteVenta(Long id) {
        ventaCrudRepository.deleteById(id);
    }

    public List<VentasEntity> getVentasByTipoComprobante(String tipoComprobante) {
        return ventaCrudRepository.findByTipoComprobante(tipoComprobante);
    }
}