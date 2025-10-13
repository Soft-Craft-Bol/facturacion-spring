package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.AjusteInventarioDTO;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioMasivoDTO;
import com.gaspar.facturador.domain.service.AjusteInventarioService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ajustes")
public class AjusteInventarioController {

    private final AjusteInventarioService ajusteService;

    public AjusteInventarioController(AjusteInventarioService ajusteService) {
        this.ajusteService = ajusteService;
    }

    @PostMapping("/single")
    public String ajustarInventario(@RequestBody AjusteInventarioDTO dto) {
        ajusteService.ajustarInventario(
                dto.sucursalId(),
                dto.itemId(),
                dto.cantidadAjuste(),
                dto.observacion(),
                dto.usuario()
        );
        return "Ajuste realizado correctamente";
    }

    @PostMapping("/masivo")
    public String ajustarInventarioMasivo(@RequestBody AjusteInventarioMasivoDTO dto) {
        ajusteService.ajustarInventarioMasivo(dto.ajustes());
        return "Ajustes masivos realizados correctamente";
    }
}
