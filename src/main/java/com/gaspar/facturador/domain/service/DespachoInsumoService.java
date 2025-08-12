package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.crud.DespachoInsumoRepository;
import com.gaspar.facturador.persistence.entity.DespachoInsumoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DespachoInsumoService {
    private final DespachoInsumoRepository despachoInsumoRepository;

    public List<DespachoInsumoEntity> findAll() {
        return despachoInsumoRepository.findAll();
    }

    public Optional<DespachoInsumoEntity> findById(Long id) {
        return despachoInsumoRepository.findById(id);
    }

    @Transactional
    public DespachoInsumoEntity save(DespachoInsumoEntity despacho) {
        // Validar que hay stock suficiente en sucursal origen para cada insumo
        despacho.getItems().forEach(item -> item.setDespacho(despacho));
        return despachoInsumoRepository.save(despacho);
    }

    @Transactional
    public void delete(Long id) {
        despachoInsumoRepository.deleteById(id);
    }

    @Transactional
    public DespachoInsumoEntity recibirDespacho(Long id) {
        DespachoInsumoEntity despacho = despachoInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));

        despacho.setEstado("RECIBIDO");
        despacho.getItems().forEach(item -> {
            if (item.getCantidadRecibida() == null) {
                item.setCantidadRecibida(item.getCantidadEnviada());
            }
            // Aquí actualizarías el stock en sucursal destino
        });

        return despachoInsumoRepository.save(despacho);
    }
}