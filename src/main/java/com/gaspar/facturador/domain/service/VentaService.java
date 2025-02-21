package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.VentaCrudRepository;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.request.VentaDetalleRequest;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaCrudRepository ventaCrudRepository;
    private final ItemCrudRepository itemCrudRepository;

    @Autowired
    public VentaService(VentaCrudRepository ventaCrudRepository, ItemCrudRepository itemCrudRepository) {
        this.ventaCrudRepository = ventaCrudRepository;
        this.itemCrudRepository = itemCrudRepository;
    }

    public List<VentasEntity> getAllVentas() {
        return (List<VentasEntity>) ventaCrudRepository.findAll();
    }

    public Optional<VentasEntity> getVentaById(Long id) {
        return ventaCrudRepository.findById(id);
    }

    @Transactional
    public VentasEntity saveVenta(VentaSinFacturaRequest ventaRequest) {
        VentasEntity nuevaVenta = new VentasEntity();
        nuevaVenta.setCliente(ventaRequest.getUsuario());
        nuevaVenta.setTipoComprobante(TipoComprobanteEnum.valueOf(ventaRequest.getTipoComprobante()));

        // Asignar el user_id de la solicitud al campo correspondiente en la entidad
        nuevaVenta.setUserId(ventaRequest.getUser_id());

        BigDecimal totalMonto = BigDecimal.ZERO;

        for (VentaDetalleRequest detalle : ventaRequest.getDetalle()) {
            Optional<ItemEntity> itemOpt = itemCrudRepository.findById(detalle.getIdProducto());
            if (itemOpt.isPresent()) {
                ItemEntity item = itemOpt.get();

                // Verifica si hay stock suficiente
                BigDecimal cantidadDetalle = detalle.getCantidad();

                if (item.getCantidad().compareTo(cantidadDetalle) < 0) {
                    throw new IllegalArgumentException("Stock insuficiente para el producto ID: " + detalle.getIdProducto());
                }

                // Resta la cantidad del stock del producto
                item.setCantidad(item.getCantidad().subtract(cantidadDetalle));
                itemCrudRepository.save(item);

                // Calcula el monto de la venta
                BigDecimal monto = item.getPrecioUnitario().multiply(cantidadDetalle).subtract(detalle.getMontoDescuento());
                totalMonto = totalMonto.add(monto);
            } else {
                throw new IllegalArgumentException("Producto no encontrado: ID " + detalle.getIdProducto());
            }
        }

        nuevaVenta.setMonto(totalMonto);
        return ventaCrudRepository.save(nuevaVenta);
    }




    public void deleteVenta(Long id) {
        ventaCrudRepository.deleteById(id);
    }

    public List<VentasEntity> getVentasByTipoComprobante(String tipoComprobante) {
        return ventaCrudRepository.findByTipoComprobante(tipoComprobante);
    }
}
