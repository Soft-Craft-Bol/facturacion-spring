package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.crud.VentaCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VentaService {
    private final VentaCrudRepository ventasRepository;
    private final UserRepository userRepository;
    private final PuntoVentaRepository puntoVentaRepository;
    private final ItemCrudRepository itemCrudRepository;

    public VentaService(VentaCrudRepository ventasRepository, UserRepository userRepository, PuntoVentaRepository puntoVentaRepository, ItemCrudRepository itemCrudRepository) {
        this.ventasRepository = ventasRepository;
        this.userRepository = userRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.itemCrudRepository = itemCrudRepository;
    }

    @Transactional
    public VentasEntity saveVenta(VentaSinFacturaRequest request) {
        System.out.println("Body recibido: " + request);

        // Obtener el ID del usuario por su username
        Long userId = userRepository.findIdByUsername(request.getUsername());
        if (userId == null) {
            throw new IllegalArgumentException("Usuario con username " + request.getUsername() + " no encontrado");
        }

        // Obtener el vendedor por su ID
        UserEntity vendedor = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + userId + " no encontrado"));

        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(Math.toIntExact(request.getIdPuntoVenta()))
                .orElseThrow(() -> new IllegalArgumentException("Punto de venta con ID " + request.getIdPuntoVenta() + " no encontrado"));

        // Crear la entidad de venta
        VentasEntity venta = new VentasEntity();
        venta.setFecha(new Date());

        // Asignar el cliente
        venta.setCliente(request.getCliente());

        // Manejo de comprobante
        try {
            String tipoComprobante = request.getTipoComprobante().toUpperCase();
            System.out.println("Tipo de comprobante recibido: " + tipoComprobante);
            venta.setTipoComprobante(TipoComprobanteEnum.valueOf(tipoComprobante));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de comprobante no válido: " + request.getTipoComprobante());
        }

        // Verificar que el tipo de comprobante no sea nulo
        if (venta.getTipoComprobante() == null) {
            throw new IllegalArgumentException("El tipo de comprobante no puede ser nulo");
        }

        // Manejo de método de pago
        try {
            venta.setMetodoPago(TipoPagoEnum.valueOf(request.getMetodoPago().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Método de pago no válido: " + request.getMetodoPago());
        }

        venta.setVendedor(vendedor);
        venta.setPuntoVenta(puntoVenta);

        // Calcular monto total
        BigDecimal montoTotal = BigDecimal.ZERO;
        List<VentasDetalleEntity> detalles = new ArrayList<>();
        for (var item : request.getDetalle()) {
            // Obtener el producto (ItemEntity) por su ID
            ItemEntity producto = itemCrudRepository.findById((int) item.getIdProducto().longValue())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + item.getIdProducto() + " no encontrado"));

            // Verificar si hay suficiente stock
            if (producto.getCantidad().compareTo(item.getCantidad()) < 0) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto con ID " + item.getIdProducto());
            }

            // Restar la cantidad vendida del stock del producto
            producto.setCantidad(producto.getCantidad().subtract(item.getCantidad()));

            // Guardar el producto actualizado en la base de datos
            itemCrudRepository.save(producto);

            // Calcular el monto total
            montoTotal = montoTotal.add(item.getCantidad().multiply(BigDecimal.valueOf(10)).subtract(item.getMontoDescuento()));

            // Crear el detalle de la venta
            VentasDetalleEntity detalle = new VentasDetalleEntity();
            detalle.setVenta(venta);
            detalle.setIdProducto(item.getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setMontoDescuento(item.getMontoDescuento());
            detalle.setDescripcionProducto(producto.getDescripcion());

            detalles.add(detalle);
        }

        venta.setMonto(montoTotal);
        venta.setEstado("COMPLETADO");
        venta.setDetalles(detalles);

        // Guardar la venta
        return ventasRepository.save(venta);
    }

}
