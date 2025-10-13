package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.AnularVentaRequest;
import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.response.ClienteFrecuenteDTO;
import com.gaspar.facturador.application.response.CompraInsumoResponse;
import com.gaspar.facturador.application.response.PagedResponse;
import com.gaspar.facturador.application.response.ProductoMasVendidoDTO;
import com.gaspar.facturador.application.rest.dto.ReporteProductoDTO;
import com.gaspar.facturador.application.rest.dto.VentaListadoDTO;
import com.gaspar.facturador.application.rest.dto.VentaResponseDTO;
import com.gaspar.facturador.application.rest.dto.VentasFiltroDTO;
import com.gaspar.facturador.domain.service.ProductoService;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.dto.VentaFiltroDTO;
import com.gaspar.facturador.persistence.dto.VentaHoyDTO;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.domain.service.VentaService;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
        try {
            VentasEntity nuevaVenta = ventaService.saveVenta(ventaRequest);
            VentaResponseDTO response = ventaService.convertToVentaResponseDTO(nuevaVenta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

    @GetMapping("/resumen-pagos")
    public ResponseEntity<Map<String, Object>> obtenerResumenPorTipoYMetodo(
            @RequestParam("cajaId") Long cajaId) {
        Map<String, Object> resumen = ventaService.obtenerResumenVentasConYsinFacturacion(cajaId);
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/con-factura")
    public ResponseEntity<Page<VentaListadoDTO>> getVentasConFactura(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String estadoFactura,
            @RequestParam(required = false) TipoPagoEnum metodoPago,
            @RequestParam(required = false) String codigoCliente,
            @RequestParam(required = false) String codigoProducto,
            @RequestParam(required = false) BigDecimal montoMin,
            @RequestParam(required = false) BigDecimal montoMax,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<VentaListadoDTO> ventasPage = ventaService.obtenerVentasConFactura(
                fechaDesde, fechaHasta, estadoFactura, metodoPago,
                codigoCliente, codigoProducto, montoMin, montoMax, pageable);

        return ResponseEntity.ok(ventasPage);
    }

    @GetMapping("/sin-factura")
    public ResponseEntity<Page<VentaListadoDTO>> getVentasSinFactura(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) TipoPagoEnum metodoPago,
            @RequestParam(required = false) String codigoCliente,
            @RequestParam(required = false) String codigoProducto,
            @RequestParam(required = false) BigDecimal montoMin,
            @RequestParam(required = false) BigDecimal montoMax,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<VentaListadoDTO> ventasPage = ventaService.obtenerVentasSinFactura(
                fechaDesde, fechaHasta, metodoPago,
                codigoCliente, codigoProducto, montoMin, montoMax, pageable);

        return ResponseEntity.ok(ventasPage);
    }

    @GetMapping("/caja/{cajaId}/productos")
    public ResponseEntity<List<ReporteProductoDTO>> getProductosVendidosPorCaja(
            @PathVariable Long cajaId) {

        List<ReporteProductoDTO> reporte = ventaService.obtenerReporteProductosPorCaja(cajaId);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/caja/{cajaId}/resumen")
    public ResponseEntity<Map<String, Object>> getResumenVentasPorCaja(
            @PathVariable Long cajaId) {

        List<ReporteProductoDTO> productos = ventaService.obtenerReporteProductosPorCaja(cajaId);
        BigDecimal totalVentas = ventaService.obtenerTotalVentasPorCaja(cajaId);

        return ResponseEntity.ok(Map.of(
                "cajaId", cajaId,
                "totalProductosVendidos", productos.size(),
                "totalVentas", totalVentas,
                "detallesProductos", productos
        ));
    }

    @GetMapping("/caja/{cajaId}/total-ventas")
    public ResponseEntity<Map<String, Object>> getTotalVentasPorCaja(
            @PathVariable Long cajaId) {

        BigDecimal totalVentas = ventaService.obtenerTotalVentasPorCaja(cajaId);

        return ResponseEntity.ok(Map.of(
                "cajaId", cajaId,
                "totalVentas", totalVentas
        ));
    }

    @PostMapping("/{id}/anular")
    public ResponseEntity<?> anularVenta(@PathVariable Long id, @RequestBody AnularVentaRequest request) {
        try {
            VentasEntity ventaAnulada = ventaService.anularVenta(id, request.getMotivo(), request.getUsuario());
            VentaResponseDTO response = ventaService.convertToVentaResponseDTO(ventaAnulada);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al anular la venta: " + e.getMessage());
        }
    }

}
