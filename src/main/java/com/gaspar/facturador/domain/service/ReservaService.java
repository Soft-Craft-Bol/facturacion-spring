package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.crud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaCrudRepository reservaRepository;

    @Autowired
    private PuntoVentaCrudRepository puntoVentaRepository;

    @Autowired
    private ClienteCrudRepository clienteRepository;

    @Autowired
    private ItemCrudRepository itemRepository;

    @Transactional
    public ReservaResponse crearReserva(ReservaRequest request) {
        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(request.getIdPuntoVenta())
                .orElseThrow(() -> new IllegalArgumentException("Punto de venta no encontrado"));
        ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        ReservaEntity reserva = new ReservaEntity();
        reserva.setPuntoVenta(puntoVenta);
        reserva.setCliente(cliente);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado("Pendiente");
        reserva.setAnticipo(request.getAnticipo());
        reserva.setSaldoPendiente(request.getSaldoPendiente());
        reserva.setMetodoPago(request.getMetodoPago());
        reserva.setComprobante(request.getComprobante());
        reserva.setObservaciones(request.getObservaciones());

        // Guardar items de la reserva
        List<ReservaItemEntity> items = request.getItems().stream()
                .map(itemRequest -> {
                    ItemEntity item = itemRepository.findById(itemRequest.getIdItem())
                            .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"));
                    ReservaItemEntity reservaItem = new ReservaItemEntity();
                    reservaItem.setReserva(reserva);
                    reservaItem.setItem(item);
                    reservaItem.setCantidad(itemRequest.getCantidad());
                    return reservaItem;
                })
                .collect(Collectors.toList());
        reserva.setItems(items);

        reservaRepository.save(reserva);

        // Construir respuesta
        List<ItemReservaResponse> itemsResponse = items.stream()
                .map(item -> new ItemReservaResponse(
                        item.getItem().getId(),
                        item.getItem().getDescripcion(),
                        item.getCantidad(),
                        item.getItem().getPrecioUnitario()
                ))
                .collect(Collectors.toList());

        return new ReservaResponse(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getEstado(),
                reserva.getMetodoPago(),
                reserva.getComprobante(),
                "Reserva creada exitosamente",
                itemsResponse
        );
    }

    public List<ReservaResponse> obtenerReservasNoVerificadas() {
        List<ReservaEntity> reservas = reservaRepository.findByEstado("Pendiente");
        return reservas.stream()
                .map(r -> new ReservaResponse(
                        r.getId(),
                        r.getFechaReserva(),
                        r.getEstado(),
                        r.getMetodoPago(),
                        r.getComprobante(),
                        "",
                        r.getItems().stream()
                                .map(item -> new ItemReservaResponse(
                                        item.getItem().getId(),
                                        item.getItem().getDescripcion(),
                                        item.getCantidad(),
                                        item.getItem().getPrecioUnitario()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String verificarReserva(Integer idReserva) {
        ReservaEntity reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        reserva.setEstado("Verificado");
        reservaRepository.save(reserva);
        return "Reserva verificada exitosamente";
    }
}