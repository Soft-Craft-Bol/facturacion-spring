package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.DespachoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface IDespachoRepository extends JpaRepository<DespachoEntity, Long>, JpaSpecificationExecutor<DespachoEntity> {
}
