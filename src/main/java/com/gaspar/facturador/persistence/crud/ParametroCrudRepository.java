package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ParametroEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ParametroCrudRepository extends CrudRepository<ParametroEntity, Long> {
    @Query("SELECT p FROM ParametroEntity p WHERE p.codigoTipoParametro = :codigoTipoParametro")
    List<ParametroEntity> findByCodigoTipoParametro(@Param("codigoTipoParametro") String codigoTipoParametro);
}
