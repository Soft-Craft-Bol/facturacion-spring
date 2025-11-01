package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface HorarioCrudRepository extends JpaRepository<HorarioEntity,Long> {
    List<HorarioEntity> findByIdPanadero(Long idPanadero);
}
