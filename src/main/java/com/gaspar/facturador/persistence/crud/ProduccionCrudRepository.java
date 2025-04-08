package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ProduccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduccionCrudRepository  extends JpaRepository<ProduccionEntity, Long> {
}
