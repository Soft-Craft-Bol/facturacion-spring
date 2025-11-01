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
import java.util.*;
import java.util.stream.Collectors;

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
            var sucursal = sucursalRepository.findById(sucursalId)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            var usuario = userRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            TurnoTrabajo turnoEnum;
            try {
                turnoEnum = TurnoTrabajo.valueOf(turno.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Turno inválido: " + turno);
            }

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

            Map<String, Object> stockInicial = new HashMap<>();
            stockInicial.put("cajaId", nuevaCaja.getId());
            stockInicial.put("fecha", LocalDateTime.now().toString());
            stockInicial.put("productos", productos);

            // Guardar en archivo
            // Ruta completa del archivo
            String fechaActual = LocalDateTime.now().toString().replace(":", "-");
            String rutaArchivo = appConfig.getPathFiles() + "/stock_inicial/caja_"
                    + nuevaCaja.getId() + "_" + fechaActual + ".json";


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

        String carpeta = appConfig.getPathFiles() + "/stock_inicial/";
        File dir = new File(carpeta);
        File[] archivos = dir.listFiles((d, name) -> name.startsWith("caja_" + caja.getId() + "_") && name.endsWith(".json"));

        if (archivos != null && archivos.length > 0) {
            File archivoMasReciente = archivos[0];
            for (File f : archivos) {
                if (f.lastModified() > archivoMasReciente.lastModified()) {
                    archivoMasReciente = f;
                }
            }

            // Obtener stock final actual
            Pageable pageable = PageRequest.of(0, 10000, Sort.by(Sort.Direction.DESC, "cantidad"));
            Page<ProductoSucursalDto> productosFinalesPage = itemService.getProductosByPuntoVentaId(
                    dto.getPuntoVenta(), null, null, null, null, null, pageable);
            List<Map<String, Object>> stockFinal = new ArrayList<>();

            for (ProductoSucursalDto p : productosFinalesPage.getContent()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", p.getId());
                item.put("codigo", p.getCodigo());
                item.put("descripcion", p.getDescripcion());
                item.put("cantidadDisponible", p.getCantidadDisponible());
                stockFinal.add(item);
            }

            Map<String, Object> data = objectMapper.readValue(archivoMasReciente, Map.class);
            data.put("stock_final", stockFinal);

            objectMapper.writeValue(archivoMasReciente, data);
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
                                            .metodoPago(detalle.getMetodoPago().getDescripcion())
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
                                            .metodoPago(detalle.getMetodoPago().getDescripcion())
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

    @GetMapping("/stock-inicial/{cajaId}")
    public ResponseEntity<?> obtenerStockInicial(@PathVariable Long cajaId) {
        try {
            String carpeta = appConfig.getPathFiles() + "/stock_inicial/";
            File dir = new File(carpeta);

            if (!dir.exists() || !dir.isDirectory()) {
                return ResponseEntity.badRequest().body("No existe la carpeta de stock inicial.");
            }

            // Buscar todos los archivos que empiecen con "caja_ID_"
            File[] archivos = dir.listFiles((d, name) -> name.startsWith("caja_" + cajaId + "_") && name.endsWith(".json"));

            if (archivos == null || archivos.length == 0) {
                return ResponseEntity.badRequest().body("No se encontró stock inicial para la caja " + cajaId);
            }

            // Obtener el más reciente (por fecha de modificación)
            File archivoMasReciente = archivos[0];
            for (File f : archivos) {
                if (f.lastModified() > archivoMasReciente.lastModified()) {
                    archivoMasReciente = f;
                }
            }

            // Leer el archivo JSON
            Map<String, Object> stockInicial = objectMapper.readValue(archivoMasReciente, Map.class);
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


    @GetMapping("/resumen/{cajaId}")
    public ResponseEntity<?> obtenerCajaCompleta(@PathVariable Long cajaId) {
        try {
            // Obtener información básica de la caja
            CajasEntity caja = cajasRepository.findById(cajaId)
                    .orElseThrow(() -> new RuntimeException("Caja no encontrada con ID: " + cajaId));

            // Obtener información de cierre si existe
            Optional<CierreCajasEnity> cierreOpt = cierreCajaRepository.findByCaja(caja);

            // Obtener stock inicial/final del archivo JSON
            Map<String, Object> stockData = obtenerStockData(cajaId);

            // Construir respuesta completa
            Map<String, Object> response = construirRespuestaCompleta(caja, cierreOpt, stockData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener detalles de la caja: " + e.getMessage());
        }
    }

    private Map<String, Object> obtenerStockData(Long cajaId) {
        try {
            String carpeta = appConfig.getPathFiles() + "/stock_inicial/";
            File dir = new File(carpeta);

            if (!dir.exists() || !dir.isDirectory()) {
                return new HashMap<>();
            }

            File[] archivos = dir.listFiles((d, name) -> name.startsWith("caja_" + cajaId + "_") && name.endsWith(".json"));

            if (archivos == null || archivos.length == 0) {
                return new HashMap<>();
            }

            // Obtener el archivo más reciente
            File archivoMasReciente = archivos[0];
            for (File f : archivos) {
                if (f.lastModified() > archivoMasReciente.lastModified()) {
                    archivoMasReciente = f;
                }
            }

            return objectMapper.readValue(archivoMasReciente, Map.class);

        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private Map<String, Object> construirRespuestaCompleta(CajasEntity caja,
                                                           Optional<CierreCajasEnity> cierreOpt,
                                                           Map<String, Object> stockData) {
        Map<String, Object> response = new LinkedHashMap<>();

        // Información básica de la caja
        response.put("id", caja.getId());
        response.put("nombre", caja.getNombre());
        response.put("estado", caja.getEstado());
        response.put("turno", caja.getTurno());
        response.put("montoInicial", caja.getMontoInicial());
        response.put("sucursal", caja.getSucursal().getNombre());
        response.put("usuarioApertura", caja.getUsuarioApertura().getUsername());
        response.put("fechaApertura", caja.getFechaApertura());

        if (caja.getUsuarioCierre() != null) {
            response.put("usuarioCierre", caja.getUsuarioCierre().getUsername());
        }
        if (caja.getFechaCierre() != null) {
            response.put("fechaCierre", caja.getFechaCierre());
        }

        // Información de cierre
        if (cierreOpt.isPresent()) {
            CierreCajasEnity cierre = cierreOpt.get();
            Map<String, Object> cierreMap = new LinkedHashMap<>();
            cierreMap.put("efectivoContado", cierre.getEfectivoContado());
            cierreMap.put("tarjetaContado", cierre.getTarjetaContado());
            cierreMap.put("qrContado", cierre.getQrContado());
            cierreMap.put("totalVentas", cierre.getTotalVentas());
            cierreMap.put("totalGastos", cierre.getTotalGastos());
            cierreMap.put("diferencia", cierre.getDiferencia());
            cierreMap.put("observaciones", cierre.getObservaciones());

            // Detalles del cierre
            List<Map<String, Object>> detalles = cierre.getDetalles().stream()
                    .map(detalle -> {
                        Map<String, Object> detalleMap = new LinkedHashMap<>();
                        detalleMap.put("metodoPago", detalle.getMetodoPago().getDescripcion());
                        detalleMap.put("montoFacturado", detalle.getMontoFacturado());
                        detalleMap.put("montoSinFactura", detalle.getMontoSinFactura());
                        detalleMap.put("montoContado", detalle.getMontoContado());
                        return detalleMap;
                    })
                    .collect(Collectors.toList());

            cierreMap.put("detalles", detalles);
            response.put("cierre", cierreMap);
        }

        // Información de productos y stock del archivo JSON
        if (!stockData.isEmpty()) {
            // Productos (stock inicial)
            if (stockData.containsKey("productos")) {
                response.put("productos", stockData.get("productos"));
            }

            // Stock final
            if (stockData.containsKey("stock_final")) {
                response.put("stock_final", stockData.get("stock_final"));
            }

            // Información adicional del archivo si existe
            if (stockData.containsKey("cajaId")) {
                response.put("cajaIdArchivo", stockData.get("cajaId"));
            }
            if (stockData.containsKey("fecha")) {
                response.put("fechaStockInicial", stockData.get("fecha"));
            }
        }

        return response;
    }
}
