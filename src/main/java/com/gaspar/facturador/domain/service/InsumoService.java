package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.InsumoRequest;
import com.gaspar.facturador.application.response.InsumoResponse;
import com.gaspar.facturador.application.response.InsumoSucursalResponse;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

        InsumoEntity insumo = InsumoEntity.builder()
                .nombre(request.getNombre())
                .tipo(request.getTipo())
                .precioActual(request.getPrecioActual())
                .cantidad(request.getCantidad())
                .unidades(request.getUnidades())
                .imagen(request.getImagen())
                .activo(true)
                .build();

        return mapToResponse(insumoRepository.save(insumo));
    }

    public InsumoResponse getInsumoById(Long id) {
        return insumoRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con ID: " + id));
    }

    public InsumoResponse getActiveInsumoById(Long id) {
        return insumoRepository.findByIdAndActivoTrue(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Insumo activo no encontrado con ID: " + id));
    }

    @Transactional
    public InsumoResponse updateInsumo(Long id, InsumoRequest request) {
        InsumoEntity insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con ID: " + id));

        if (!insumo.getNombre().equals(request.getNombre()) &&
                insumoRepository.existsByNombreAndIdNot(request.getNombre(), id)) {
            throw new IllegalStateException("Ya existe un insumo con el nombre: " + request.getNombre());
        }

        insumo.setNombre(request.getNombre());
        insumo.setTipo(request.getTipo());
        insumo.setPrecioActual(request.getPrecioActual());
        insumo.setCantidad(request.getCantidad());
        insumo.setUnidades(request.getUnidades());
        insumo.setImagen(request.getImagen());

        return mapToResponse(insumoRepository.save(insumo));
    }

    @Transactional
    public void toggleInsumoStatus(Long id, boolean activo) {
        insumoRepository.findById(id)
                .ifPresentOrElse(
                        insumo -> {
                            insumo.setActivo(activo);
                            insumoRepository.save(insumo);
                        },
                        () -> { throw new EntityNotFoundException("Insumo no encontrado con ID: " + id); }
                );
    }

    @Transactional
    public void deleteInsumo(Long id) {
        if (!insumoRepository.existsById(id)) {
            throw new EntityNotFoundException("Insumo no encontrado con ID: " + id);
        }
        insumoRepository.deleteById(id);
    }

    public List<InsumoResponse> getActiveInsumos() {
        return insumoRepository.findByActivoTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<InsumoResponse> getAllInsumos(String nombre, String tipo, Boolean activo, Pageable pageable) {
        String nombreFiltro = StringUtils.isBlank(nombre) ? null : nombre.trim();
        TipoInsumo tipoEnum = parseTipoInsumo(tipo);

        if (activo == null) {
            return searchAllStatus(nombreFiltro, tipoEnum, pageable);
        } else {
            return searchByActiveStatus(nombreFiltro, tipoEnum, activo, pageable);
        }
    }

    public Page<InsumoSucursalResponse> getInsumosBySucursal(
            Long sucursalId,
            boolean soloActivos,
            Pageable pageable) {

        Page<SucursalInsumoEntity> insumosPage = soloActivos ?
                insumoRepository.findActiveInsumosBySucursalId(sucursalId, pageable) :
                insumoRepository.findInsumosBySucursalId(sucursalId, pageable);

        return insumosPage.map(this::mapToInsumoSucursalResponse);
    }

    public Page<InsumoSucursalResponse> getInsumosBySucursalExcludingMateriaPrima(
            Long sucursalId,
            boolean soloActivos,
            Pageable pageable) {

        Page<SucursalInsumoEntity> insumosPage = soloActivos ?
                insumoRepository.findActiveInsumosBySucursalIdExcludingMateriaPrima(sucursalId, pageable) :
                insumoRepository.findInsumosBySucursalIdExcludingMateriaPrima(sucursalId, pageable);

        return insumosPage.map(this::mapToInsumoSucursalResponse);
    }
    // Métodos auxiliares
    private TipoInsumo parseTipoInsumo(String tipo) {
        if (StringUtils.isBlank(tipo)) return null;
        try {
            return TipoInsumo.valueOf(tipo);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de insumo inválido: " + tipo);
        }
    }

    private Page<InsumoResponse> searchAllStatus(String nombre, TipoInsumo tipo, Pageable pageable) {
        if (nombre == null && tipo == null) {
            return insumoRepository.findAll(pageable).map(this::mapToResponse);
        } else if (nombre != null && tipo == null) {
            return insumoRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                    .map(this::mapToResponse);
        } else if (nombre == null) {
            return insumoRepository.findByTipo(tipo, pageable)
                    .map(this::mapToResponse);
        } else {
            return insumoRepository.findByNombreContainingIgnoreCaseAndTipo(nombre, tipo, pageable)
                    .map(this::mapToResponse);
        }
    }

    private Page<InsumoResponse> searchByActiveStatus(String nombre, TipoInsumo tipo, boolean activo, Pageable pageable) {
        if (nombre == null && tipo == null) {
            return insumoRepository.findAllByActivo(activo, pageable).map(this::mapToResponse);
        } else if (nombre != null && tipo == null) {
            return insumoRepository.findByNombreContainingIgnoreCaseAndActivo(nombre, activo, pageable)
                    .map(this::mapToResponse);
        } else if (nombre == null) {
            return insumoRepository.findByTipoAndActivo(tipo, activo, pageable)
                    .map(this::mapToResponse);
        } else {
            return insumoRepository.findByNombreContainingIgnoreCaseAndTipoAndActivo(nombre, tipo, activo, pageable)
                    .map(this::mapToResponse);
        }
    }

    private InsumoResponse mapToResponse(InsumoEntity insumo) {
        return InsumoResponse.builder()
                .id(insumo.getId())
                .nombre(insumo.getNombre())
                .tipo(insumo.getTipo())
                .precioActual(insumo.getPrecioActual())
                .cantidad(insumo.getCantidad())
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
                .activo(sucursalInsumo.getInsumo().getActivo())
                .build();
    }
}