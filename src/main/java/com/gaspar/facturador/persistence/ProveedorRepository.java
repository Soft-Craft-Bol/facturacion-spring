package com.gaspar.facturador.persistence;

import com.gaspar.facturador.domain.repository.IProveedorRepository;
import com.gaspar.facturador.persistence.entity.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorRepository {
    @Autowired
    private IProveedorRepository proveedorRepository;

    public List<ProveedorEntity> findAll(){
        return proveedorRepository.findAll();
    }
    public Optional<ProveedorEntity> findById(long id) {
        return proveedorRepository.findById(id);
    }

    public ProveedorEntity save(ProveedorEntity proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void deleteById(long id) {
        proveedorRepository.deleteById(id);
    }
}