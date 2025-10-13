package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.AjusteInventarioInsumoMasivoDTO;
import com.gaspar.facturador.domain.service.AjusteInventariosInsumosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajustes-insumo")
public class AjusteInventarioInsumoController {

    private final AjusteInventariosInsumosService ajusteService;

    public AjusteInventarioInsumoController(AjusteInventariosInsumosService ajusteService) {
        this.ajusteService = ajusteService;
    }

    @PostMapping("/masivo")
    public String ajustarInventarioMasivo(@RequestBody AjusteInventarioInsumoMasivoDTO dto) {
        ajusteService.ajustarInventarioInsumoMasivo(dto.ajustes());
        return "Ajustes masivos de insumos realizados correctamente";
    }
}
