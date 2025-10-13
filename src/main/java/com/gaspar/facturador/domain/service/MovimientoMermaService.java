package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.MovimientoMermaResponse;
import com.gaspar.facturador.application.rest.dto.MovimientoMermaDTO;
import com.gaspar.facturador.persistence.crud.MovimientoMermaRepository;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.MovimientoMermaEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoMerma;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.gaspar.facturador.persistence.specification.MovimientoMermaSpecification.*;

@Service
@RequiredArgsConstructor
public class MovimientoMermaService {

    private final MovimientoMermaRepository movimientoMermaRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final SucursalItemCrudRepository sucursalItemRepository;

    @Transactional
    public void registrarMerma(MovimientoMermaDTO dto) {
        MovimientoMermaEntity movimiento = new MovimientoMermaEntity();
        movimiento.setSucursal(new SucursalEntity());
        movimiento.getSucursal().setId(Math.toIntExact(dto.getSucursalId()));
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setMotivo(dto.getMotivo());
        movimiento.setRegistradoPor(dto.getRegistradoPor());
        movimiento.setTipo(TipoMerma.valueOf(dto.getTipo()));
        movimiento.setCantidad(dto.getCantidad());

        if (dto.getInsumoId() != null) {
            movimiento.setInsumo(new InsumoEntity());
            movimiento.getInsumo().setId(dto.getInsumoId());
            var stock = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(dto.getSucursalId().intValue(), dto.getInsumoId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado en la sucursal"));
            stock.setCantidad(stock.getCantidad().subtract(dto.getCantidad()));
            sucursalInsumoRepository.save(stock);
        }

        if (dto.getItemId() != null) {
            movimiento.setItem(new ItemEntity());
            movimiento.getItem().setId(dto.getItemId());
            var stock = sucursalItemRepository
                    .findBySucursal_IdAndItem_Id(dto.getSucursalId().intValue(), dto.getItemId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado en la sucursal"));
            stock.setCantidad(stock.getCantidad() - dto.getCantidad().intValue());
            sucursalItemRepository.save(stock);
        }

        movimientoMermaRepository.save(movimiento);
    }

    public MovimientoMermaResponse obtenerMermaPorId(Long id) {
        return movimientoMermaRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NoSuchElementException("Merma no encontrada"));
    }

    public Page<MovimientoMermaResponse> buscarMermas(
            Integer sucursalId,
            TipoMerma tipo,
            String motivo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Pageable pageable) {

        Specification<MovimientoMermaEntity> spec = Specification
                .where(sucursalIdEquals(sucursalId))
                .and(tipoEquals(tipo))
                .and(motivoLike(motivo))
                .and(fechaBetween(fechaInicio, fechaFin));

        return movimientoMermaRepository.findAll(spec, pageable)
                .map(this::mapToResponse);
    }


    @Transactional
    public void eliminarMerma(Long id) {
        MovimientoMermaEntity merma = movimientoMermaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Merma no encontrada"));
        if (merma.getInsumo() != null) {
            var stock = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(merma.getSucursal().getId(), merma.getInsumo().getId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado en la sucursal"));
            stock.setCantidad(stock.getCantidad().add(merma.getCantidad()));
            sucursalInsumoRepository.save(stock);
        }

        if (merma.getItem() != null) {
            var stock = sucursalItemRepository
                    .findBySucursal_IdAndItem_Id(merma.getSucursal().getId(), merma.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado en la sucursal"));
            stock.setCantidad(stock.getCantidad() + merma.getCantidad().intValue());
            sucursalItemRepository.save(stock);
        }

        movimientoMermaRepository.delete(merma);
    }

    private MovimientoMermaResponse mapToResponse(MovimientoMermaEntity entity) {
        return MovimientoMermaResponse.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .cantidad(entity.getCantidad())
                .tipo(entity.getTipo().name())
                .motivo(entity.getMotivo())
                .sucursalId(entity.getSucursal().getId().longValue())
                .sucursalNombre(entity.getSucursal().getNombre())
                .insumoId(entity.getInsumo() != null ? entity.getInsumo().getId() : null)
                .insumoNombre(entity.getInsumo() != null ? entity.getInsumo().getNombre() : null)
                .itemId(entity.getItem() != null ? entity.getItem().getId() : null)
                .itemNombre(entity.getItem() != null ? entity.getItem().getDescripcion() : null)
                .registradoPor(entity.getRegistradoPor())
                .build();
    }
}
