package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.CompraInsumoRequest;
import com.gaspar.facturador.application.response.CompraInsumoResponse;
import com.gaspar.facturador.application.response.PagedResponse;
import com.gaspar.facturador.persistence.crud.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraInsumoService {

    private final CompraInsumoCrudRepository compraInsumoRepository;
    private final InsumoCrudRepository insumoRepository;
    private final SucursalCrudRepository sucursalRepository;
    private final SucursalInsumoCrudRepository sucursalInsumoRepository;
    private final ProveedorRepository proveedorRepository;
    private final GastoRepository gastoRepository;
    private final SucursalInsumoService sucursalInsumoService;

    @Transactional
    public void registrarCompraInsumo(CompraInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        // Validar y obtener entidades relacionadas
        InsumoEntity insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        SucursalEntity sucursal = sucursalRepository.findById(Math.toIntExact(request.getSucursalId()))
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        ProveedorEntity proveedor = request.getProveedorId() != null ?
                proveedorRepository.findById(request.getProveedorId())
                        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado")) : null;

        // Registrar gasto
        GastoEntity gasto = crearGasto(request, insumo, sucursal, proveedor);
        gasto = gastoRepository.save(gasto);

        // Registrar compra
        CompraInsumoEntity compra = crearCompra(request, insumo, sucursal, proveedor, gasto);
        compraInsumoRepository.save(compra);

        // Actualizar stock
        actualizarStockYprecio(insumo, sucursal, request);
    }

    public CompraInsumoResponse obtenerCompraPorId(Long id) {
        CompraInsumoEntity compra = compraInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        return mapToResponse(compra);
    }

    public PagedResponse<CompraInsumoResponse> listarComprasConFiltros(
            Long sucursalId, Long proveedorId, LocalDate fechaInicio,
            LocalDate fechaFin, TipoInsumo tipoInsumo, Pageable pageable) {

        Specification<CompraInsumoEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (sucursalId != null) {
                predicates.add(cb.equal(root.get("sucursal").get("id"), sucursalId));
            }

            if (proveedorId != null) {
                predicates.add(cb.equal(root.get("proveedor").get("id"), proveedorId));
            }

            if (fechaInicio != null && fechaFin != null) {
                predicates.add(cb.between(root.get("fecha"), fechaInicio, fechaFin));
            } else if (fechaInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), fechaInicio));
            } else if (fechaFin != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), fechaFin));
            }

            if (tipoInsumo != null) {
                Join<CompraInsumoEntity, InsumoEntity> insumoJoin = root.join("insumo");
                predicates.add(cb.equal(insumoJoin.get("tipo"), tipoInsumo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<CompraInsumoEntity> comprasPage = compraInsumoRepository.findAll(spec, pageable);

        List<CompraInsumoResponse> content = comprasPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                comprasPage.getNumber(),
                comprasPage.getSize(),
                comprasPage.getTotalElements(),
                comprasPage.getTotalPages(),
                comprasPage.isLast()
        );
    }

    @Transactional
    public void actualizarCompra(Long id, CompraInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        CompraInsumoEntity compra = compraInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        // Revertir stock anterior
        sucursalInsumoService.updateStockInsumo(
                compra.getSucursal().getId(),
                compra.getInsumo().getId(),
                compra.getCantidad().negate());

        // Actualizar compra y gasto
        actualizarEntidades(compra, request);

        // Aplicar nuevos valores de stock
        actualizarStockYprecio(compra.getInsumo(), compra.getSucursal(), request);
    }

    @Transactional
    public void eliminarCompra(Long id) throws ChangeSetPersister.NotFoundException {
        CompraInsumoEntity compra = compraInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        // Revertir stock
        sucursalInsumoService.updateStockInsumo(
                compra.getSucursal().getId(),
                compra.getInsumo().getId(),
                compra.getCantidad().negate());

        // Eliminar compra y gasto asociado
        compraInsumoRepository.delete(compra);
        gastoRepository.delete(compra.getGasto());
    }

    private GastoEntity crearGasto(CompraInsumoRequest request, InsumoEntity insumo,
                                   SucursalEntity sucursal, ProveedorEntity proveedor) {
        GastoEntity gasto = new GastoEntity();
        gasto.setDescripcion("Compra de " + insumo.getNombre());
        gasto.setMonto(request.getPrecioUnitario().multiply(BigDecimal.valueOf(request.getCantidad())));
        gasto.setFecha(LocalDate.now());
        gasto.setCategoria(insumo.getTipo() == TipoInsumo.MATERIA_PRIMA ?
                GastoEnum.COMPRA_MATERIA_PRIMA : GastoEnum.COMPRA_PRODUCTOS_TERMINADOS);
        gasto.setProveedor(proveedor);
        gasto.setSucursal(sucursal);
        gasto.setNumeroFactura(request.getNumeroFactura());
        gasto.setNotas(request.getNotas());
        return gasto;
    }

    private CompraInsumoEntity crearCompra(CompraInsumoRequest request, InsumoEntity insumo,
                                           SucursalEntity sucursal, ProveedorEntity proveedor,
                                           GastoEntity gasto) {
        CompraInsumoEntity compra = new CompraInsumoEntity();
        compra.setCantidad(BigDecimal.valueOf(request.getCantidad()));
        compra.setPrecioUnitario(request.getPrecioUnitario());
        compra.setFecha(LocalDate.now());
        compra.setGasto(gasto);
        compra.setInsumo(insumo);
        compra.setSucursal(sucursal);
        compra.setProveedor(proveedor);
        return compra;
    }

    private void actualizarStockYprecio(InsumoEntity insumo, SucursalEntity sucursal,
                                        CompraInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        // Actualizar stock en sucursal
        sucursalInsumoService.updateStockInsumo(
                sucursal.getId(),
                insumo.getId(),
                BigDecimal.valueOf(request.getCantidad()));

        // Actualizar precio si es diferente
        if (request.getPrecioUnitario().compareTo(insumo.getPrecioActual()) != 0) {
            insumo.setPrecioActual(request.getPrecioUnitario());
            insumoRepository.save(insumo);
        }

        // Actualizar fecha de vencimiento (si viene en la request)
        if (request.getFechaVencimiento() != null) {
            Optional<SucursalInsumoEntity> sucursalInsumoOpt = sucursalInsumoRepository
                    .findBySucursalIdAndInsumoId(sucursal.getId(), insumo.getId());

            if (sucursalInsumoOpt.isPresent()) {
                SucursalInsumoEntity sucursalInsumo = sucursalInsumoOpt.get();
                sucursalInsumo.setFechaVencimiento(request.getFechaVencimiento());
                sucursalInsumoRepository.save(sucursalInsumo);
            }
        }
    }

    private void actualizarEntidades(CompraInsumoEntity compra, CompraInsumoRequest request) {
        // Actualizar compra
        compra.setCantidad(BigDecimal.valueOf(request.getCantidad()));
        compra.setPrecioUnitario(request.getPrecioUnitario());
        compraInsumoRepository.save(compra);

        // Actualizar gasto asociado
        GastoEntity gasto = compra.getGasto();
        gasto.setMonto(request.getPrecioUnitario().multiply(BigDecimal.valueOf(request.getCantidad())));
        gasto.setNumeroFactura(request.getNumeroFactura());
        gasto.setNotas(request.getNotas());
        gastoRepository.save(gasto);
    }

    private CompraInsumoResponse mapToResponse(CompraInsumoEntity compra) {
        CompraInsumoResponse response = new CompraInsumoResponse();
        response.setId(compra.getId());
        response.setInsumoId(compra.getInsumo().getId());
        response.setInsumoNombre(compra.getInsumo().getNombre());
        response.setTipoInsumo(compra.getInsumo().getTipo());
        response.setSucursalId(Long.valueOf(compra.getSucursal().getId()));
        response.setSucursalNombre(compra.getSucursal().getNombre());

        if (compra.getProveedor() != null) {
            response.setProveedorId(compra.getProveedor().getId());
            response.setProveedorNombre(compra.getProveedor().getNombreRazonSocial());
        }

        response.setCantidad(compra.getCantidad());
        response.setPrecioUnitario(compra.getPrecioUnitario());
        response.setFecha(compra.getFecha());
        response.setNumeroFactura(compra.getGasto().getNumeroFactura());
        response.setNotas(compra.getGasto().getNotas());
        response.setTotal(compra.getPrecioUnitario().multiply(compra.getCantidad()));

        return response;
    }
}