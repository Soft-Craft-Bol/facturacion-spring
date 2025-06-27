package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.response.ClienteFrecuenteDTO;
import com.gaspar.facturador.domain.repository.IClienteRepository;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.dto.VentaFiltroDTO;
import com.gaspar.facturador.persistence.dto.VentaHoyDTO;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

        Long userId = userRepository.findIdByUsername(request.getUsername());
        if (userId == null) {
            throw new IllegalArgumentException("Usuario con username " + request.getUsername() + " no encontrado");
        }

        UserEntity vendedor = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + userId + " no encontrado"));

        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(Math.toIntExact(request.getIdPuntoVenta()))
                .orElseThrow(() -> new IllegalArgumentException("Punto de venta con ID " + request.getIdPuntoVenta() + " no encontrado"));

        VentasEntity venta = new VentasEntity();
        venta.setFecha(new Date());
        ClienteEntity cliente = clienteCrudRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + request.getIdCliente() + " no encontrado"));
        venta.setCliente(cliente);


        try {
            String tipoComprobante = request.getTipoComprobante().toUpperCase();
            System.out.println("Tipo de comprobante recibido: " + tipoComprobante);
            venta.setTipoComprobante(TipoComprobanteEnum.valueOf(tipoComprobante));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de comprobante no válido: " + request.getTipoComprobante());
        }

        if (venta.getTipoComprobante() == null) {
            throw new IllegalArgumentException("El tipo de comprobante no puede ser nulo");
        }

        try {
            venta.setMetodoPago(TipoPagoEnum.valueOf(request.getMetodoPago().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Método de pago no válido: " + request.getMetodoPago());
        }

        venta.setVendedor(vendedor);
        venta.setPuntoVenta(puntoVenta);

        BigDecimal montoTotal = BigDecimal.ZERO;
        List<VentasDetalleEntity> detalles = new ArrayList<>();

        for (var item : request.getDetalle()) {
            SucursalItemEntity sucursalItem = sucursalItemCrudRepository.findBySucursal_IdAndItem_Id(
                            puntoVenta.getSucursal().getId(),
                            (int) item.getIdProducto().longValue())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + item.getIdProducto() + " no encontrado en la sucursal"));

            if (BigDecimal.valueOf(sucursalItem.getCantidad()).compareTo(item.getCantidad()) < 0) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto con ID " + item.getIdProducto());
            }

            // Obtener el precio unitario del ItemEntity
            BigDecimal precioUnitario = sucursalItem.getItem().getPrecioUnitario();
            if (precioUnitario == null) {
                throw new IllegalArgumentException("El producto con ID " + item.getIdProducto() + " no tiene precio unitario definido");
            }

            // Calcular subtotal con el precio real
            BigDecimal subtotal = item.getCantidad().multiply(precioUnitario);
            BigDecimal montoItem = subtotal.subtract(item.getMontoDescuento());

            montoTotal = montoTotal.add(montoItem);

            // Actualizar stock
            sucursalItem.setCantidad(sucursalItem.getCantidad() - item.getCantidad().intValue());
            sucursalItemCrudRepository.save(sucursalItem);

            VentasDetalleEntity detalle = new VentasDetalleEntity();
            detalle.setVenta(venta);
            ItemEntity producto = itemCrudRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + item.getIdProducto() + " no encontrado"));

            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setMontoDescuento(item.getMontoDescuento());
            detalle.setDescripcionProducto(sucursalItem.getItem().getDescripcion());
            detalle.setPrecioUnitario(precioUnitario); // Guardar el precio unitario

            detalles.add(detalle);
        }

        venta.setMonto(montoTotal);
        venta.setEstado("COMPLETADO");
        venta.setDetalles(detalles);

        if (request.getIdfactura() != null) {
            FacturaEntity factura = new FacturaEntity();
            factura.setId(request.getIdfactura());
            venta.setFactura(factura);
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
        Pageable pageable = PageRequest.of(page, size);

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
            // Usar el precio unitario guardado en el detalle
            detalleDTO.setSubTotal(detalle.getCantidad().multiply(detalle.getPrecioUnitario()));
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


    public Page<VentaFiltroDTO> filtrarVentas(
            Long idPuntoVenta,
            LocalDate fecha,
            String estado,
            String productoNombre,
            String nombreCliente,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        // Convertir LocalDate a Date para la consulta
        Date fechaInicio = fecha != null ?
                Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        Date fechaFin = fecha != null ?
                Date.from(fecha.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

        // Consulta con filtros
        Page<VentasEntity> ventasPage = ventasRepository.findByFiltros(
                idPuntoVenta,
                fechaInicio,
                fechaFin,
                estado,
                productoNombre,
                nombreCliente,
                pageable);

        return ventasPage.map(this::mapToVentaFiltroDTO);
    }

    private VentaFiltroDTO mapToVentaFiltroDTO(VentasEntity venta) {
        VentaFiltroDTO dto = new VentaFiltroDTO();
        dto.setIdVenta(venta.getId());
        dto.setFechaInicio(venta.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        // Mapear datos del punto de venta
        if (venta.getPuntoVenta() != null) {
            dto.setIdPuntoVenta(Long.valueOf(venta.getPuntoVenta().getId()));
            dto.setNombrePuntoVenta(venta.getPuntoVenta().getNombre());
            if (venta.getPuntoVenta().getSucursal() != null) {
                dto.setNombreSucursal(venta.getPuntoVenta().getSucursal().getNombre());
            }
        }

        // Mapear datos del vendedor
        if (venta.getVendedor() != null) {
            dto.setIdUsuario(venta.getVendedor().getId());
            dto.setNombreUsuario(venta.getVendedor().getUsername());
        }

        // Mapear datos del cliente
        if (venta.getCliente() != null) {
            dto.setCodigoCliente(venta.getCliente().getCodigoCliente());
            dto.setNombreCliente(venta.getCliente().getNombreRazonSocial());
        }

        dto.setTipoComprobante(venta.getTipoComprobante());
        dto.setMetodoPago(venta.getMetodoPago());
        dto.setEstado(venta.getEstado() != null ? venta.getEstado() :
                (venta.getFactura() != null ? venta.getFactura().getEstado() : null));

        // Mapear detalles
        dto.setDetalles(venta.getDetalles().stream().map(detalle -> {
            VentaFiltroDTO.VentaDetalleDTO detalleDTO = new VentaFiltroDTO.VentaDetalleDTO();
            detalleDTO.setIdProducto(Long.valueOf(detalle.getProducto().getId()));
            detalleDTO.setDescripcionProducto(detalle.getDescripcionProducto());
            detalleDTO.setCantidad(detalle.getCantidad());
            detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleDTO.setMontoDescuento(detalle.getMontoDescuento());
            detalleDTO.setSubTotal(detalle.getCantidad().multiply(detalle.getPrecioUnitario())
                    .subtract(detalle.getMontoDescuento()));
            return detalleDTO;
        }).collect(Collectors.toList()));

        // Calcular monto total
        BigDecimal montoTotal = dto.getDetalles().stream()
                .map(VentaFiltroDTO.VentaDetalleDTO::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setMontoMinimo(montoTotal);
        dto.setMontoMaximo(montoTotal);

        return dto;
    }
}
