package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.persistence.crud.CufdCrudRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.mapper.CufdMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
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
        Optional<CufdEntity> cufdAnterior = this.cufdCrudRepository.findByPuntoVentaAndVigente(puntoVenta, true);
        cufdAnterior.ifPresent(cufd -> {
            cufd.setVigente(false);
            this.cufdCrudRepository.save(cufd); // Guardamos el cambio
        });

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

    @Override
    public List<CufdEntity> findAnteriores(PuntoVentaEntity puntoVenta) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(72);
        return this.cufdCrudRepository.findAnteriores(puntoVenta, fechaLimite);
    }

    @Override
    public boolean existsByCodigoStartingWithAndFechaVigenciaAfter(String nombreDir, LocalDateTime now) {
        if (nombreDir == null || nombreDir.isEmpty()) {
            return false;
        }
        String codigoBusqueda = nombreDir.length() > 10 ? nombreDir.substring(0, 10) : nombreDir;

        return cufdCrudRepository.existsByCodigoStartingWithAndFechaVigenciaAfterAndVigente(
                codigoBusqueda, now, true);
    }


}