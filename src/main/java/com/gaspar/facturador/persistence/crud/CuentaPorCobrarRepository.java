package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CuentaPorCobrarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface CuentaPorCobrarRepository extends JpaRepository<CuentaPorCobrarEntity, Long> {

    List<CuentaPorCobrarEntity> findByTipoCuentaAndEstadoNot(String tipoCuenta, String estado);

    List<CuentaPorCobrarEntity> findByClienteIdAndTipoCuenta(Integer clienteId, String tipoCuenta);

    List<CuentaPorCobrarEntity> findByEstadoAndFechaVencimientoBefore(String estado, Date fecha);
}