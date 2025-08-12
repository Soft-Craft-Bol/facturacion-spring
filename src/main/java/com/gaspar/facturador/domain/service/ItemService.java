package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ItemWithSucursalesDTO;
import com.gaspar.facturador.application.response.ProductoConRecetaResponse;
import com.gaspar.facturador.application.response.SucursalInfoDTO;
import com.gaspar.facturador.application.rest.dto.ProductoSucursalDto;
import com.gaspar.facturador.application.rest.dto.PromocionInfo;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.PromocionCrudRepository;
import com.gaspar.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    private final ItemCrudRepository itemCrudRepository;
    @Autowired
    private final PuntoVentaCrudRepository puntoVentaCrudRepository;
    @Autowired
    private final SucursalItemCrudRepository sucursalItemCrudRepository;
    @Autowired
    private final PromocionCrudRepository promocionCrudRepository;

    @Transactional(readOnly = true)
    public Page<ItemWithSucursalesDTO> findItemsWithSucursales(
            String search,
            String codigo,
            Boolean conDescuento,
            Integer sucursalId,
            Integer categoriaId,
            Pageable pageable) {

        Specification<ItemEntity> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("descripcion")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("codigo")), "%" + search.toLowerCase() + "%")
                    ));
        }

        if (codigo != null && !codigo.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("codigo"), codigo + "%"));
        }


        if (conDescuento != null) {
            spec = spec.and((root, query, cb) ->
                    conDescuento ?
                            cb.isNotEmpty(root.get("promocionItems")) :
                            cb.isEmpty(root.get("promocionItems")));
        }

        if (sucursalId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.join("sucursalItems").get("sucursal").get("id"), sucursalId));
        }

        if (categoriaId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("id"), categoriaId));
        }

        Page<ItemEntity> itemsPage = itemCrudRepository.findAll(spec, pageable);

        itemsPage.getContent().forEach(item -> {
            Hibernate.initialize(item.getPromocionItems());
        });

        return itemsPage.map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductoConRecetaResponse> listarProductosConRecetaInfo(
            String searchTerm,
            Boolean tieneReceta,
            Pageable pageable) {

        Specification<ItemEntity> spec = Specification.where(null);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("descripcion")), "%" + searchTerm.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("codigo")), "%" + searchTerm.toLowerCase() + "%")
                    )
            );
        }

        if (tieneReceta != null) {
            spec = spec.and((root, query, cb) -> {
                Join<ItemEntity, RecetasEntity> recetasJoin = root.join("recetas", JoinType.LEFT);
                if (tieneReceta) {
                    return cb.isNotEmpty(root.get("recetas"));
                } else {
                    return cb.isEmpty(root.get("recetas"));
                }
            });
        }

        Page<ItemEntity> itemsPage = itemCrudRepository.findAll(spec, pageable);

        return itemsPage.map(item -> {
            ProductoConRecetaResponse dto = new ProductoConRecetaResponse();
            dto.setId(item.getId());
            dto.setCodigo(item.getCodigo());
            dto.setDescripcion(item.getDescripcion());
            dto.setUnidadMedida(item.getUnidadMedida());
            dto.setPrecioUnitario(item.getPrecioUnitario());
            dto.setCodigoProductoSin(item.getCodigoProductoSin());
            dto.setImagen(item.getImagen());

            boolean tieneRecetas = !item.getRecetas().isEmpty();
            dto.setTieneReceta(tieneRecetas);
            dto.setCantidadRecetas(tieneRecetas ? item.getRecetas().size() : 0);

            return dto;
        });
    }

    private ItemWithSucursalesDTO convertToDto(ItemEntity item) {
        ItemWithSucursalesDTO dto = new ItemWithSucursalesDTO();
        dto.setId(item.getId());
        dto.setCodigo(item.getCodigo());
        dto.setDescripcion(item.getDescripcion());
        dto.setUnidadMedida(item.getUnidadMedida());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setCodigoProductoSin(item.getCodigoProductoSin());
        dto.setCategoria(item.getCategoria() != null && item.getCategoria().getNombre() != null
                ? item.getCategoria().getNombre()
                : "Sin categoría");

        dto.setImagen(item.getImagen());
        dto.setImagen(item.getImagen());

        // Lógica de descuentos
        boolean tieneDescuento = !item.getPromocionItems().isEmpty();
        dto.setTieneDescuento(tieneDescuento);

        BigDecimal precioConDescuento = item.getPrecioUnitario();
        if(tieneDescuento) {
            double maxDescuento = item.getPromocionItems().stream()
                    .mapToDouble(PromocionEntity::getDescuento)
                    .max()
                    .orElse(0.0);

            BigDecimal descuento = item.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(maxDescuento))
                    .divide(BigDecimal.valueOf(100));

            precioConDescuento = item.getPrecioUnitario().subtract(descuento);
        }
        dto.setPrecioConDescuento(precioConDescuento);

        // Mapeo de sucursales
        dto.setSucursales(item.getSucursalItems().stream()
                .map(si -> {
                    SucursalInfoDTO sucursalDto = new SucursalInfoDTO(
                            si.getSucursal().getId(),
                            si.getSucursal().getNombre(),
                            si.getSucursal().getDepartamento(),
                            si.getCantidad());

                    // Lógica de descuentos por sucursal
                    Optional<PromocionEntity> promocion = item.getPromocionItems().stream()
                            .filter(p -> p.getSucursal().getId().equals(si.getSucursal().getId()))
                            .findFirst();

                    if(promocion.isPresent()) {
                        sucursalDto.setTieneDescuento(true);
                        BigDecimal descuento = item.getPrecioUnitario()
                                .multiply(BigDecimal.valueOf(promocion.get().getDescuento()))
                                .divide(BigDecimal.valueOf(100));
                        sucursalDto.setPrecioConDescuento(item.getPrecioUnitario().subtract(descuento));
                    } else {
                        sucursalDto.setTieneDescuento(false);
                        sucursalDto.setPrecioConDescuento(item.getPrecioUnitario());
                    }

                    return sucursalDto;
                })
                .collect(Collectors.toList()));

        return dto;
    }

    @Transactional(readOnly = true)
    public Page<ProductoSucursalDto> getProductosByPuntoVentaId(
            Integer puntoVentaId,
            String searchTerm,
            Integer codigoProductoSin,
            Boolean conDescuento,
            Boolean sinStock,
            Integer categoriaId,
            Pageable pageable) {

        PuntoVentaEntity puntoVenta = puntoVentaCrudRepository.findById(puntoVentaId)
                .orElseThrow(() -> new EntityNotFoundException("Punto de venta no encontrado"));
        SucursalEntity sucursal = puntoVenta.getSucursal();

        Specification<SucursalItemEntity> spec = Specification.where(
                (root, query, cb) -> cb.equal(root.get("sucursal").get("id"), sucursal.getId())
        );

        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                Join<SucursalItemEntity, ItemEntity> itemJoin = root.join("item");
                return cb.or(
                        cb.like(cb.lower(itemJoin.get("codigo")), "%" + searchTerm.toLowerCase() + "%"),
                        cb.like(cb.lower(itemJoin.get("descripcion")), "%" + searchTerm.toLowerCase() + "%")
                );
            });
        }

        if (codigoProductoSin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.join("item").get("codigoProductoSin"), codigoProductoSin));
        }

        if (conDescuento != null) {
            spec = spec.and((root, query, cb) -> {
                Join<SucursalItemEntity, PromocionEntity> promocionJoin = root.join("item")
                        .join("promocionItems", JoinType.LEFT);

                if (conDescuento) {
                    return cb.isNotNull(promocionJoin.get("id"));
                } else {
                    return cb.isNull(promocionJoin.get("id"));
                }
            });
        }

        if (sinStock != null) {
            spec = spec.and((root, query, cb) -> {
                if (sinStock) {
                    return cb.lessThanOrEqualTo(root.get("cantidad"), 0);
                } else {
                    return cb.greaterThan(root.get("cantidad"), 0);
                }
            });
        }

        if (categoriaId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.join("item").join("categoria").get("id"), categoriaId));
        }

        Page<SucursalItemEntity> sucursalItemsPage = sucursalItemCrudRepository.findAll(spec, pageable);

        return sucursalItemsPage.map(sucursalItem -> {
            ItemEntity item = sucursalItem.getItem();
            ProductoSucursalDto dto = new ProductoSucursalDto();

            dto.setId(item.getId());
            dto.setCodigo(item.getCodigo());
            dto.setDescripcion(item.getDescripcion());
            dto.setUnidadMedida(item.getUnidadMedida());
            dto.setPrecioUnitario(item.getPrecioUnitario());
            dto.setCodigoProductoSin(item.getCodigoProductoSin());
            dto.setImagen(item.getImagen());
            dto.setCantidadDisponible(sucursalItem.getCantidad());

            if(item.getCategoria() != null) {
                dto.setCategoriaId(item.getCategoria().getId());
                dto.setCategoriaNombre(item.getCategoria().getNombre());
            } else {
                dto.setCategoriaId(null);
                dto.setCategoriaNombre(null);
            }

            dto.setSucursalId(sucursal.getId());
            dto.setSucursalNombre(sucursal.getNombre());
            dto.setSucursalDireccion(sucursal.getDireccion());

            // Info de promoción
            PromocionInfo promocion = getActivePromocionForItemInSucursal(item.getId(), sucursal.getId());
            dto.setTieneDescuento(promocion != null);
            dto.setPrecioConDescuento(promocion != null ?
                    promocion.getPrecioPromocional() : item.getPrecioUnitario());

            return dto;
        });
    }

    private PromocionInfo getActivePromocionForItemInSucursal(Integer itemId, Integer sucursalId) {
        Optional<PromocionEntity> promocionOpt = promocionCrudRepository
                .findOneByItemIdAndSucursalId(itemId, sucursalId);

        if (promocionOpt.isPresent()) {
            PromocionEntity promocion = promocionOpt.get();
            ItemEntity item = promocion.getItem();

            PromocionInfo info = new PromocionInfo();
            info.setPromocionId(promocion.getId());
            info.setPorcentajeDescuento(promocion.getDescuento());

            BigDecimal descuento = item.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(promocion.getDescuento()))
                    .divide(BigDecimal.valueOf(100));
            BigDecimal precioPromocional = item.getPrecioUnitario().subtract(descuento);
            info.setPrecioPromocional(precioPromocional);

            return info;
        }
        return null;
    }
}