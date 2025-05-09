package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.*;
import com.gaspar.facturador.application.rest.util.SucursalItemUtility;
import com.gaspar.facturador.domain.service.ItemService;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.PromocionEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/sucursal-items")
public class SucursalItemController {

    @Autowired
    private SucursalItemCrudRepository sucursalItemCrudRepository;

    @Autowired
    private SucursalCrudRepository sucursalCrudRepository;

    @Autowired
    private ItemCrudRepository itemCrudRepository;

    public final ItemService itemService;

    public SucursalItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> findAll() {
        List<SucursalItemEntity> sucursalItems = (List<SucursalItemEntity>) sucursalItemCrudRepository.findAll();
        return ResponseEntity.ok(SucursalItemUtility.groupItemsBySucursal(sucursalItems));
    }
    //obtener items por sucursal
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<SucursalDTO> findBySucursal(@PathVariable Integer sucursalId) {
        List<SucursalItemEntity> items = sucursalItemCrudRepository.findBySucursal_Id(sucursalId);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SucursalEntity sucursal = items.get(0).getSucursal();
        SucursalDTO sucursalDTO = new SucursalDTO();
        sucursalDTO.setId(sucursal.getId());
        sucursalDTO.setCodigo(sucursal.getCodigo());
        sucursalDTO.setNombre(sucursal.getNombre());
        sucursalDTO.setDepartamento(sucursal.getDepartamento());
        sucursalDTO.setMunicipio(sucursal.getMunicipio());
        sucursalDTO.setDireccion(sucursal.getDireccion());
        sucursalDTO.setTelefono(sucursal.getTelefono());
        sucursalDTO.setImage(sucursal.getImage());

        List<ItemWithQuantityDTO> itemDTOs = items.stream().map(sucursalItem -> {
            ItemWithQuantityDTO itemDTO = new ItemWithQuantityDTO();
            itemDTO.setId(sucursalItem.getItem().getId());
            itemDTO.setCodigo(sucursalItem.getItem().getCodigo());
            itemDTO.setDescripcion(sucursalItem.getItem().getDescripcion());
            itemDTO.setUnidadMedida(sucursalItem.getItem().getUnidadMedida());
            itemDTO.setPrecioUnitario(sucursalItem.getItem().getPrecioUnitario());
            itemDTO.setCodigoProductoSin(sucursalItem.getItem().getCodigoProductoSin());
            itemDTO.setImagen(sucursalItem.getItem().getImagen());
            itemDTO.setCantidad(sucursalItem.getCantidad());
            return itemDTO;
        }).collect(Collectors.toList());
        sucursalDTO.setItems(itemDTOs);
        return ResponseEntity.ok(sucursalDTO);
    }
    //insertar un item con cantidad a una sucursal a una sucursal
    @PostMapping("/sucursal/{sucursalId}/item/{itemId}")
    public ResponseEntity<SucursalItemEntity> setInitialQuantity(@PathVariable Integer sucursalId, @PathVariable Integer itemId, @RequestParam Integer cantidad) {
        Optional<SucursalEntity> sucursalOptional = sucursalCrudRepository.findById(sucursalId);
        Optional<ItemEntity> itemOptional = itemCrudRepository.findById(itemId);

        if (!sucursalOptional.isPresent() || !itemOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<SucursalItemEntity> sucursalItems = sucursalItemCrudRepository.findBySucursal_IdAndItem_Id(sucursalId, itemId);
        if (!sucursalItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        SucursalItemEntity sucursalItem = new SucursalItemEntity();
        sucursalItem.setSucursal(sucursalOptional.get());
        sucursalItem.setItem(itemOptional.get());
        sucursalItem.setCantidad(cantidad);
        SucursalItemEntity savedSucursalItem = sucursalItemCrudRepository.save(sucursalItem);
        return ResponseEntity.ok(savedSucursalItem);
    }

    @DeleteMapping("/sucursal/{sucursalId}/item/{itemId}")
    public ResponseEntity<Void> removeItemFromScurusal(@PathVariable Integer sucursalId, @PathVariable Integer itemId) {
        Optional<SucursalItemEntity> sucursalItemOptional = sucursalItemCrudRepository.findBySucursal_IdAndItem_Id(sucursalId, itemId);
        if (!sucursalItemOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sucursalItemCrudRepository.delete(sucursalItemOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/sucursal/{sucursalId}/item/{itemId}/increase")
    public ResponseEntity<SucursalItemEntity> increaseQuantity(@PathVariable Integer sucursalId, @PathVariable Integer itemId, @RequestParam Integer cantidad) {
        Optional<SucursalItemEntity> sucursalItemOptional = sucursalItemCrudRepository.findBySucursal_IdAndItem_Id(sucursalId, itemId);

        if (!sucursalItemOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SucursalItemEntity sucursalItem = sucursalItemOptional.get();
        sucursalItem.setCantidad(sucursalItem.getCantidad() + cantidad);
        SucursalItemEntity updatedSucursalItem = sucursalItemCrudRepository.save(sucursalItem);
        return ResponseEntity.ok(updatedSucursalItem);
    }

    @PutMapping("/sucursal/{sucursalId}/item/{itemId}/decrease")
    public ResponseEntity<SucursalItemEntity> decreaseQuantity(@PathVariable Integer sucursalId, @PathVariable Integer itemId, @RequestParam Integer cantidad) {
        Optional<SucursalItemEntity> sucursalItemOptional = sucursalItemCrudRepository.findBySucursal_IdAndItem_Id(sucursalId, itemId);

        if (!sucursalItemOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SucursalItemEntity sucursalItem = sucursalItemOptional.get();
        int newCantidad = sucursalItem.getCantidad() - cantidad;
        if (newCantidad < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        sucursalItem.setCantidad(newCantidad);
        SucursalItemEntity updatedSucursalItem = sucursalItemCrudRepository.save(sucursalItem);
        return ResponseEntity.ok(updatedSucursalItem);
    }
    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemWithSucursalesDTO> findItemWithSucursales(@PathVariable Integer itemId) {
        List<SucursalItemEntity> sucursalItems = sucursalItemCrudRepository.findByItemId(itemId);

        if (sucursalItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SucursalItemEntity firstItem = sucursalItems.get(0);
        ItemWithSucursalesDTO itemDTO = new ItemWithSucursalesDTO();
        itemDTO.setId(firstItem.getItem().getId());
        itemDTO.setCodigo(firstItem.getItem().getCodigo());
        itemDTO.setDescripcion(firstItem.getItem().getDescripcion());
        itemDTO.setUnidadMedida(firstItem.getItem().getUnidadMedida());
        itemDTO.setPrecioUnitario(firstItem.getItem().getPrecioUnitario());
        itemDTO.setCodigoProductoSin(firstItem.getItem().getCodigoProductoSin());
        itemDTO.setImagen(firstItem.getItem().getImagen());

        List<SucursalInfoDTO> sucursales = sucursalItems.stream().map(sucursalItem -> {
            SucursalInfoDTO sucursalInfo = new SucursalInfoDTO();
            sucursalInfo.setId(sucursalItem.getSucursal().getId());
            sucursalInfo.setNombre(sucursalItem.getSucursal().getNombre());
            sucursalInfo.setDepartamento(sucursalItem.getSucursal().getDepartamento());
            sucursalInfo.setCantidad(sucursalItem.getCantidad());
            return sucursalInfo;
        }).collect(Collectors.toList());

        itemDTO.setSucursales(sucursales);

        return ResponseEntity.ok(itemDTO);
    }

    @GetMapping("/items-with-sucursales")
    public ResponseEntity<Page<ItemWithSucursalesDTO>> findAllItemsWithSucursales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) Boolean conDescuento,
            @RequestParam(required = false) Integer sucursalId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        // Configuración de ordenación
        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.ASC);
        Sort sort = sortBy != null ? Sort.by(direction, sortBy) : Sort.by("descripcion").ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Llamada al servicio con todos los parámetros
        Page<ItemWithSucursalesDTO> itemsPage = itemService.findItemsWithSucursales(
                search, codigo, conDescuento, sucursalId, pageable);

        return ResponseEntity.ok(itemsPage);
    }

}