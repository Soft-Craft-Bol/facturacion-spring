package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.PromocionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PromocionCrudRepository extends CrudRepository<PromocionEntity, Long> {
    List<PromocionEntity> findByItemIdAndSucursalId(Integer itemId, Integer sucursalId);
}

