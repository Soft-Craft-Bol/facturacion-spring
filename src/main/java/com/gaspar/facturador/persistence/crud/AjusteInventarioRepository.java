package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.AjusteInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AjusteInventarioRepository extends JpaRepository<AjusteInventarioEntity, Long> {
}
