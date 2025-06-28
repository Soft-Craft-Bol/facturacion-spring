package com.gaspar.facturador.persistence;

import com.gaspar.facturador.persistence.crud.EgresosCrudRepository;
import com.gaspar.facturador.persistence.crud.GastoRepository;
import com.gaspar.facturador.persistence.dto.SalesChartResponse;
import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SalesChartService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private EgresosCrudRepository egresosCrudRepository;

    public List<SalesChartResponse> getSalesAndExpensesByDay() {
        return gastoRepository.findAll().stream()
                .map(gasto -> new SalesChartResponse(
                        gasto.getFecha().getDayOfWeek().toString(),
                        gasto.getMonto().doubleValue(),
                        StreamSupport.stream(egresosCrudRepository.findAll().spliterator(), false)
                                .filter(egreso -> egreso.getFechaDePago().toLocalDate().equals(gasto.getFecha()))
                                .mapToDouble(EgresosEntity::getMonto)
                                .sum()
                ))
                .collect(Collectors.toList());
    }
}