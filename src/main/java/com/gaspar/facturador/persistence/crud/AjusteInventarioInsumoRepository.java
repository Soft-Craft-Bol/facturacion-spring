package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.AjusteInventarioEntity;
import com.gaspar.facturador.persistence.entity.AjusteInventarioInsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AjusteInventarioInsumoRepository extends JpaRepository<AjusteInventarioInsumoEntity, Long> {
}