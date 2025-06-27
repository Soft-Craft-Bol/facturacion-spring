package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.InsumoRequest;
import com.gaspar.facturador.application.response.InsumoResponse;
import com.gaspar.facturador.application.response.InsumoSucursalResponse;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumoService {
    private final InsumoCrudRepository insumoRepository;

    @Transactional
    public InsumoResponse createInsumo(InsumoRequest request) {
        if(insumoRepository.existsByNombre(request.getNombre())) {
            throw new IllegalStateException("Ya existe un insumo con el nombre: " + request.getNombre());
        }

        InsumoEntity insumo = new InsumoEntity();
        insumo.setNombre(request.getNombre());
        insumo.setTipo(request.getTipo());
        insumo.setPrecioActual(request.getPrecioActual());
        insumo.setUnidades(request.getUnidades());
        insumo.setImagen(request.getImagen());
        insumo.setActivo(true);

        insumo = insumoRepository.save(insumo);
        return mapToResponse(insumo);
    }

    public InsumoResponse getInsumoById(Long id) throws ChangeSetPersister.NotFoundException {
        InsumoEntity insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return mapToResponse(insumo);
    }

    public Page<InsumoResponse> getAllInsumos(Pageable pageable) {
        return insumoRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<InsumoResponse> getInsumosActivos(Pageable pageable) {
        return insumoRepository.findByActivoTrue(pageable)
                .map(this::mapToResponse);
    }

    public List<InsumoSucursalResponse> getInsumosBySucursal(Long sucursalId) {
        List<SucursalInsumoEntity> sucursalInsumos = insumoRepository.findInsumosBySucursalId(sucursalId);
        return sucursalInsumos.stream()
                .map(this::mapToInsumoSucursalResponse)
                .collect(Collectors.toList());
    }

    private InsumoResponse mapToResponse(InsumoEntity insumo) {
        return InsumoResponse.builder()
                .id(insumo.getId())
                .nombre(insumo.getNombre())
                .tipo(insumo.getTipo())
                .precioActual(insumo.getPrecioActual())
                .unidades(insumo.getUnidades())
                .imagen(insumo.getImagen())
                .activo(insumo.getActivo())
                .build();
    }

    private InsumoSucursalResponse mapToInsumoSucursalResponse(SucursalInsumoEntity sucursalInsumo) {
        return InsumoSucursalResponse.builder()
                .insumoId(sucursalInsumo.getInsumo().getId())
                .nombre(sucursalInsumo.getInsumo().getNombre())
                .tipo(sucursalInsumo.getInsumo().getTipo())
                .unidades(sucursalInsumo.getInsumo().getUnidades())
                .cantidad(sucursalInsumo.getCantidad())
                .stockMinimo(sucursalInsumo.getStockMinimo())
                .fechaVencimiento(sucursalInsumo.getFechaVencimiento())
                .build();
    }
}