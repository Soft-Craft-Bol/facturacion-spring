package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.AbonoCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbonoCreditoRepository extends JpaRepository<AbonoCreditoEntity, Long> {
}
