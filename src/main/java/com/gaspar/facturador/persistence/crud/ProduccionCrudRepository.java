package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ProduccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProduccionCrudRepository extends JpaRepository<ProduccionEntity, Long>, JpaSpecificationExecutor<ProduccionEntity> {

}
