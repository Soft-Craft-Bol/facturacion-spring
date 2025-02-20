package com.gaspar.facturador.persistence;

import com.gaspar.facturador.domain.repository.IFacturaRepository;
import com.gaspar.facturador.persistence.crud.FacturaCrudRepository;
import com.gaspar.facturador.persistence.crud.FacturaDetalleCrudRepository;
import com.gaspar.facturador.persistence.entity.FacturaDetalleEntity;
import com.gaspar.facturador.persistence.entity.FacturaEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class FacturaRepository implements IFacturaRepository {

    private final FacturaCrudRepository facturaCrudRepository;

    public FacturaRepository(FacturaCrudRepository facturaCrudRepository) {
        this.facturaCrudRepository = facturaCrudRepository;
    }

    @Override
    public Long obtenerNumeroFactura() {
        long cantidad = this.facturaCrudRepository.count();
        return cantidad == 0 ? 1 : cantidad + 1;
    }

    @Override
    @Transactional
    public FacturaEntity save(FacturaEntity factura) {
        // Save the main invoice first
        FacturaEntity savedFactura = facturaCrudRepository.save(factura);

        // If there are details, save them
        if (factura.getDetalleList() != null && !factura.getDetalleList().isEmpty()) {
            for (FacturaDetalleEntity detalle : factura.getDetalleList()) {
                detalle.setFactura(savedFactura);
            }
            savedFactura.setDetalleList(factura.getDetalleList());
        }

        return savedFactura;
    }

    @Override
    public Optional<FacturaEntity> findById(Long id) {
        return facturaCrudRepository.findById(id);
    }

    @Override
    public Optional<FacturaEntity> findByCuf(String cuf) {
        return facturaCrudRepository.findByCuf(cuf);
    }

    @Override
    public List<FacturaEntity> findAll() {
        return (List<FacturaEntity>) facturaCrudRepository.findAll();
    }

    @Override
    @Transactional
    public void updateEstado(Long id, String estado) {
        Optional<FacturaEntity> facturaOpt = facturaCrudRepository.findById(id);
        if (facturaOpt.isPresent()) {
            FacturaEntity factura = facturaOpt.get();
            factura.setEstado(estado);
            facturaCrudRepository.save(factura);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        facturaCrudRepository.deleteById(id);
    }
}
