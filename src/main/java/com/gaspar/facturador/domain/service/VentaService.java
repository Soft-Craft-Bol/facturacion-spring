package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.crud.VentaCrudRepository;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.entity.UserEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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

        UserEntity vendedor = userRepository.findById(Long.valueOf(request.getUser_id()))
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + request.getUser_id() + " no encontrado"));

        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(Math.toIntExact(request.getIdPuntoVenta()))
                .orElseThrow(() -> new IllegalArgumentException("Punto de venta con ID " + request.getIdPuntoVenta() + " no encontrado"));

        ItemCrudRepository producto = itemCrudRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + 1L + " no encontrado"));

        // Crear la entidad de venta
        VentasEntity venta = new VentasEntity();
        venta.setFecha(new Date());

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
        for (var item : request.getDetalle()) {
            montoTotal = montoTotal.add(item.getCantidad().multiply(BigDecimal.valueOf(10)).subtract(item.getMontoDescuento()));
        }

        venta.setMonto(montoTotal);
        venta.setEstado("COMPLETADO");

        // Guardar la venta
        return ventasRepository.save(venta);
    }

}
