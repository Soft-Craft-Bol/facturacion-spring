package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.crud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private SucursalItemCrudRepository sucursalItemRepository;

    private static final List<String> ESTADOS_VALIDOS = List.of(
            "Pendiente", "Aprobada", "Rechazada", "Cancelada", "Completada"
    );

    private static final List<String> ESTADOS_FINALES = List.of(
            "Completada", "Cancelada", "Rechazada"
    );

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

        // Construir mensaje de observaciones con validaciones de stock
        StringBuilder observacionesBuilder = new StringBuilder();
        if (request.getObservaciones() != null) {
            observacionesBuilder.append(request.getObservaciones()).append("\n");
        }

        ReservaEntity finalReserva = reserva;
        List<ReservaItemEntity> items = request.getItems().stream()
                .map(itemRequest -> {
                    ItemEntity item = itemRepository.findById(itemRequest.getIdItem())
                            .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: " + itemRequest.getIdItem()));

                    // Buscar stock en la sucursal específica
                    SucursalItemEntity sucursalItem = sucursalItemRepository
                            .findBySucursalAndItem(puntoVenta.getSucursal(), item)
                            .orElse(new SucursalItemEntity()); // Si no existe, stock cero

                    // Verificar stock
                    BigDecimal stockDisponible = BigDecimal.valueOf(sucursalItem.getCantidad() != null ? sucursalItem.getCantidad() : 0);
                    boolean stockSuficiente = stockDisponible.compareTo(itemRequest.getCantidad()) >= 0;

                    if (!stockSuficiente) {
                        observacionesBuilder.append(String.format(
                                "Advertencia: Stock insuficiente para %s (Solicitado: %s, Disponible: %s)\n",
                                item.getDescripcion(), itemRequest.getCantidad(), stockDisponible
                        ));
                    }

                    ReservaItemEntity reservaItem = new ReservaItemEntity();
                    reservaItem.setReserva(finalReserva);
                    reservaItem.setItem(item);
                    reservaItem.setCantidad(itemRequest.getCantidad());
                    reservaItem.setStockSuficiente(stockSuficiente);
                    return reservaItem;
                })
                .collect(Collectors.toList());

        reserva.setItems(items);
        reserva.setObservaciones(observacionesBuilder.toString().trim());
        reserva = reservaRepository.save(reserva);

        return convertirAResponse(reserva, "Reserva creada exitosamente. Verifique las observaciones sobre disponibilidad.");
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> obtenerReservasPorEstado(String estado) {
        if (estado != null && !ESTADOS_VALIDOS.contains(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }

        List<ReservaEntity> reservas = estado != null ?
                reservaRepository.findByEstado(estado) :
                (List<ReservaEntity>) reservaRepository.findAll();

        return convertirAResponse(reservas);
    }

    @Transactional
    public ReservaResponse cambiarEstadoReserva(Integer idReserva, String nuevoEstado, String motivo) {
        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }

        ReservaEntity reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada: " + idReserva));

        // Validar transición de estados
        if (ESTADOS_FINALES.contains(reserva.getEstado())) {
            throw new IllegalStateException(
                    String.format("No se puede modificar una reserva en estado %s", reserva.getEstado())
            );
        }

        // Validar transiciones específicas si es necesario
        if ("Aprobada".equals(nuevoEstado) && !"Pendiente".equals(reserva.getEstado())) {
            throw new IllegalStateException(
                    "Solo se puede aprobar una reserva en estado Pendiente"
            );
        }

        reserva.setEstado(nuevoEstado);

        // Actualizar observaciones solo si se proporciona un motivo
        String nuevasObservaciones = (motivo != null && !motivo.isBlank()) ?
                String.format("%s\n[%s] %s: %s",
                        reserva.getObservaciones() != null ? reserva.getObservaciones() : "",
                        LocalDateTime.now(),
                        nuevoEstado,
                        motivo) :
                reserva.getObservaciones();

        reserva.setObservaciones(nuevasObservaciones);
        reserva = reservaRepository.save(reserva);

        return convertirAResponse(reserva, "Estado actualizado a " + nuevoEstado);
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> filtrarReservas(
            String estado,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ) {
        // Validaciones
        if (estado != null && !ESTADOS_VALIDOS.contains(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }

        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("Rango de fechas inválido");
        }

        List<ReservaEntity> reservas;
        if (estado != null && fechaInicio != null) {
            reservas = reservaRepository.findByEstadoAndFechaReservaBetween(estado, fechaInicio, fechaFin);
        } else if (estado != null) {
            reservas = reservaRepository.findByEstado(estado);
        } else if (fechaInicio != null) {
            reservas = reservaRepository.findByFechaReservaBetween(fechaInicio, fechaFin);
        } else {
            reservas = (List<ReservaEntity>) reservaRepository.findAll();
        }

        return convertirAResponse(reservas);
    }

    // Métodos auxiliares
    private List<ReservaResponse> convertirAResponse(List<ReservaEntity> reservas) {
        return reservas.stream()
                .map(r -> convertirAResponse(r, ""))
                .collect(Collectors.toList());
    }

    private ReservaResponse convertirAResponse(ReservaEntity reserva, String mensaje) {
        List<ItemReservaResponse> itemsResponse = reserva.getItems().stream()
                .map(item -> {
                    // Obtener stock disponible para este item en la sucursal
                    SucursalItemEntity sucursalItem = sucursalItemRepository
                            .findBySucursalAndItem(reserva.getPuntoVenta().getSucursal(), item.getItem())
                            .orElse(new SucursalItemEntity());

                    BigDecimal stockDisponible = BigDecimal.valueOf(sucursalItem.getCantidad() != null ? sucursalItem.getCantidad() : 0);

                    return new ItemReservaResponse(
                            item.getItem().getId(),
                            item.getItem().getDescripcion(),
                            item.getCantidad(),
                            item.getItem().getPrecioUnitario(),
                            item.getItem().getImagen(),
                            item.isStockSuficiente(),
                            stockDisponible
                    );
                })
                .collect(Collectors.toList());

        boolean stockGeneral = itemsResponse.stream().allMatch(ItemReservaResponse::isStockSuficiente);
        BigDecimal stockMinimo = itemsResponse.stream()
                .map(ItemReservaResponse::getStockDisponible)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return new ReservaResponse(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getEstado(),
                reserva.getMetodoPago(),
                reserva.getComprobante(),
                mensaje,
                reserva.getAnticipo(),
                reserva.getSaldoPendiente(),
                reserva.getObservaciones(),
                reserva.getCliente().getId(),
                reserva.getCliente().getNombreRazonSocial(),
                reserva.getPuntoVenta().getId(),
                reserva.getPuntoVenta().getNombre(),
                stockGeneral,       // ✅ nuevo campo
                stockMinimo,        // ✅ nuevo campo
                itemsResponse
        );


    }
}