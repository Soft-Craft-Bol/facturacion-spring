package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ActividadesDocumentoSectorDto;
import com.gaspar.facturador.domain.repository.IActividadDocumentoSectorRepository;
import com.gaspar.facturador.persistence.crud.ActividadDocumentoSectorCrudRepository;
import com.gaspar.facturador.persistence.mapper.ActividadDocumentoSectorMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ActividadDocumentoSectorRepository implements IActividadDocumentoSectorRepository {

    private final ActividadDocumentoSectorMapper actividadDocumentoSectorMapper;
    private final ActividadDocumentoSectorCrudRepository actividadDocumentoSectorCrudRepository;

    public ActividadDocumentoSectorRepository(
            ActividadDocumentoSectorMapper actividadDocumentoSectorMapper,
        ActividadDocumentoSectorCrudRepository actividadDocumentoSectorCrudRepository
    ) {
        this.actividadDocumentoSectorMapper = actividadDocumentoSectorMapper;
        this.actividadDocumentoSectorCrudRepository = actividadDocumentoSectorCrudRepository;
    }

    @Override
    public void save(ActividadesDocumentoSectorDto actividadDocumentoSectorCrudRepository) {
        this.actividadDocumentoSectorCrudRepository.save(
            this.actividadDocumentoSectorMapper.toActividadDocumentoSectorEntity(actividadDocumentoSectorCrudRepository)
        );
    }

    @Override
    public void deleteAll() {
        this.actividadDocumentoSectorCrudRepository.deleteAll();
    }
}
