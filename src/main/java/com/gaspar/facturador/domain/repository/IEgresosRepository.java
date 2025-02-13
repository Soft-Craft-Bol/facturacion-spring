package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEgresosRepository extends JpaRepository<EgresosEntity,Long> {

}
