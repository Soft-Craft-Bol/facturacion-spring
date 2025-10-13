package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ClienteCrudRepository extends CrudRepository<ClienteEntity, Integer> {
    long count();
    List<ClienteEntity> findByNombreRazonSocialContainingIgnoreCase(String nombre);
    @Query("SELECT c FROM ClienteEntity c WHERE CAST(c.numeroDocumento AS string) LIKE CONCAT(:prefix, '%')")
    List<ClienteEntity> findByNumeroDocumentoStartingWith(@Param("prefix") String prefix);

}

