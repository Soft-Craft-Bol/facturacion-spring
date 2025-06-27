package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
}
