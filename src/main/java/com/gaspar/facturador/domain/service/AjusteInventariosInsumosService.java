package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.AjusteInventarioInsumoResponse;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioInsumoDTO;
import com.gaspar.facturador.application.rest.dto.AjusteInventarioInsumoFiltroDTO;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.AjusteInventarioInsumoRepository;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import com.gaspar.facturador.persistence.specification.AjusteInventarioInsumoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AjusteInventariosInsumosService {

    private final SucursalInsumoCrudRepository sucursalInsumoRepo;
    private final AjusteInventarioInsumoRepository ajusteRepo;
    private final InsumoCrudRepository insumoRepo;
    private final SucursalCrudRepository sucursalRepo;

    public AjusteInventariosInsumosService(
            SucursalInsumoCrudRepository sucursalInsumoRepo,
            AjusteInventarioInsumoRepository ajusteRepo,
            InsumoCrudRepository insumoRepo,
            SucursalCrudRepository sucursalRepo) {
        this.sucursalInsumoRepo = sucursalInsumoRepo;
        this.ajusteRepo = ajusteRepo;
        this.insumoRepo = insumoRepo;
        this.sucursalRepo = sucursalRepo;
    }

    @Transactional
    public void ajustarInventarioInsumoMasivo(List<AjusteInventarioInsumoDTO> ajustes) {
        for (AjusteInventarioInsumoDTO a : ajustes) {
            ajustarInventarioInsumo(
                    a.sucursalId(),
                    a.insumoId(),
                    a.cantidadAjuste(),
                    a.motivo(),
                    a.usuarioResponsable()
            );
        }
    }

    @Transactional
    public void ajustarInventarioInsumo(Integer sucursalId, Long insumoId, BigDecimal cantidadAjuste,
                                        String motivo, String usuario) {
        // Buscar las entidades completas desde la base de datos
        SucursalEntity sucursal = sucursalRepo.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + sucursalId));

        InsumoEntity insumo = insumoRepo.findById(insumoId)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + insumoId));

        // Buscar stock de insumo en la sucursal
        Optional<SucursalInsumoEntity> existingSucursalInsumo = sucursalInsumoRepo
                .findBySucursal_IdAndInsumo_Id(sucursalId, insumoId);

        SucursalInsumoEntity sucursalInsumo;
        BigDecimal cantidadAnterior;

        if (existingSucursalInsumo.isPresent()) {
            sucursalInsumo = existingSucursalInsumo.get();
            cantidadAnterior = sucursalInsumo.getCantidad();
        } else {
            sucursalInsumo = new SucursalInsumoEntity();
            sucursalInsumo.setSucursal(sucursal);
            sucursalInsumo.setInsumo(insumo);
            sucursalInsumo.setCantidad(BigDecimal.ZERO);
            cantidadAnterior = BigDecimal.ZERO;
        }

        BigDecimal cantidadNueva = cantidadAnterior.add(cantidadAjuste);

        // Validar que no quede stock negativo
        if (cantidadNueva.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("No se puede ajustar el inventario. Stock resultante sería negativo para el insumo: " + insumo.getNombre());
        }

        sucursalInsumo.setCantidad(cantidadNueva);
        sucursalInsumoRepo.save(sucursalInsumo);

        // Determinar tipo de ajuste y estado
        String tipoAjuste = determinarTipoAjuste(cantidadAjuste);
        String estadoAjuste = determinarEstadoAjuste(cantidadAnterior, cantidadNueva);

        // Registrar ajuste
        AjusteInventarioInsumoEntity ajuste = AjusteInventarioInsumoEntity.builder()
                .sucursalInsumo(sucursalInsumo)
                .cantidadAnterior(cantidadAnterior)
                .cantidadNueva(cantidadNueva)
                .diferencia(cantidadAjuste)
                .tipoAjuste(tipoAjuste)
                .motivo(motivo)
                .usuarioResponsable(usuario)
                .build();

        ajusteRepo.save(ajuste);
    }

    private String determinarTipoAjuste(BigDecimal cantidadAjuste) {
        if (cantidadAjuste.compareTo(BigDecimal.ZERO) > 0) {
            return "ENTRADA";
        } else if (cantidadAjuste.compareTo(BigDecimal.ZERO) < 0) {
            return "SALIDA";
        } else {
            return "AJUSTE";
        }
    }

    private String determinarEstadoAjuste(BigDecimal cantidadAnterior, BigDecimal cantidadNueva) {
        if (cantidadNueva.compareTo(cantidadAnterior) > 0) {
            return "INCREMENTO";
        } else if (cantidadNueva.compareTo(cantidadAnterior) < 0) {
            return "DECREMENTO";
        } else {
            return "SIN_CAMBIO";
        }
    }

    @Transactional(readOnly = true)
    public Page<AjusteInventarioInsumoResponse> listarAjustesConFiltros(AjusteInventarioInsumoFiltroDTO filtro, Pageable pageable) {
        // Convertir LocalDate a Date para las fechas
        Date fechaDesde = filtro.fechaDesde() != null ?
                Date.valueOf(filtro.fechaDesde()) : null;
        Date fechaHasta = filtro.fechaHasta() != null ?
                Date.valueOf(filtro.fechaHasta()) : null;

        // Crear Specification
        Specification<AjusteInventarioInsumoEntity> spec = AjusteInventarioInsumoSpecification.construirSpecification(
                filtro.sucursalId(),
                filtro.insumoId(),
                filtro.insumoCodigo(),
                fechaDesde,
                fechaHasta,
                filtro.usuarioResponsable(),
                filtro.tipoAjuste()
        );

        Page<AjusteInventarioInsumoEntity> ajustes = ajusteRepo.findAll(spec, pageable);

        return ajustes.map(this::convertToResponseDTO);
    }

    private AjusteInventarioInsumoResponse convertToResponseDTO(AjusteInventarioInsumoEntity entity) {
        SucursalInsumoEntity sucursalInsumo = entity.getSucursalInsumo();
        SucursalEntity sucursal = sucursalInsumo.getSucursal();
        InsumoEntity insumo = sucursalInsumo.getInsumo();

        return new AjusteInventarioInsumoResponse(
                entity.getId(),
                entity.getFechaAjuste(),
                // Información de sucursal
                sucursal.getId(),
                sucursal.getNombre(),
                String.format("%s, %s", sucursal.getDireccion(), sucursal.getMunicipio()),
                // Información del insumo
                insumo.getId(),
                generarCodigoInsumo(insumo), // Método para generar código si no existe
                insumo.getNombre(),
                generarDescripcionInsumo(insumo),
                insumo.getUnidades(),
                insumo.getTipo() != null ? insumo.getTipo().name() : "SIN_TIPO",
                // Datos del ajuste
                entity.getCantidadAnterior(),
                entity.getCantidadNueva(),
                entity.getDiferencia(),
                entity.getTipoAjuste(),
                entity.getMotivo(),
                entity.getUsuarioResponsable(),
                entity.getCantidadNueva(),
                // Información adicional
                determinarEstadoAjuste(entity.getCantidadAnterior(), entity.getCantidadNueva())
        );
    }

    private String generarCodigoInsumo(InsumoEntity insumo) {
        // Si no hay código específico, generar uno basado en ID y nombre
        return String.format("INS-%03d-%s", insumo.getId(),
                insumo.getNombre().substring(0, Math.min(3, insumo.getNombre().length())).toUpperCase());
    }

    private String generarDescripcionInsumo(InsumoEntity insumo) {
        StringBuilder descripcion = new StringBuilder();
        descripcion.append(insumo.getNombre());

        if (insumo.getTipo() != null) {
            descripcion.append(" - ").append(insumo.getTipo());
        }

        if (insumo.getUnidades() != null) {
            descripcion.append(" (").append(insumo.getUnidades()).append(")");
        }

        return descripcion.toString();
    }

}