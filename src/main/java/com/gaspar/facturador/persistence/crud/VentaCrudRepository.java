package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.VentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaCrudRepository extends JpaRepository<VentasEntity, Long> {
    List<VentasEntity> findByTipoComprobante(String tipoComprobante);
}