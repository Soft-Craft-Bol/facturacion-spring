package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.crud.VentaCrudRepository;
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

    public VentaService(VentaCrudRepository ventasRepository, UserRepository userRepository, PuntoVentaRepository puntoVentaRepository, ItemCrudRepository itemCrudRepository, SucursalItemCrudRepository sucursalItemCrudRepository){
        this.ventasRepository = ventasRepository;
        this.userRepository = userRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.itemCrudRepository = itemCrudRepository;
        this.sucursalItemCrudRepository=sucursalItemCrudRepository;
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
        venta.setCliente(request.getCliente());

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
            SucursalItemEntity sucursalItem = sucursalItemCrudRepository.findBySucursalIdAndItemId(puntoVenta.getSucursal().getId(), (int) item.getIdProducto().longValue())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + item.getIdProducto() + " no encontrado en la sucursal"));

            if (BigDecimal.valueOf(sucursalItem.getCantidad()).compareTo(item.getCantidad()) < 0) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto con ID " + item.getIdProducto());
            }

            sucursalItem.setCantidad(sucursalItem.getCantidad() - item.getCantidad().intValue());
            sucursalItemCrudRepository.save(sucursalItem);

            montoTotal = montoTotal.add(item.getCantidad().multiply(BigDecimal.valueOf(10)).subtract(item.getMontoDescuento()));

            VentasDetalleEntity detalle = new VentasDetalleEntity();
            detalle.setVenta(venta);
            detalle.setIdProducto(item.getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setMontoDescuento(item.getMontoDescuento());
            detalle.setDescripcionProducto(sucursalItem.getItem().getDescripcion());

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

    public Page<VentaHoyDTO> getVentasDeHoy(int page, int size) {
        LocalDate hoy = LocalDate.now();
        Pageable pageable = PageRequest.of(page, size);
        Page<VentasEntity> ventasPage = ventasRepository.findByFecha(hoy, pageable);

        return ventasPage.map(this::mapToVentaHoyDTO);
    }

    private VentaHoyDTO mapToVentaHoyDTO(VentasEntity venta) {
        VentaHoyDTO dto = new VentaHoyDTO();
        dto.setIdVenta(venta.getId());
        dto.setCodigoCliente(venta.getCliente());
        dto.setNombreRazonSocial(venta.getCliente()); // Asumiendo que el cliente es la razón social

        // Convertir java.util.Date a LocalDateTime
        Date fechaVenta = venta.getFecha();
        LocalDateTime fechaEmision = fechaVenta.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        dto.setFechaEmision(fechaEmision);

        // Priorizar el estado de la factura si existe
        if (venta.getFactura() != null) {
            dto.setEstado(venta.getFactura().getEstado()); // Estado de la factura
            dto.setCuf(venta.getFactura().getCuf()); // CUF de la factura
        } else {
            dto.setEstado(venta.getEstado()); // Estado de la venta
        }

        dto.setTipoComprobante(venta.getTipoComprobante());

        // Mapear detalles de la venta
        dto.setDetalles(venta.getDetalles().stream().map(detalle -> {
            VentaHoyDTO.VentaDetalleDTO detalleDTO = new VentaHoyDTO.VentaDetalleDTO();
            detalleDTO.setDescripcion(detalle.getDescripcionProducto());
            detalleDTO.setSubTotal(detalle.getCantidad().multiply(BigDecimal.valueOf(10))); // Asumiendo un precio unitario de 10
            return detalleDTO;
        }).collect(Collectors.toList()));

        // Mapear información del punto de venta, sucursal y vendedor
        dto.setNombrePuntoVenta(venta.getPuntoVenta().getNombre());
        dto.setIdPuntoVenta(venta.getPuntoVenta().getId());
        dto.setNombreSucursal(venta.getPuntoVenta().getSucursal().getNombre());
        dto.setIdUsuario(venta.getVendedor().getId());
        dto.setNombreUsuario(venta.getVendedor().getUsername());

        return dto;
    }

}
