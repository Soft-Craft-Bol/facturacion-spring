package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.CajaHistorialResponseDTO;
import com.gaspar.facturador.application.rest.dto.CierreCajaDTO;
import com.gaspar.facturador.application.rest.dto.CierreCajaDetalleResponseDTO;
import com.gaspar.facturador.application.rest.dto.CierreCajaResponseDTO;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cajas")
@RequiredArgsConstructor
public class CajaController {

    private final CajaRepository cajasRepository;
    private final UserRepository userRepository;
    private final SucursalCrudRepository sucursalRepository;
    private final CierreCajaRepository cierreCajaRepository;
    private final MetodoPagoRepository metodoPagoRepository;


    @PostMapping("/abrir")
    public ResponseEntity<?> abrirCaja(
            @RequestParam Integer sucursalId,
            @RequestParam Long usuarioId,
            @RequestParam String turno,
            @RequestParam BigDecimal montoInicial
    ) {
        // Validar si el usuario ya tiene una caja abierta en ese turno y sucursal
        boolean existeAbierta = cajasRepository.existsBySucursalIdAndTurnoAndUsuarioAperturaIdAndEstado(
                sucursalId,
                Enum.valueOf(com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo.class, turno),
                usuarioId,
                "ABIERTA"
        );

        if (existeAbierta) {
            return ResponseEntity.badRequest().body("Ya tienes una caja abierta para ese turno.");
        }

        var sucursal = sucursalRepository.findById(sucursalId).orElseThrow();
        var usuario = userRepository.findById(usuarioId).orElseThrow();

        CajasEntity nuevaCaja = CajasEntity.builder()
                .nombre("Caja " + turno + " - " + usuario.getFirstName() + "-" + usuario.getLastName())
                .estado("ABIERTA")
                .turno(Enum.valueOf(com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo.class, turno))
                .montoInicial(montoInicial)
                .sucursal(sucursal)
                .usuarioApertura(usuario)
                .build();

        cajasRepository.save(nuevaCaja);

        return ResponseEntity.ok("Caja abierta exitosamente.");
    }

    @Transactional
    @PostMapping("/cerrar")
    public ResponseEntity<?> cerrarCaja(@RequestBody CierreCajaDTO dto) {
        CajasEntity caja = cajasRepository.findById(dto.getCajaId())
                .orElseThrow(() -> new RuntimeException("Caja no encontrada."));

        if (!"ABIERTA".equals(caja.getEstado())) {
            return ResponseEntity.badRequest().body("La caja no está abierta.");
        }

        UserEntity usuarioCierre = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // Cerrar caja
        caja.setEstado("CERRADA");
        caja.setFechaCierre(LocalDateTime.now());
        caja.setUsuarioCierre(usuarioCierre);
        cajasRepository.save(caja);

        // Crear cierre
        CierreCajasEnity cierre = new CierreCajasEnity();
        cierre.setCaja(caja);
        cierre.setEfectivoContado(dto.getEfectivoFinal());
        cierre.setTarjetaContado(dto.getBilleteraMovilFinal());
        cierre.setQrContado(dto.getTransferenciaFinal());
        cierre.setTotalGastos(dto.getGastos());
        cierre.setTotalVentas(dto.getTotalSistema());
        cierre.setDiferencia(dto.getDiferencia());
        cierre.setObservaciones(dto.getObservaciones());

        // Agregar detalles
        for (Map.Entry<String, Map<String, BigDecimal>> tipo : dto.getResumenPagos().entrySet()) {
            String tipoFacturacion = tipo.getKey(); // facturacion | sin_facturacion
            for (Map.Entry<String, BigDecimal> metodo : tipo.getValue().entrySet()) {
                String codigoMetodo = metodo.getKey(); // efectivo, tarjeta, etc.
                BigDecimal monto = metodo.getValue();

                if (monto == null || monto.compareTo(BigDecimal.ZERO) == 0) continue;

                MetodoPagoEntity metodoPago = metodoPagoRepository.findByCodigoIgnoreCase(codigoMetodo)
                        .orElseThrow(() -> new RuntimeException("Método de pago no encontrado: " + codigoMetodo));


                CierreCajaDetalleEntity detalle = new CierreCajaDetalleEntity();
                detalle.setMetodoPago(metodoPago);
                detalle.setMontoFacturado("facturacion".equals(tipoFacturacion) ? monto : BigDecimal.ZERO);
                detalle.setMontoSinFactura("sin_facturacion".equals(tipoFacturacion) ? monto : BigDecimal.ZERO);
                detalle.setMontoContado(BigDecimal.ZERO); // se puede usar si se cuenta físicamente por método

                cierre.agregarDetalle(detalle);
            }
        }

        cierreCajaRepository.save(cierre);
        return ResponseEntity.ok("Caja cerrada exitosamente.");
    }

