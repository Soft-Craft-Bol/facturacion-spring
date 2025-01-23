package com.gaspar.facturador.persistence;

import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public class PuntoVentaRepository implements IPuntoVentaRepository {

    private PuntoVentaCrudRepository puntoVentaCrudRepository;

    public PuntoVentaRepository(PuntoVentaCrudRepository puntoVentaCrudRepository) {
        this.puntoVentaCrudRepository = puntoVentaCrudRepository;
    }

    @Override
    public Optional<PuntoVentaEntity> findById(Integer id) {
        return this.puntoVentaCrudRepository.findById(id);
    }

    @Override
    public Optional<PuntoVentaEntity> findByCodigo(Integer codigo) {
        return this.puntoVentaCrudRepository.findByCodigo(codigo);
    }
}
