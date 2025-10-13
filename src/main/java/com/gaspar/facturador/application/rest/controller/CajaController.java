package com.gaspar.facturador.application.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaspar.facturador.application.rest.dto.*;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.service.ItemService;
import com.gaspar.facturador.domain.service.VentaService;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    public final ItemService itemService;
    private final AppConfig appConfig;
    @Autowired
    private final ObjectMapper objectMapper;

    @PostMapping("/abrir")
    public ResponseEntity<?> abrirCaja(
            @RequestParam Integer sucursalId,
            @RequestParam Integer puntoVentaId,
            @RequestParam Long usuarioId,
            @RequestParam String turno,
            @RequestParam BigDecimal montoInicial
    ) {
        try {
            // Validar si existe sucursal
            var sucursal = sucursalRepository.findById(sucursalId)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            // Validar si existe usuario
            var usuario = userRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Validar turno
            TurnoTrabajo turnoEnum;
            try {
                turnoEnum = TurnoTrabajo.valueOf(turno.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Turno inválido: " + turno);
            }

            // Validar si el usuario ya tiene una caja abierta
            boolean existeAbierta = cajasRepository.existsBySucursalIdAndTurnoAndUsuarioAperturaIdAndEstado(
                    sucursalId,
                    turnoEnum,
                    usuarioId,
                    "ABIERTA"
            );

            if (existeAbierta) {
                return ResponseEntity.badRequest().body("Ya tienes una caja abierta para ese turno.");
            }

            CajasEntity nuevaCaja = CajasEntity.builder()
                    .nombre("Caja " + turno + " - " + usuario.getFirstName() + " " + usuario.getLastName())
                    .estado("ABIERTA")
                    .turno(Enum.valueOf(com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo.class, turno))
                    .montoInicial(montoInicial)
                    .sucursal(sucursal)
                    .usuarioApertura(usuario)
                    .fechaApertura(LocalDateTime.now())
                    .build();

            cajasRepository.save(nuevaCaja);


            Pageable pageable = PageRequest.of(0, 10000, Sort.by(Sort.Direction.DESC, "cantidad"));
            Page<ProductoSucursalDto> productosPage = itemService.getProductosByPuntoVentaId(
                    puntoVentaId, null, null, null, null, null, pageable);

            List<ProductoSucursalDto> productos = productosPage.getContent();

            // Crear JSON con stock inicial
            Map<String, Object> stockInicial = new HashMap<>();
            stockInicial.put("cajaId", nuevaCaja.getId());
            stockInicial.put("fecha", LocalDateTime.now().toString());
            stockInicial.put("productos", productos);

            // Guardar en archivo
            // Ruta completa del archivo
            String rutaArchivo = appConfig.getPathFiles() + "/stock_inicial/caja_" + nuevaCaja.getId() + ".json";

// Obtener solo la carpeta padre
            java.nio.file.Path dirPath = java.nio.file.Paths.get(rutaArchivo).getParent();

// Crear carpetas si no existen
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }

// Guardar JSON
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            mapper.writeValue(new java.io.File(rutaArchivo), stockInicial);


            return ResponseEntity.ok(Map.of(
                    "mensaje", "Caja abierta exitosamente.",
                    "cajaId", nuevaCaja.getId(),
                    "cajaNombre", nuevaCaja.getNombre(),
                    "stockInicial", rutaArchivo
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al abrir caja: " + e.getMessage());
        }
    }

        @Transactional
    @PostMapping("/cerrar")
    public ResponseEntity<?> cerrarCaja(@RequestBody CierreCajaDTO dto) throws IOException {
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
        String rutaArchivo = appConfig.getPathFiles() + "/stock_inicial/caja_" + caja.getId() + ".json";
            Path path = Path.of(rutaArchivo);
            if (Files.exists(path)) {
                Files.delete(path);
            }
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

    @GetMapping("/{cajaId}")
    public ResponseEntity<?> obtenerStockInicial(@PathVariable Long cajaId) {
        try {
            String rutaArchivo = appConfig.getPathFiles() + "/stock_inicial/caja_" + cajaId + ".json";
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                return ResponseEntity.badRequest().body("No se encontró stock inicial para la caja " + cajaId);
            }

            Map<String, Object> stockInicial = objectMapper.readValue(archivo, Map.class);
            return ResponseEntity.ok(stockInicial);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al leer el stock inicial: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cajaId}")
    public ResponseEntity<?> eliminarStockInicial(@PathVariable Long cajaId) {
        try {
            String rutaArchivo = appConfig.getPathFiles() + "/stock_inicial/caja_" + cajaId + ".json";
            Path path = Path.of(rutaArchivo);

            if (Files.exists(path)) {
                Files.delete(path);
                return ResponseEntity.ok("Stock inicial eliminado correctamente para la caja " + cajaId);
            } else {
                return ResponseEntity.badRequest().body("No existe archivo de stock inicial para la caja " + cajaId);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar stock inicial: " + e.getMessage());
        }
    }

}
