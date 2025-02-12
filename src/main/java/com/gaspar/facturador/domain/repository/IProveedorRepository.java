package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorRepository extends JpaRepository<ProveedorEntity,Long> {

}