package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.ReservaRequest;
import com.gaspar.facturador.application.rest.dto.ReservaResponse;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.crud.ReservaCrudRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.gaspar.facturador.persistence.crud.ClienteCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservaService {

    @Autowired
    private ReservaCrudRepository reservaRepository;

    @Autowired
    private ItemCrudRepository itemRepository;

    @Autowired
    private PuntoVentaCrudRepository puntoVentaRepository;

    @Autowired
    private ClienteCrudRepository clienteRepository;

    public ReservaResponse crearReserva(ReservaRequest request) {
        ItemEntity item = itemRepository.findById(request.getIdItem())
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"));
        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(request.getIdPuntoVenta())
                .orElseThrow(() -> new IllegalArgumentException("Punto de venta no encontrado"));
        ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        ReservaEntity reserva = new ReservaEntity();
        reserva.setItem(item);
        reserva.setPuntoVenta(puntoVenta);
        reserva.setCliente(cliente);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setCantidad(request.getCantidad());
        reserva.setAnticipo(request.getAnticipo());
        reserva.setSaldoPendiente(request.getSaldoPendiente());
        reserva.setEstado("Pendiente"); // Estado inicial
        reserva.setObservaciones(request.getObservaciones());

        reservaRepository.save(reserva);

        ReservaResponse response = new ReservaResponse();
        response.setId(reserva.getId());
        response.setFechaReserva(reserva.getFechaReserva());
        response.setEstado(reserva.getEstado());
        response.setMensaje("Reserva creada exitosamente");

        return response;
    }
}