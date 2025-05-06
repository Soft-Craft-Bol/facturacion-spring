package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.SucursalInsumoRequest;
import com.gaspar.facturador.application.response.*;
import com.gaspar.facturador.application.rest.util.SucursalInsumoUtility;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/sucursal-insumos")
public class SucursalInsumoController {
    @Autowired
    private SucursalCrudRepository sucursalCrudRepository;
    @Autowired
    private InsumoCrudRepository insumoCrudRepository;
    @Autowired
    private SucursalInsumoCrudRepository sucursalInsumoCrudRepository;
    @GetMapping
    public ResponseEntity<List<SucursalDTOIns>> findAll() {
        List<SucursalInsumoEntity> sucursalInsumos = (List<SucursalInsumoEntity>) sucursalInsumoCrudRepository.findAll();
        return ResponseEntity.ok(SucursalInsumoUtility.groupInsumosBySucursal(sucursalInsumos));
    }
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<SucursalDTOIns> findBySucursal(@PathVariable Integer sucursalId) {
        List<SucursalInsumoEntity> insumos = sucursalInsumoCrudRepository.findBySucursalId(sucursalId);
        if(insumos.isEmpty())
            return ResponseEntity.notFound().build();
        SucursalEntity sucursal = insumos.get(0).getSucursal();
        SucursalDTOIns sucursalDTO = new SucursalDTOIns();
        sucursalDTO.setId(sucursal.getId());
        sucursalDTO.setCodigo(sucursal.getCodigo());
        sucursalDTO.setNombre(sucursal.getNombre());
        sucursalDTO.setDepartamento(sucursal.getDepartamento());
        sucursalDTO.setMunicipio(sucursal.getMunicipio());
        sucursalDTO.setDireccion(sucursal.getDireccion());
        sucursalDTO.setTelefono(sucursal.getTelefono());
        sucursalDTO.setImage(sucursal.getImage());

        List<InsumoWithQuantityDTO> insumoDTOs = insumos.stream().map( sucursalInsumo -> {
            InsumoWithQuantityDTO insumoDTO = new InsumoWithQuantityDTO();
            insumoDTO.setId(sucursalInsumo.getInsumo().getId());
            insumoDTO.setNombre(sucursalInsumo.getInsumo().getNombre());
            insumoDTO.setProveedor(sucursalInsumo.getInsumo().getProveedor());
            insumoDTO.setMarca(sucursalInsumo.getInsumo().getMarca());
            insumoDTO.setPrecio(sucursalInsumo.getInsumo().getPrecio());
            insumoDTO.setUnidades(sucursalInsumo.getInsumo().getUnidades());
            insumoDTO.setDescripcion(sucursalInsumo.getInsumo().getDescripcion());
            insumoDTO.setImagen(sucursalInsumo.getInsumo().getImagen());
            insumoDTO.setCantidad(sucursalInsumo.getCantidad());
            return insumoDTO;
        }).collect(Collectors.toList());
        sucursalDTO.setInsumos(insumoDTOs);
        return ResponseEntity.ok(sucursalDTO);
    }
    @PostMapping("/sucursal/{sucursalId}/insumo/{insumoId}")
    public ResponseEntity<SucursalInsumoEntity> setInitialQuantity(
            @PathVariable Integer sucursalId,
            @PathVariable Long insumoId,
            @RequestBody SucursalInsumoRequest request) {

        Optional<SucursalEntity> sucursalOptional = sucursalCrudRepository.findById(sucursalId);
        Optional<InsumoEntity> insumoOptional = insumoCrudRepository.findById(insumoId);

        if (!sucursalOptional.isPresent() || !insumoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<SucursalInsumoEntity> sucursalInsumos = sucursalInsumoCrudRepository.findBySucursalIdAndInsumoId(sucursalId, insumoId);
        if (!sucursalInsumos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        SucursalInsumoEntity sucursalInsumo = new SucursalInsumoEntity();
        sucursalInsumo.setSucursal(sucursalOptional.get());
        sucursalInsumo.setInsumo(insumoOptional.get());
        sucursalInsumo.setCantidad(request.getCantidad());
        sucursalInsumo.setStockMinimo(request.getStockMinimo());
        sucursalInsumo.setFechaIngreso(request.getFechaIngreso());
        sucursalInsumo.setFechaVencimiento(request.getFechaVencimiento());
        sucursalInsumo.setUltimaAdquisicion(request.getUltimaAdquisicion());
        SucursalInsumoEntity saved = sucursalInsumoCrudRepository.save(sucursalInsumo);
        return ResponseEntity.ok(saved);  // Respuesta exitosa
    }


    @DeleteMapping("/sucursal/{sucursalId}/insumo/{insumoId}")
    public ResponseEntity<Void> removeInsumoFromSucursal(@PathVariable Integer sucursalId, @PathVariable Long insumoId) {
        Optional<SucursalInsumoEntity> sucursalInsumoOptional = sucursalInsumoCrudRepository.findBySucursalIdAndInsumoId(sucursalId, insumoId);
        if(sucursalInsumoOptional.isPresent())
            return ResponseEntity.notFound().build();
        sucursalInsumoCrudRepository.delete(sucursalInsumoOptional.get());
        return ResponseEntity.noContent().build();

    }
    @PutMapping("/sucursal/{sucursalId}/insumo/{insumoId}/increase")
    public ResponseEntity<SucursalInsumoEntity> increaseQuantity(@PathVariable Integer sucursalId, @PathVariable Long insumoId, @RequestParam Integer cantidad) {
        Optional<SucursalInsumoEntity> sucursalInsumoOptional = sucursalInsumoCrudRepository.findBySucursalIdAndInsumoId(sucursalId, insumoId);
        if(!sucursalInsumoOptional.isPresent())
            return ResponseEntity.notFound().build();
        SucursalInsumoEntity sucursalInsumo = sucursalInsumoOptional.get();
        sucursalInsumo.setCantidad(sucursalInsumo.getCantidad().add(BigDecimal.valueOf(cantidad)));
        SucursalInsumoEntity updatedSucursalInsumo = sucursalInsumoCrudRepository.save(sucursalInsumo);
        return ResponseEntity.ok(updatedSucursalInsumo);
    }
    @PutMapping("/sucursal/{sucursalId}/insumo/{insumoId}/decrease")
    public ResponseEntity<SucursalInsumoEntity> decreaseQuantity(@PathVariable Integer sucursalId, @PathVariable Long insumoId, @RequestParam Integer cantidad) {
        Optional<SucursalInsumoEntity> sucursalInsumoOptional = sucursalInsumoCrudRepository.findBySucursalIdAndInsumoId(sucursalId, insumoId);
        if(!sucursalInsumoOptional.isPresent())
            return ResponseEntity.notFound().build();
        SucursalInsumoEntity sucursalInsumo = sucursalInsumoOptional.get();
        sucursalInsumo.setCantidad(sucursalInsumo.getCantidad().subtract(BigDecimal.valueOf(cantidad)));
        SucursalInsumoEntity updatedSucursalInsumo = sucursalInsumoCrudRepository.save(sucursalInsumo);
        return ResponseEntity.ok(updatedSucursalInsumo);
    }
    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<InsumoWithSucursalesDTO> findInsumoWithSucursales(@PathVariable Long insumoId) {
        List<SucursalInsumoEntity> sucursalInsumos = sucursalInsumoCrudRepository.findByInsumoId(insumoId);
        if (sucursalInsumos.isEmpty())
            return ResponseEntity.notFound().build();

        SucursalInsumoEntity firstInsumo = sucursalInsumos.get(0);
        InsumoWithSucursalesDTO insumoDTO = new InsumoWithSucursalesDTO();
        insumoDTO.setId(firstInsumo.getInsumo().getId());
        insumoDTO.setNombre(firstInsumo.getInsumo().getNombre());
        insumoDTO.setProveedor(firstInsumo.getInsumo().getProveedor());
        insumoDTO.setMarca(firstInsumo.getInsumo().getMarca());
        insumoDTO.setPrecio(firstInsumo.getInsumo().getPrecio());
        insumoDTO.setUnidades(firstInsumo.getInsumo().getUnidades());
        insumoDTO.setDescripcion(firstInsumo.getInsumo().getDescripcion());
        insumoDTO.setImagen(firstInsumo.getInsumo().getImagen());

        List<SucursalInfoDTO> sucursales = sucursalInsumos.stream().map(sucursalInsumo ->{
            SucursalInfoDTO sucursalInfo = new SucursalInfoDTO();
            sucursalInfo.setId(sucursalInsumo.getSucursal().getId());
            sucursalInfo.setNombre(sucursalInsumo.getSucursal().getNombre());
            sucursalInfo.setDepartamento(sucursalInsumo.getSucursal().getDepartamento());
            sucursalInfo.setCantidad(sucursalInsumo.getCantidad().intValue());
            return sucursalInfo;
        }).collect(Collectors.toList());
        insumoDTO.setSucursales(sucursales);
        return ResponseEntity.ok(insumoDTO);
    }
    @GetMapping("/insumo-with-sucursales")
    public ResponseEntity<List<InsumoWithSucursalesDTO>> findAllInsumoWithSucursales() {
        List<InsumoEntity> insumos = StreamSupport.stream(insumoCrudRepository.findAll().spliterator(), false).collect(Collectors.toList());
        List<InsumoWithSucursalesDTO> insumosWithSucursales = insumos.stream().map(
                insumo ->{
                    List<SucursalInsumoEntity> sucursalInsumos = sucursalInsumoCrudRepository.findByInsumoId(insumo.getId());
                    InsumoWithSucursalesDTO insumoDTO = new InsumoWithSucursalesDTO();
                    insumoDTO.setId(insumo.getId());
                    insumoDTO.setNombre(insumo.getNombre());
                    insumoDTO.setProveedor(insumo.getProveedor());
                    insumoDTO.setMarca(insumo.getMarca());
                    insumoDTO.setPrecio(insumo.getPrecio());
                    insumoDTO.setUnidades(insumo.getUnidades());
                    insumoDTO.setDescripcion(insumo.getDescripcion());
                    insumoDTO.setImagen(insumo.getImagen());

                    List<SucursalInfoDTO> sucursales = sucursalInsumos.stream().map(sucursalInsumo ->{
                        SucursalInfoDTO sucursalInfo = new SucursalInfoDTO();
                        sucursalInfo.setId(sucursalInsumo.getSucursal().getId());
                        sucursalInfo.setNombre(sucursalInsumo.getSucursal().getNombre());
                        sucursalInfo.setDepartamento(sucursalInsumo.getSucursal().getDepartamento());
                        sucursalInfo.setCantidad(sucursalInsumo.getCantidad().intValue());
                        return sucursalInfo;
                    }).collect(Collectors.toList());
                    insumoDTO.setSucursales(sucursales);
                    return insumoDTO;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(insumosWithSucursales);
    }
}
