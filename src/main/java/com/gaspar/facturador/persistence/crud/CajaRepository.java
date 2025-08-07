package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CajasEntity;
import com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CajaRepository extends JpaRepository<CajasEntity, Long> {
    boolean existsBySucursalIdAndTurnoAndUsuarioAperturaIdAndEstado(
            Integer sucursalId,
            TurnoTrabajo turno,
            Long usuarioAperturaId,
            String estado
    );
    Optional<CajasEntity> findByUsuarioAperturaIdAndEstado(Long usuarioId, String estado);
    Page<CajasEntity> findAll(Pageable pageable);
    Page<CajasEntity> findAllByUsuarioAperturaIdAndFechaAperturaBetween(
            Long usuarioId,
            LocalDateTime desde,
            LocalDateTime hasta,
            Pageable pageable
    );

    Page<CajasEntity> findAllByUsuarioAperturaId(
            Long usuarioId,
            Pageable pageable
    );

}
