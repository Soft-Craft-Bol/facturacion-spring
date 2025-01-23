package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.RespuestaCuis;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.persistence.crud.CuisCrudRepository;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.mapper.CuisMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;



@Repository
public class CuisRepository implements ICuisRepository {

    private final CuisMapper cuisMapper;
    private final CuisCrudRepository cuisCrudRepository;

    public CuisRepository(CuisMapper cuisMapper, CuisCrudRepository cuisCrudRepository) {
        this.cuisMapper = cuisMapper;
        this.cuisCrudRepository = cuisCrudRepository;
    }

    @Override
    public void save(RespuestaCuis respuestaCuis, PuntoVentaEntity puntoVenta) {
        CuisEntity cuis = this.cuisMapper.toCuisEntity(respuestaCuis);
        cuis.setFechaSolicitada(LocalDateTime.now());
        cuis.setVigente(true);
        cuis.setPuntoVenta(puntoVenta);

        this.cuisCrudRepository.save(cuis);
    }

    @Override
    public Optional<CuisEntity> findActual(PuntoVentaEntity puntoVenta) {
        return this.cuisCrudRepository.findByPuntoVentaAndVigente(puntoVenta, true);
    }
}
