package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.ParametroRepository;
import com.gaspar.facturador.persistence.entity.ParametroEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/parametros")
public class ParametroController {

    private final ParametroRepository parametroRepository;

    public ParametroController(ParametroRepository parametroRepository){
        this.parametroRepository=parametroRepository;
    }

    @GetMapping("/documentos-identidad")
    public List<ParametroEntity> getDocumentosIdentidad() {
        return parametroRepository.getDocumentosIdentidad();
    }

    @GetMapping("/unidades-medida")
    public List<ParametroEntity> getUnidadesMedida() {
        List<String> codigosClasificador = Arrays.asList("40", "46", "31", "111", "4", "5", "6", "14", "17", "27", "22");
        return parametroRepository.getUnidadesMedida(codigosClasificador);
    }
    @GetMapping("/tipo-moneda")
    public List<ParametroEntity> getTipoMoneda() {
        return parametroRepository.getTipoMoneda();
    }
}
