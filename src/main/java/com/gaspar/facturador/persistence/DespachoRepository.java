package com.gaspar.facturador.persistence;

import com.gaspar.facturador.persistence.crud.IDespachoRepository;
import com.gaspar.facturador.persistence.entity.DespachoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DespachoRepository {
    private final IDespachoRepository despachoRepository;

    public DespachoRepository(IDespachoRepository despachoRepository){
        this.despachoRepository=despachoRepository;
    }
    public List<DespachoEntity> getAll() {
        return (List<DespachoEntity>) despachoRepository.findAll();
    }

    public Optional<DespachoEntity> getById(Long id) {
        return despachoRepository.findById(id);
    }

    public DespachoEntity save(DespachoEntity despacho) {
        return despachoRepository.save(despacho);
    }

    public void delete(Long id) {
        despachoRepository.deleteById(id);
    }
}
