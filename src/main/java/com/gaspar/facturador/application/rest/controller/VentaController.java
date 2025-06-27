package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.response.ClienteFrecuenteDTO;
import com.gaspar.facturador.application.response.ProductoMasVendidoDTO;
import com.gaspar.facturador.domain.service.ProductoService;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.VentasDetalleRepository;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.dto.VentaHoyDTO;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.domain.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final PuntoVentaRepository puntoVentaRepository;
    private final ProductoService productoService;

    public VentaController(VentaService ventaService, PuntoVentaRepository puntoVentaRepository, ProductoService productoService) {
        this.ventaService = ventaService;
        this.puntoVentaRepository = puntoVentaRepository;
        this.productoService = productoService;
    }


    @PostMapping
    public ResponseEntity<?> createVenta(@Valid @RequestBody VentaSinFacturaRequest ventaRequest) {
        // Verificar si el punto de venta existe
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(ventaRequest.getIdPuntoVenta()));
        if (puntoVenta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El punto de venta con ID " + ventaRequest.getIdPuntoVenta() + " no existe.");
        }

        try {
            VentasEntity nuevaVenta = ventaService.saveVenta(ventaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la venta: " + e.getMessage());
        }
    }

    @GetMapping("/hoy")
    public ResponseEntity<Page<VentaHoyDTO>> getVentasDeHoy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer idPuntoVenta,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String tipoBusqueda) {

        Page<VentaHoyDTO> ventas = ventaService.getVentasDeHoy(
                page, size, idPuntoVenta, estado, busqueda, tipoBusqueda);

        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/totales-por-dia")
    public ResponseEntity<List<TotalVentasPorDiaDTO>> obtenerTotalesPorDia() {
        List<TotalVentasPorDiaDTO> totales = ventaService.obtenerTotalesVentasPorDia();
        return ResponseEntity.ok(totales);
    }

    @GetMapping("/mas-vendidos")
    public List<ProductoMasVendidoDTO> obtenerProductosMasVendidos(@RequestParam(defaultValue = "10") int limite) {
        return productoService.obtenerTopProductosMasVendidos(limite);
    }

    @GetMapping("/clientes-frecuentes")
    public ResponseEntity<List<ClienteFrecuenteDTO>> obtenerClientesFrecuentes() {
        List<ClienteFrecuenteDTO> clientesFrecuentes = ventaService.obtenerTop10ClientesFrecuentes();
        return ResponseEntity.ok(clientesFrecuentes);
    }
}
