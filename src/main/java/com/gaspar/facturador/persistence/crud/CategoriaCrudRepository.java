package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaCrudRepository extends JpaRepository<CategoriaEntity, Integer> {
    Optional<CategoriaEntity> findByNombre(String nombre);
}