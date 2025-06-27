package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ProductoMasVendidoDTO;
import com.gaspar.facturador.persistence.crud.VentasDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final VentasDetalleRepository ventasDetalleRepository;

    public ProductoService(VentasDetalleRepository ventasDetalleRepository) {
        this.ventasDetalleRepository = ventasDetalleRepository;
    }

    public List<ProductoMasVendidoDTO> obtenerTopProductosMasVendidos(int limite) {
        return ventasDetalleRepository.findTop10ProductosMasVendidos().stream()
                .limit(limite)
                .toList();
    }
}