    @GetMapping("/abierta/{usuarioId}")
    public ResponseEntity<?> obtenerCajaAbiertaPorUsuario(@PathVariable Long usuarioId) {
        Optional<CajasEntity> cajaOpt = cajasRepository.findByUsuarioAperturaIdAndEstado(usuarioId, "ABIERTA");

        if (cajaOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensaje", "No tienes caja abierta actualmente."));
        }

        CajasEntity caja = cajaOpt.get();

        Map<String, Object> response = new HashMap<>();
        response.put("id", caja.getId());
        response.put("nombre", caja.getNombre());
        response.put("estado", caja.getEstado());
        response.put("fechaApertura", caja.getFechaApertura());
        response.put("montoInicial", caja.getMontoInicial());
        response.put("turno", caja.getTurno());
        response.put("sucursal", caja.getSucursal().getNombre());

        if (caja.getFechaCierre() != null) {
            response.put("fechaCierre", caja.getFechaCierre());
        }
        if (caja.getUsuarioCierre() != null) {
            response.put("usuarioCierre", caja.getUsuarioCierre().getUsername());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/historial")
    public ResponseEntity<?> obtenerHistorialCajas(Pageable pageable) {
        var cajasPage = cajasRepository.findAll(pageable);

        var dtos = cajasPage.map(caja -> {
            CierreCajasEnity cierre = cierreCajaRepository.findByCaja(caja).orElse(null);

            return CajaHistorialResponseDTO.builder()
                    .id(caja.getId())
                    .nombre(caja.getNombre())
                    .estado(caja.getEstado())
                    .turno(caja.getTurno())
                    .montoInicial(caja.getMontoInicial())
                    .sucursal(caja.getSucursal().getNombre())
                    .usuarioApertura(caja.getUsuarioApertura() != null ? caja.getUsuarioApertura().getUsername() : null)
                    .usuarioCierre(caja.getUsuarioCierre() != null ? caja.getUsuarioCierre().getUsername() : null)
                    .fechaApertura(caja.getFechaApertura())
                    .fechaCierre(caja.getFechaCierre())
                    .cierre(cierre != null ? CierreCajaResponseDTO.builder()
                            .efectivoContado(cierre.getEfectivoContado())
                            .tarjetaContado(cierre.getTarjetaContado())
                            .qrContado(cierre.getQrContado())
                            .totalGastos(cierre.getTotalGastos())
                            .totalVentas(cierre.getTotalVentas())
                            .diferencia(cierre.getDiferencia())
                            .observaciones(cierre.getObservaciones())
                            .detalles(cierre.getDetalles().stream().map(detalle ->
                                    CierreCajaDetalleResponseDTO.builder()
                                            .metodoPago(detalle.getMetodoPago().toString())
                                            .montoFacturado(detalle.getMontoFacturado())
                                            .montoSinFactura(detalle.getMontoSinFactura())
                                            .montoContado(detalle.getMontoContado())
                                            .build()
                            ).toList())
                            .build() : null)
                    .build();
        });

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<?> obtenerHistorialCajasPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            Pageable pageable
    ) {
        Page<CajasEntity> cajasPage;

        if (desde != null && hasta != null) {
            LocalDateTime fechaDesde = LocalDateTime.parse(desde);
            LocalDateTime fechaHasta = LocalDateTime.parse(hasta);
            cajasPage = cajasRepository.findAllByUsuarioAperturaIdAndFechaAperturaBetween(usuarioId, fechaDesde, fechaHasta, pageable);
        } else {
            cajasPage = cajasRepository.findAllByUsuarioAperturaId(usuarioId, pageable);
        }

        var dtos = cajasPage.map(caja -> {
            CierreCajasEnity cierre = cierreCajaRepository.findByCaja(caja).orElse(null);

            return CajaHistorialResponseDTO.builder()
                    .id(caja.getId())
                    .nombre(caja.getNombre())
                    .estado(caja.getEstado())
                    .turno(caja.getTurno())
                    .montoInicial(caja.getMontoInicial())
                    .sucursal(caja.getSucursal().getNombre())
                    .usuarioApertura(caja.getUsuarioApertura() != null ? caja.getUsuarioApertura().getUsername() : null)
                    .usuarioCierre(caja.getUsuarioCierre() != null ? caja.getUsuarioCierre().getUsername() : null)
                    .fechaApertura(caja.getFechaApertura())
                    .fechaCierre(caja.getFechaCierre())
                    .cierre(cierre != null ? CierreCajaResponseDTO.builder()
                            .efectivoContado(cierre.getEfectivoContado())
                            .tarjetaContado(cierre.getTarjetaContado())
                            .qrContado(cierre.getQrContado())
                            .totalGastos(cierre.getTotalGastos())
                            .totalVentas(cierre.getTotalVentas())
                            .diferencia(cierre.getDiferencia())
                            .observaciones(cierre.getObservaciones())
                            .detalles(cierre.getDetalles().stream().map(detalle ->
                                    CierreCajaDetalleResponseDTO.builder()
                                            .montoFacturado(detalle.getMontoFacturado())
                                            .montoSinFactura(detalle.getMontoSinFactura())
                                            .montoContado(detalle.getMontoContado())
                                            .build()
                            ).toList())
                            .build() : null)
                    .build();
        });

        return ResponseEntity.ok(dtos);
    }

}
