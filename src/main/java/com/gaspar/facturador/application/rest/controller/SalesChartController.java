package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.SalesChartService;
import com.gaspar.facturador.persistence.dto.SalesChartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales-chart")
public class SalesChartController {

    @Autowired
    private SalesChartService salesChartService;

    @GetMapping
    public List<SalesChartResponse> getSalesAndExpenses() {
        return salesChartService.getSalesAndExpensesByDay();
    }
}