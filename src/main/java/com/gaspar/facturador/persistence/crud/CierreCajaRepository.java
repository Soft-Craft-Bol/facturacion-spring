package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CajasEntity;
import com.gaspar.facturador.persistence.entity.CierreCajasEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CierreCajaRepository extends JpaRepository<CierreCajasEnity, Long> {
    Optional<CierreCajasEnity> findByCaja(CajasEntity caja);

}