package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ItemWithSucursalesDTO;
import com.gaspar.facturador.application.response.SucursalInfoDTO;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.PromocionEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    private final ItemCrudRepository itemCrudRepository;

    @Transactional(readOnly = true)
    public Page<ItemWithSucursalesDTO> findItemsWithSucursales(
            String search,
            String codigo,
            Boolean conDescuento,
            Integer sucursalId,
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

        Page<ItemEntity> itemsPage = itemCrudRepository.findAll(spec, pageable);

        itemsPage.getContent().forEach(item -> {
            Hibernate.initialize(item.getPromocionItems());
        });

        return itemsPage.map(this::convertToDto);
    }

    private ItemWithSucursalesDTO convertToDto(ItemEntity item) {
        ItemWithSucursalesDTO dto = new ItemWithSucursalesDTO();
        dto.setId(item.getId());
        dto.setCodigo(item.getCodigo());
        dto.setDescripcion(item.getDescripcion());
        dto.setUnidadMedida(item.getUnidadMedida());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setCodigoProductoSin(item.getCodigoProductoSin());
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
}