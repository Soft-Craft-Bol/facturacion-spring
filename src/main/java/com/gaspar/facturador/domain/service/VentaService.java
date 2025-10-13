package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.MetodoPagoRequest;
import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.response.ClienteFrecuenteDTO;
import com.gaspar.facturador.application.rest.dto.*;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.dto.VentaHoyDTO;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import com.gaspar.facturador.persistence.specification.VentaSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaService {
    private final VentaCrudRepository ventasRepository;
    private final UserRepository userRepository;
    private final PuntoVentaRepository puntoVentaRepository;
    private final ItemCrudRepository itemCrudRepository;
    private final SucursalItemCrudRepository sucursalItemCrudRepository;
    private final ClienteCrudRepository clienteCrudRepository;

    public VentaService(VentaCrudRepository ventasRepository, UserRepository userRepository, PuntoVentaRepository puntoVentaRepository, ItemCrudRepository itemCrudRepository, SucursalItemCrudRepository sucursalItemCrudRepository, ClienteCrudRepository clienteCrudRepository){
        this.ventasRepository = ventasRepository;
        this.userRepository = userRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.itemCrudRepository = itemCrudRepository;
        this.sucursalItemCrudRepository=sucursalItemCrudRepository;
        this.clienteCrudRepository = clienteCrudRepository;
    }

    @Transactional
    public VentasEntity saveVenta(VentaSinFacturaRequest request) {
        System.out.println("Body recibido: " + request);

        // Buscar usuario
        Long userId = userRepository.findIdByUsername(request.getUsername());
        if (userId == null) {
            throw new IllegalArgumentException("Usuario con username " + request.getUsername() + " no encontrado");
        }

        UserEntity vendedor = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + userId + " no encontrado"));

        // Buscar punto de venta
        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(Math.toIntExact(request.getIdPuntoVenta()))
                .orElseThrow(() -> new IllegalArgumentException("Punto de venta con ID " + request.getIdPuntoVenta() + " no encontrado"));

        // Validar métodos de pago combinados
        validarMetodosPago(request);

        // Crear venta
        VentasEntity venta = new VentasEntity();
        venta.setFecha(new Date());

        // Caja
        CajasEntity caja = new CajasEntity();
        caja.setId(request.getCajaId());
        venta.setCaja(caja);

        // Cliente
        ClienteEntity cliente = clienteCrudRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + request.getIdCliente() + " no encontrado"));
        venta.setCliente(cliente);

        // Tipo comprobante
        try {
            String tipoComprobante = request.getTipoComprobante().toUpperCase();
            System.out.println("Tipo de comprobante recibido: " + tipoComprobante);
            venta.setTipoComprobante(TipoComprobanteEnum.valueOf(tipoComprobante));
        } catch (Exception e) {
            throw new IllegalArgumentException("Tipo de comprobante no válido: " + request.getTipoComprobante());
        }

        // Método de pago (general, si no usa múltiples métodos)
        if (request.getMetodoPago() != null) {
            try {
                venta.setMetodoPago(TipoPagoEnum.valueOf(request.getMetodoPago().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Método de pago no válido: " + request.getMetodoPago());
            }
        }

        venta.setVendedor(vendedor);
        venta.setPuntoVenta(puntoVenta);

        // --- DETALLES ---
        BigDecimal montoTotal = BigDecimal.ZERO;
        List<VentasDetalleEntity> detalles = new ArrayList<>();

        for (var item : request.getDetalle()) {
            // Buscar stock en sucursal
            SucursalItemEntity sucursalItem = sucursalItemCrudRepository.findBySucursal_IdAndItem_Id(
                            puntoVenta.getSucursal().getId(),
                            item.getIdProducto().intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + item.getIdProducto() + " no encontrado en la sucursal"));

            // Validar stock
            if (BigDecimal.valueOf(sucursalItem.getCantidad()).compareTo(item.getCantidad()) < 0) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto con ID " + item.getIdProducto());
            }

            // Precio unitario
            BigDecimal precioUnitario = sucursalItem.getItem().getPrecioUnitario();
            if (precioUnitario == null) {
                throw new IllegalArgumentException("El producto con ID " + item.getIdProducto() + " no tiene precio unitario definido");
            }

            // Subtotal
            BigDecimal subtotal = item.getCantidad().multiply(precioUnitario);
            BigDecimal montoItem = subtotal.subtract(item.getMontoDescuento());
            montoTotal = montoTotal.add(montoItem);

            // Actualizar stock
            sucursalItem.setCantidad(sucursalItem.getCantidad() - item.getCantidad().intValue());
            sucursalItemCrudRepository.save(sucursalItem);

            // Crear detalle
            ItemEntity producto = itemCrudRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + item.getIdProducto() + " no encontrado"));

            VentasDetalleEntity detalle = new VentasDetalleEntity();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setMontoDescuento(item.getMontoDescuento());
            detalle.setDescripcionProducto(sucursalItem.getItem().getDescripcion());
            detalle.setPrecioUnitario(precioUnitario);

            detalles.add(detalle);
        }

        venta.setDetalles(detalles);
        venta.setMonto(montoTotal);

        // --- PAGOS ---
        if (request.getMetodosPago() != null && !request.getMetodosPago().isEmpty()) {
            List<VentaPagoEntity> metodosPagoEntities = new ArrayList<>();

            for (MetodoPagoRequest metodoRequest : request.getMetodosPago()) {
                VentaPagoEntity metodoPago = new VentaPagoEntity();
                metodoPago.setVenta(venta);
                metodoPago.setMetodoPago(metodoRequest.getMetodoPago());
                metodoPago.setMonto(metodoRequest.getMonto());
                metodoPago.setReferencia(metodoRequest.getReferencia());
                metodoPago.setEntidadBancaria(metodoRequest.getEntidadBancaria());
                metodosPagoEntities.add(metodoPago);
            }

            venta.setMetodosPago(metodosPagoEntities);

            BigDecimal totalPagado = request.getMetodosPago().stream()
                    .map(MetodoPagoRequest::getMonto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            venta.setMontoRecibido(totalPagado);
            venta.setMontoDevuelto(totalPagado.subtract(montoTotal).max(BigDecimal.ZERO));
        } else {
            venta.setMontoRecibido(request.getMontoRecibido());
            venta.setMontoDevuelto(request.getMontoDevuelto());
        }

        // --- FACTURA ---
        if (request.getIdfactura() != null) {
            FacturaEntity factura = new FacturaEntity();
            factura.setId(request.getIdfactura());
            venta.setFactura(factura);
        }

        // --- CRÉDITO ---
        if (Boolean.TRUE.equals(request.getEsCredito())) {
            if (!cliente.getPermiteCredito() || cliente.getCredito() == null) {
                throw new IllegalArgumentException("El cliente no tiene crédito habilitado");
            }

            BigDecimal creditoDisponible = cliente.getCredito().getCreditoDisponible();
            if (creditoDisponible.compareTo(montoTotal) < 0) {
                throw new IllegalArgumentException("El cliente no tiene suficiente crédito disponible");
            }

            cliente.getCredito().setCreditoDisponible(creditoDisponible.subtract(montoTotal));

            CuentaPorCobrarEntity cxc = new CuentaPorCobrarEntity();
            cxc.setVenta(venta);
            cxc.setCliente(cliente);
            cxc.setMontoTotal(montoTotal);
            cxc.setSaldoPendiente(montoTotal);
            cxc.setFechaEmision(new Date());
            cxc.setDiasCredito(request.getDiasCredito());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, request.getDiasCredito());
            cxc.setFechaVencimiento(calendar.getTime());
            cxc.setEstado("PENDIENTE");

            venta.setEsCredito(true);
            venta.setDiasCredito(request.getDiasCredito());
            venta.setCuentaPorCobrar(cxc);
            venta.setEstado("PENDIENTE");
        } else {
            venta.setEsCredito(false);
            venta.setEstado("COMPLETADO");
        }

        return ventasRepository.save(venta);
    }

    public Page<VentaHoyDTO> getVentasDeHoy(
            int page,
            int size,
            Integer idPuntoVenta,
            String estado,
            String busqueda,
            String tipoBusqueda) {

        LocalDate hoy = LocalDate.now();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecha"));

        Page<VentasEntity> ventasPage = ventasRepository.findByFechaAndFiltros(
                hoy, idPuntoVenta, estado, busqueda, tipoBusqueda, pageable);

        return ventasPage.map(this::mapToVentaHoyDTO);
    }


    private VentaHoyDTO mapToVentaHoyDTO(VentasEntity venta) {
        VentaHoyDTO dto = new VentaHoyDTO();
        dto.setIdVenta(venta.getId());
        ClienteEntity cliente = venta.getCliente();
        if (cliente != null) {
            dto.setCodigoCliente(cliente.getCodigoCliente());
            dto.setNombreRazonSocial(cliente.getNombreRazonSocial());
        }

        Date fechaVenta = venta.getFecha();
        LocalDateTime fechaEmision = fechaVenta.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        dto.setFechaEmision(fechaEmision);

        if (venta.getFactura() != null) {
            dto.setEstado(venta.getFactura().getEstado());
            dto.setCuf(venta.getFactura().getCuf());
        } else {
            dto.setEstado(venta.getEstado());
        }

        dto.setTipoComprobante(venta.getTipoComprobante());

        dto.setDetalles(venta.getDetalles().stream().map(detalle -> {
            VentaHoyDTO.VentaDetalleDTO detalleDTO = new VentaHoyDTO.VentaDetalleDTO();
            detalleDTO.setDescripcion(detalle.getDescripcionProducto());

            // Calcular precio con descuento
            BigDecimal precioConDescuento = detalle.getPrecioUnitario();
            if (detalle.getMontoDescuento() != null && detalle.getMontoDescuento().compareTo(BigDecimal.ZERO) > 0) {
                precioConDescuento = detalle.getPrecioUnitario().subtract(detalle.getMontoDescuento());
            }

            // Calcular subtotal
            detalleDTO.setSubTotal(detalle.getCantidad().multiply(precioConDescuento));
            return detalleDTO;
        }).collect(Collectors.toList()));

        dto.setNombrePuntoVenta(venta.getPuntoVenta().getNombre());
        dto.setIdPuntoVenta(venta.getPuntoVenta().getId());
        dto.setNombreSucursal(venta.getPuntoVenta().getSucursal().getNombre());
        dto.setIdUsuario(venta.getVendedor().getId());
        dto.setNombreUsuario(venta.getVendedor().getUsername());

        return dto;
    }

    public List<TotalVentasPorDiaDTO> obtenerTotalesVentasPorDia() {
        return ventasRepository.findVentasAgrupadasPorDia();
    }
    public List<ClienteFrecuenteDTO> obtenerTop10ClientesFrecuentes() {
        return ventasRepository.findTop10ClientesFrecuentes();
    }

    public Map<String, Object> obtenerResumenVentasConYsinFacturacion(Long cajaId) {
        List<VentasEntity> ventas = ventasRepository.findByCajaId(cajaId);

        // Definir las categorías principales
        Map<String, BigDecimal> facturacion = new HashMap<>();
        Map<String, BigDecimal> sinFacturacion = new HashMap<>();
        Map<String, BigDecimal> total = new HashMap<>();

        // Inicializar categorías principales
        String[] categoriasPrincipales = {
                "efectivo", "billetera_movil", "tarjeta", "transferencia",
                "pago_online", "gift_card", "otros", "cheque", "vales",
                "debito_automatico", "deposito_en_cuenta", "canal_de_pago"
        };

        for (String categoria : categoriasPrincipales) {
            facturacion.put(categoria, BigDecimal.ZERO);
            sinFacturacion.put(categoria, BigDecimal.ZERO);
            total.put(categoria, BigDecimal.ZERO);
        }

        BigDecimal subtotalFacturacion = BigDecimal.ZERO;
        BigDecimal subtotalSinFacturacion = BigDecimal.ZERO;

        for (VentasEntity venta : ventas) {
            if (venta.getMetodoPago() == null) continue;

            BigDecimal monto = venta.getMonto() != null ? venta.getMonto() : BigDecimal.ZERO;
            String categoriaPrincipal = determinarCategoriaPrincipal(venta.getMetodoPago());

            if (venta.getFactura() != null) {
                facturacion.put(categoriaPrincipal,
                        facturacion.get(categoriaPrincipal).add(monto));
                subtotalFacturacion = subtotalFacturacion.add(monto);
            } else {
                sinFacturacion.put(categoriaPrincipal,
                        sinFacturacion.get(categoriaPrincipal).add(monto));
                subtotalSinFacturacion = subtotalSinFacturacion.add(monto);
            }

            total.put(categoriaPrincipal,
                    total.get(categoriaPrincipal).add(monto));
        }

        BigDecimal totalGeneral = subtotalFacturacion.add(subtotalSinFacturacion);

        // Armar el JSON final
        Map<String, Object> result = new LinkedHashMap<>();

        Map<String, Object> facturacionMap = new LinkedHashMap<>(facturacion);
        facturacionMap.put("subtotal", subtotalFacturacion);

        Map<String, Object> sinFacturacionMap = new LinkedHashMap<>(sinFacturacion);
        sinFacturacionMap.put("subtotal", subtotalSinFacturacion);

        Map<String, Object> totalMap = new LinkedHashMap<>(total);
        totalMap.put("general", totalGeneral);

        result.put("facturacion", replaceZeroWithNull(facturacionMap));
        result.put("sin_facturacion", replaceZeroWithNull(sinFacturacionMap));
        result.put("total", replaceZeroWithNull(totalMap));

        return result;
    }

    private String determinarCategoriaPrincipal(TipoPagoEnum metodoPago) {
        String descripcion = metodoPago.getDescripcion().toLowerCase();

        // Primero verificar los casos más específicos
        if (descripcion.contains("billetera movil") || descripcion.contains("billetera_movil")) {
            return "billetera_movil";
        }
        if (descripcion.contains("transferencia bancaria") || descripcion.contains("transferencia swift")) {
            return "transferencia";
        }
        if (descripcion.contains("pago online") || descripcion.contains("pago_onine")) {
            return "pago_online";
        }
        if (descripcion.contains("gift card") || descripcion.contains("gift-card") || descripcion.contains("gift")) {
            return "gift_card";
        }
        if (descripcion.contains("canal de pago") || descripcion.contains("canal_pago")) {
            return "canal_de_pago";
        }
        if (descripcion.contains("deposito en cuenta") || descripcion.contains("deposito_en_cuenta")) {
            return "deposito_en_cuenta";
        }
        if (descripcion.contains("debito automatico") || descripcion.contains("debito_automatico")) {
            return "debito_automatico";
        }

        // Luego los casos generales
        if (descripcion.contains("efectivo")) {
            return "efectivo";
        }
        if (descripcion.contains("tarjeta")) {
            return "tarjeta";
        }
        if (descripcion.contains("cheque")) {
            return "cheque";
        }
        if (descripcion.contains("vales")) {
            return "vales";
        }

        return "otros";
    }

    // Método auxiliar para convertir ceros en null (se mantiene igual)
    private Map<String, Object> replaceZeroWithNull(Map<String, ?> input) {
        Map<String, Object> cleaned = new LinkedHashMap<>();
        input.forEach((k, v) -> {
            if (v instanceof BigDecimal && ((BigDecimal) v).compareTo(BigDecimal.ZERO) == 0) {
                cleaned.put(k, null);
            } else {
                cleaned.put(k, v);
            }
        });
        return cleaned;
    }

    public Page<VentaListadoDTO> obtenerVentasConFactura(
            LocalDate fechaDesde, LocalDate fechaHasta,
            String estadoFactura, TipoPagoEnum metodoPago,
            String codigoCliente, String codigoProducto,
            BigDecimal montoMin, BigDecimal montoMax,
            Pageable pageable) {

        Specification<VentasEntity> spec = Specification.where(VentaSpecifications.hasFactura())
                .and(VentaSpecifications.withFechaDesde(fechaDesde))
                .and(VentaSpecifications.withFechaHasta(fechaHasta))
                .and(VentaSpecifications.withEstadoFactura(estadoFactura))
                .and(VentaSpecifications.withMetodoPago(metodoPago))
                .and(VentaSpecifications.withCodigoCliente(codigoCliente))
                .and(VentaSpecifications.withCodigoProducto(codigoProducto))
                .and(VentaSpecifications.withMontoMin(montoMin))
                .and(VentaSpecifications.withMontoMax(montoMax));

        Page<VentasEntity> ventasPage = ventasRepository.findAll(spec, pageable);
        return ventasPage.map(this::convertToDTO);
    }

    public List<ReporteProductoDTO> obtenerReporteProductosPorCaja(Long cajaId) {
        return ventasRepository.findProductosVendidosPorCaja(cajaId);
    }

    public BigDecimal obtenerTotalVentasPorCaja(Long cajaId) {
        List<ReporteProductoDTO> reporte = obtenerReporteProductosPorCaja(cajaId);
        return reporte.stream()
                .map(ReporteProductoDTO::totalVenta)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public VentasEntity anularVenta(Long ventaId, String motivo, String usuario) {
        VentasEntity venta = ventasRepository.findById(ventaId)
                .orElseThrow(() -> new IllegalArgumentException("Venta con ID " + ventaId + " no encontrada"));

        if (Boolean.TRUE.equals(venta.getAnulada())) {
            throw new IllegalArgumentException("La venta ya ha sido anulada");
        }

        if (!"COMPLETADO".equals(venta.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden anular ventas con estado COMPLETADO");
        }

        // 1️⃣ Revertir stock
        for (VentasDetalleEntity detalle : venta.getDetalles()) {
            ItemEntity producto = detalle.getProducto();
            Integer sucursalId = venta.getPuntoVenta().getSucursal().getId();

            SucursalItemEntity sucursalItem = sucursalItemCrudRepository
                    .findBySucursal_IdAndItem_Id(sucursalId, Math.toIntExact(producto.getId()))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Producto con ID " + producto.getId() + " no encontrado en la sucursal"));

            sucursalItem.setCantidad(sucursalItem.getCantidad() + detalle.getCantidad().intValue());
            sucursalItemCrudRepository.save(sucursalItem);
        }

        // 2️⃣ Actualizar montos en la venta
        venta.setAnulada(true);
        venta.setMotivoAnulacion(motivo);
        venta.setFechaAnulacion(new Date());
        venta.setUsuarioAnulacion(usuario);
        venta.setEstado("ANULADA");
        venta.setMonto(BigDecimal.ZERO);
        venta.setMontoRecibido(BigDecimal.ZERO);
        venta.setMontoDevuelto(BigDecimal.ZERO);

        return ventasRepository.save(venta);
    }

    private void validarMetodosPago(VentaSinFacturaRequest request) {
        if (request.getMetodosPago() != null && !request.getMetodosPago().isEmpty()) {
            if (request.getMetodosPago().size() > 3) {
                throw new IllegalArgumentException("Máximo 3 métodos de pago permitidos");
            }

            for (MetodoPagoRequest metodo : request.getMetodosPago()) {
                if (metodo.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("El monto debe ser mayor a cero para todos los métodos de pago");
                }
            }

            BigDecimal totalMetodos = request.getMetodosPago().stream()
                    .map(MetodoPagoRequest::getMonto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal montoAproximado = request.getDetalle().stream()
                    .map(detalle -> detalle.getCantidad().multiply(BigDecimal.TEN)) // Precio aproximado de 10
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalMetodos.compareTo(montoAproximado) < 0) {
                throw new IllegalArgumentException("La suma de los métodos de pago parece insuficiente para el monto total de la venta");
            }
        } else if (request.getMetodoPago() == null) {
            throw new IllegalArgumentException("Debe especificar al menos un método de pago");
        }
    }

    private VentaListadoDTO convertToDTO(VentasEntity venta) {
        VentaListadoDTO dto = new VentaListadoDTO();

        // Mapeo de campos básicos
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setMetodoPago(venta.getMetodoPago());
        dto.setMonto(venta.getMonto());
        dto.setEstado(venta.getEstado());
        dto.setTipoComprobante(venta.getTipoComprobante());

        // Mapeo del cliente
        if (venta.getCliente() != null) {
            VentaListadoDTO.ClienteDTO clienteDTO = new VentaListadoDTO.ClienteDTO();
            clienteDTO.setId(venta.getCliente().getId());
            clienteDTO.setNombreRazonSocial(venta.getCliente().getNombreRazonSocial());
            clienteDTO.setCodigoCliente(venta.getCliente().getCodigoCliente());
            clienteDTO.setNumeroDocumento(venta.getCliente().getNumeroDocumento());
            dto.setCliente(clienteDTO);
        }

        // Mapeo de detalles
        if (venta.getDetalles() != null && !venta.getDetalles().isEmpty()) {
            List<VentaListadoDTO.DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
                    .map(detalle -> {
                        VentaListadoDTO.DetalleVentaDTO detalleDTO = new VentaListadoDTO.DetalleVentaDTO();
                        detalleDTO.setId(detalle.getId());
                        detalleDTO.setCantidad(detalle.getCantidad());
                        detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                        detalleDTO.setDescripcionProducto(detalle.getDescripcionProducto());

                        // Mapeo del producto
                        if (detalle.getProducto() != null) {
                            VentaListadoDTO.ProductoDTO productoDTO = new VentaListadoDTO.ProductoDTO();
                            productoDTO.setId(detalle.getProducto().getId());
                            productoDTO.setCodigo(detalle.getProducto().getCodigo());
                            productoDTO.setDescripcion(detalle.getProducto().getDescripcion());
                            productoDTO.setUnidadMedida(detalle.getProducto().getUnidadMedida());
                            productoDTO.setPrecioUnitario(detalle.getProducto().getPrecioUnitario());
                            productoDTO.setImagen(detalle.getProducto().getImagen());
                            detalleDTO.setProducto(productoDTO);
                        }

                        return detalleDTO;
                    })
                    .collect(Collectors.toList());
            dto.setDetalles(detallesDTO);
        }

        // Mapeo del vendedor
        if (venta.getVendedor() != null) {
            VentaListadoDTO.VendedorDTO vendedorDTO = new VentaListadoDTO.VendedorDTO();
            vendedorDTO.setId(venta.getVendedor().getId());
            vendedorDTO.setFirstName(venta.getVendedor().getFirstName());
            vendedorDTO.setLastName(venta.getVendedor().getLastName());
            vendedorDTO.setEmail(venta.getVendedor().getEmail());
            dto.setVendedor(vendedorDTO);
        }

        // Mapeo del punto de venta
        if (venta.getPuntoVenta() != null) {
            VentaListadoDTO.PuntoVentaDTO puntoVentaDTO = new VentaListadoDTO.PuntoVentaDTO();
            puntoVentaDTO.setId(venta.getPuntoVenta().getId());
            puntoVentaDTO.setNombre(venta.getPuntoVenta().getNombre());

            if (venta.getPuntoVenta().getSucursal() != null) {
                VentaListadoDTO.SucursalDTO sucursalDTO = new VentaListadoDTO.SucursalDTO();
                sucursalDTO.setId(venta.getPuntoVenta().getSucursal().getId());
                sucursalDTO.setNombre(venta.getPuntoVenta().getSucursal().getNombre());
                sucursalDTO.setDireccion(venta.getPuntoVenta().getSucursal().getDireccion());
                puntoVentaDTO.setSucursal(sucursalDTO);
            }

            dto.setPuntoVenta(puntoVentaDTO);
        }

        // Mapeo de la factura
        if (venta.getFactura() != null) {
            VentaListadoDTO.FacturaResumenDTO facturaDTO = new VentaListadoDTO.FacturaResumenDTO();
            facturaDTO.setId(venta.getFactura().getId());
            facturaDTO.setNumeroFactura(venta.getFactura().getNumeroFactura().intValue());
            facturaDTO.setCuf(venta.getFactura().getCuf());
            facturaDTO.setCufd(venta.getFactura().getCufd());
            facturaDTO.setEstado(venta.getFactura().getEstado());
            facturaDTO.setMontoTotal(venta.getFactura().getMontoTotal());
            dto.setFactura(facturaDTO);
        }

        return dto;
    }

    public VentaResponseDTO convertToVentaResponseDTO(VentasEntity venta) {
        VentaResponseDTO dto = new VentaResponseDTO();

        // Mapeo de campos básicos
        dto.setIdVenta(venta.getId());
        dto.setEstado(venta.getEstado());
        dto.setFechaEmision(venta.getFecha().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        dto.setMontoTotal(venta.getMonto());
        dto.setMetodoPago(venta.getMetodoPago().name());

        // Datos del cliente
        if (venta.getCliente() != null) {
            dto.setNombreCliente(venta.getCliente().getNombreRazonSocial());
        } else {
            dto.setNombreCliente("CONSUMIDOR FINAL");
        }

        // Mapeo de detalles
        dto.setDetalles(venta.getDetalles().stream().map(detalle -> {
            VentaResponseDTO.DetalleVentaDTO detalleDTO = new VentaResponseDTO.DetalleVentaDTO();
            detalleDTO.setDescripcionProducto(detalle.getDescripcionProducto());
            detalleDTO.setCantidad(detalle.getCantidad());
            detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleDTO.setDescuento(detalle.getMontoDescuento());

            // Calcular subtotal (precioUnitario * cantidad - descuento)
            BigDecimal subtotal = detalle.getPrecioUnitario()
                    .multiply(detalle.getCantidad())
                    .subtract(detalle.getMontoDescuento());
            detalleDTO.setSubtotal(subtotal);

            return detalleDTO;
        }).collect(Collectors.toList()));

        return dto;
    }

    public Page<VentaListadoDTO> obtenerVentasSinFactura(
            LocalDate fechaDesde, LocalDate fechaHasta,
            TipoPagoEnum metodoPago,
            String codigoCliente, String codigoProducto,
            BigDecimal montoMin, BigDecimal montoMax,
            Pageable pageable) {

        Specification<VentasEntity> spec = Specification.where(VentaSpecifications.hasNoFactura())
                .and(VentaSpecifications.withFechaDesde(fechaDesde))
                .and(VentaSpecifications.withFechaHasta(fechaHasta))
                .and(VentaSpecifications.withMetodoPago(metodoPago))
                .and(VentaSpecifications.withCodigoCliente(codigoCliente))
                .and(VentaSpecifications.withCodigoProducto(codigoProducto))
                .and(VentaSpecifications.withMontoMin(montoMin))
                .and(VentaSpecifications.withMontoMax(montoMax));

        Page<VentasEntity> ventasPage = ventasRepository.findAll(spec, pageable);
        return ventasPage.map(this::convertToDTO);
    }
}
