package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.AjusteInventarioInsumoResponse;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioInsumoFiltroDTO;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioInsumoMasivoDTO;
import com.gaspar.facturador.domain.service.AjusteInventariosInsumosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public Page<AjusteInventarioInsumoResponse> listarAjustes(
            @RequestParam(required = false) Integer sucursalId,
            @RequestParam(required = false) Long insumoId,
            @RequestParam(required = false) String insumoCodigo,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) String usuarioResponsable,
            @RequestParam(required = false) String tipoAjuste,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fechaAjuste") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        // Crear objeto de filtro
        AjusteInventarioInsumoFiltroDTO filtro = new AjusteInventarioInsumoFiltroDTO(
                sucursalId,
                insumoId,
                insumoCodigo,
                fechaDesde != null ? java.time.LocalDate.parse(fechaDesde) : null,
                fechaHasta != null ? java.time.LocalDate.parse(fechaHasta) : null,
                usuarioResponsable,
                tipoAjuste
        );

        // Crear paginación
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ajusteService.listarAjustesConFiltros(filtro, pageable);
    }
}
