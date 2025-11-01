package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.crud.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.crud.IDespachoRepository;
import com.gaspar.facturador.persistence.specification.DespachoSpecifications;
import com.gaspar.facturador.persistence.specification.ProduccionSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class DespachoService {

    private final IDespachoRepository despachoRepository;
    private final SucursalItemCrudRepository sucursalItemRepository;

    public DespachoService(IDespachoRepository despachoRepository,
                           SucursalItemCrudRepository sucursalItemRepository) {
        this.despachoRepository = despachoRepository;
        this.sucursalItemRepository = sucursalItemRepository;
    }

    @Transactional
    public DespachoEntity registrarDespacho(DespachoEntity despacho) {
        SucursalEntity origen = despacho.getSucursalOrigen();
        SucursalEntity destino = despacho.getSucursalDestino();

        for (DespachoItemEntity itemDespacho : despacho.getDespachoItems()) {
            ItemEntity item = itemDespacho.getItem();

            // Stock en origen
            SucursalItemEntity stockOrigen = sucursalItemRepository
                    .findBySucursalAndItem(origen, item)
                    .orElseThrow(() -> new RuntimeException("No existe stock en origen para el item " + item.getCodigo()));

            if (stockOrigen.getCantidad() < itemDespacho.getCantidad().intValue()) {
                throw new RuntimeException("Stock insuficiente en origen para el item " + item.getCodigo());
            }

            // Descontar en origen
            stockOrigen.setCantidad(stockOrigen.getCantidad() - itemDespacho.getCantidad().intValue());
            sucursalItemRepository.save(stockOrigen);

            // Sumar en destino
            SucursalItemEntity stockDestino = sucursalItemRepository
                    .findBySucursalAndItem(destino, item)
                    .orElse(new SucursalItemEntity());

            if (stockDestino.getId() == null) { // si no existe, creamos uno nuevo
                stockDestino.setSucursal(destino);
                stockDestino.setItem(item);
                stockDestino.setCantidad(0);
            }

            stockDestino.setCantidad(stockDestino.getCantidad() + itemDespacho.getCantidad().intValue());
            sucursalItemRepository.save(stockDestino);

            // Asociar item al despacho
            itemDespacho.setDespacho(despacho);
        }

        return despachoRepository.save(despacho);
    }

    public Page<DespachoEntity> findAllWithFilters(Date fechaInicio,
                                                   Date fechaFin,
                                                   String transporte,
                                                   Long numeroContacto,
                                                   Long sucursalOrigen,
                                                   Long sucursalDestino,
                                                   Long itemId,
                                                   int page,
                                                   int size) {

        Specification<DespachoEntity> spec = Specification.where(DespachoSpecifications.fechaBetween(fechaInicio, fechaFin))
                .and(DespachoSpecifications.transporteEquals(transporte))
                .and(DespachoSpecifications.numeroContactoEquals(numeroContacto))
                .and(DespachoSpecifications.sucursalOrigenEquals(sucursalOrigen))
                .and(DespachoSpecifications.sucursalDestinoEquals(sucursalDestino))
                .and(DespachoSpecifications.contieneItem(itemId));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaEnvio"));

        return despachoRepository.findAll(spec, pageable);
    }
}
