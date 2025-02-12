package com.gaspar.facturador.persistence;

import com.gaspar.facturador.domain.repository.IEgresosRepository;
import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EgresosRepository {
    @Autowired
    private IEgresosRepository egresosRepository;

    public List<EgresosEntity> findAll() {
        return egresosRepository.findAll();
    }

    public Optional<EgresosEntity> findById(long id) {
        return egresosRepository.findById(id);
    }

    public EgresosEntity save(EgresosEntity egreso) {
        return egresosRepository.save(egreso);
    }

    public void deleteById(long id) {
        egresosRepository.deleteById(id);
    }
}
