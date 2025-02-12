package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.persistence.crud.CufdCrudRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.mapper.CufdMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public class CufdRepository implements ICufdRepository {

    private final CufdMapper cufdMapper;
    private final CufdCrudRepository cufdCrudRepository;

    public CufdRepository(CufdMapper cufdMapper, CufdCrudRepository cufdCrudRepository) {
        this.cufdMapper = cufdMapper;
        this.cufdCrudRepository = cufdCrudRepository;
    }

    @Override
    public void save(RespuestaCufd respuestaCufd, PuntoVentaEntity puntoVenta) {

        CufdEntity cufd = this.cufdMapper.toCufdEntity(respuestaCufd);
        cufd.setFechaInicio(LocalDateTime.now());
        cufd.setVigente(true);
        cufd.setPuntoVenta(puntoVenta);

        this.cufdCrudRepository.save(cufd);
    }

    @Override
    public Optional<CufdEntity> findActual(PuntoVentaEntity puntoVenta) {
        return this.cufdCrudRepository.findByPuntoVentaAndVigente(puntoVenta, true);
    }
}
