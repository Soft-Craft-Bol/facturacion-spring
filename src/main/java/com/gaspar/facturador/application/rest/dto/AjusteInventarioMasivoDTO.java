package com.gaspar.facturador.application.rest.dto;

import java.util.List;

public record AjusteInventarioMasivoDTO(
        List<AjusteInventarioDTO> ajustes
) { }