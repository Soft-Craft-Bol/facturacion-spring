package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.AbonoRequest;
import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.rest.dto.VentaResponseDTO;
import com.gaspar.facturador.domain.service.PagoPosteriorService;
import com.gaspar.facturador.domain.service.VentaService;
import com.gaspar.facturador.persistence.entity.AbonoCreditoEntity;
import com.gaspar.facturador.persistence.entity.CuentaPorCobrarEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago-posterior")
public class PagoPosteriorController {

    private final PagoPosteriorService pagoPosteriorService;
    private final VentaService ventaService;

    public PagoPosteriorController(PagoPosteriorService pagoPosteriorService, VentaService ventaService) {
        this.pagoPosteriorService = pagoPosteriorService;
        this.ventaService = ventaService;
    }

    @PostMapping("/venta")
    public ResponseEntity<?> crearVentaPagoPosterior(@Valid @RequestBody VentaSinFacturaRequest request) {
        try {
            if (!Boolean.TRUE.equals(request.getEsPagoPosterior())) {
                return ResponseEntity.badRequest().body("Esta ruta es solo para ventas a posterior");
            }

            VentasEntity venta = pagoPosteriorService.crearVentaPagoPosterior(request);
            VentaResponseDTO response = ventaService.convertToVentaResponseDTO(venta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{ventaId}/abonar")
    public ResponseEntity<?> registrarAbono(
            @PathVariable Long ventaId,
            @RequestBody AbonoRequest abonoRequest) {

        try {
            AbonoCreditoEntity abono = pagoPosteriorService.registrarAbonoPagoPosterior(
                    ventaId,
                    abonoRequest.getMontoAbono(),
                    abonoRequest.getMetodoPago(),
                    abonoRequest.getReferencia(),
                    abonoRequest.getCajaId(),
                    abonoRequest.getUsuario()
            );

            return ResponseEntity.ok(abono);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<CuentaPorCobrarEntity>> obtenerCuentasPendientes() {
        List<CuentaPorCobrarEntity> cuentas = pagoPosteriorService.obtenerCuentasPagoPosteriorPendientes();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaPorCobrarEntity>> obtenerCuentasPorCliente(
            @PathVariable Integer clienteId) {
        List<CuentaPorCobrarEntity> cuentas = pagoPosteriorService.obtenerCuentasPagoPosteriorPorCliente(clienteId);
        return ResponseEntity.ok(cuentas);
    }
}
