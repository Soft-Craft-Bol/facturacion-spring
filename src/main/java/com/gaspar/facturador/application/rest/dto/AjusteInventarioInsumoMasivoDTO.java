package com.gaspar.facturador.application.rest.dto;

import java.util.List;

public record AjusteInventarioInsumoMasivoDTO(
        List<AjusteInventarioInsumoDTO> ajustes
) { }
