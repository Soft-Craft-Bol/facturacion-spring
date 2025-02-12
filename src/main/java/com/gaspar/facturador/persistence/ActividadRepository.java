package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ActividadesDto;
import com.gaspar.facturador.domain.repository.IActividadRepository;
import com.gaspar.facturador.persistence.crud.ActividadCrudRepository;
import com.gaspar.facturador.persistence.mapper.ActividadMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ActividadRepository implements IActividadRepository {

    private final ActividadMapper actividadMapper;
    private final ActividadCrudRepository actividadCrudRepository;

    public ActividadRepository(ActividadMapper actividadMapper, ActividadCrudRepository actividadCrudRepository) {
        this.actividadMapper = actividadMapper;
        this.actividadCrudRepository = actividadCrudRepository;
    }

    @Override
    public void save(ActividadesDto actividadesDto) {
        this.actividadCrudRepository.save(this.actividadMapper.toActividadEntity(actividadesDto));
    }

    @Override
    public void deleteAll() {
        this.actividadCrudRepository.deleteAll();
    }
}
