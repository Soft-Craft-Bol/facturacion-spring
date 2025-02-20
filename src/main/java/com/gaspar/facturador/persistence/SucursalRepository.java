package com.gaspar.facturador.persistence;

import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SucursalRepository {
    @Autowired
    private SucursalCrudRepository sucursalCrudRepository;
    public Iterable<SucursalEntity> findAll() {
        return sucursalCrudRepository.findAll();
    }

    public Optional<SucursalEntity> findById(Integer id) {
        return sucursalCrudRepository.findById(id);
    }

    public SucursalEntity save(SucursalEntity sucursal) {
        return sucursalCrudRepository.save(sucursal);
    }

    public void deleteById(Integer id) {
        sucursalCrudRepository.deleteById(id);
    }

    public long count() {
        return sucursalCrudRepository.count();
    }
}
