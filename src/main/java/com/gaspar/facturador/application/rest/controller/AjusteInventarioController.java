package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.AjusteInventarioResponse;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioDTO;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioFiltroDTO;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioMasivoDTO;
import com.gaspar.facturador.domain.service.AjusteInventarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public Page<AjusteInventarioResponse> listarAjustes(
            @RequestParam(required = false) Integer sucursalId,
            @RequestParam(required = false) Integer itemId,
            @RequestParam(required = false) String itemCodigo,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) String usuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fecha") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        // Crear objeto de filtro
        AjusteInventarioFiltroDTO filtro = new AjusteInventarioFiltroDTO(
                sucursalId,
                itemId,
                itemCodigo,
                fechaDesde != null ? java.time.LocalDate.parse(fechaDesde) : null,
                fechaHasta != null ? java.time.LocalDate.parse(fechaHasta) : null,
                usuario
        );

        // Crear paginación
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ajusteService.listarAjustesConFiltros(filtro, pageable);
    }
}
