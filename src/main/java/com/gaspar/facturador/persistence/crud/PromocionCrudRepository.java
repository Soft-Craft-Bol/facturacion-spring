package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.PromocionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PromocionCrudRepository extends CrudRepository<PromocionEntity, Long> {
    List<PromocionEntity> findByItemIdAndSucursalId(Integer itemId, Integer sucursalId);
    Optional<PromocionEntity> findOneByItemIdAndSucursalId(Integer itemId, Integer sucursalId);
    List<PromocionEntity> findByItemId(Integer itemId); // Para la opción 2

}

