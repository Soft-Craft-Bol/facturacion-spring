package com.gaspar.facturador.utils;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.VentasDetalleEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VentaSpecifications {

    public static Specification<VentasEntity> hasFactura() {
        return (root, query, cb) -> cb.isNotNull(root.get("factura"));
    }

    public static Specification<VentasEntity> withFechaDesde(LocalDate fecha) {
        return (root, query, cb) -> fecha == null ?
                null : cb.greaterThanOrEqualTo(root.get("fecha"), fecha.atStartOfDay());
    }

    public static Specification<VentasEntity> withFechaHasta(LocalDate fecha) {
        return (root, query, cb) -> fecha == null ?
                null : cb.lessThanOrEqualTo(root.get("fecha"), fecha.plusDays(1).atStartOfDay());
    }

    public static Specification<VentasEntity> withEstadoFactura(String estado) {
        return (root, query, cb) -> estado == null ?
                null : cb.equal(root.join("factura").get("estado"), estado);
    }

    public static Specification<VentasEntity> withMetodoPago(TipoPagoEnum metodo) {
        return (root, query, cb) -> metodo == null ?
                null : cb.equal(root.get("metodoPago"), metodo);
    }

    public static Specification<VentasEntity> withCodigoCliente(String codigo) {
        return (root, query, cb) -> codigo == null ?
                null : cb.equal(root.join("cliente").get("codigoCliente"), codigo);
    }

    public static Specification<VentasEntity> withCodigoProducto(String codigo) {
        return (root, query, cb) -> {
            if (codigo == null) {
                return null;
            }

            Subquery<VentasDetalleEntity> subquery = query.subquery(VentasDetalleEntity.class);
            Root<VentasDetalleEntity> detalle = subquery.from(VentasDetalleEntity.class);
            Join<VentasDetalleEntity, ItemEntity> producto = detalle.join("producto");

            subquery.select(detalle)
                    .where(
                            cb.equal(detalle.get("venta"), root),
                            cb.equal(producto.get("codigo"), codigo)
                    );

            return cb.exists(subquery);
        };
    }

    public static Specification<VentasEntity> withMontoMin(BigDecimal monto) {
        return (root, query, cb) -> monto == null ?
                null : cb.greaterThanOrEqualTo(root.get("monto"), monto);
    }

    public static Specification<VentasEntity> withMontoMax(BigDecimal monto) {
        return (root, query, cb) -> monto == null ?
                null : cb.lessThanOrEqualTo(root.get("monto"), monto);
    }

    public static Specification<VentasEntity> hasNoFactura() {
        return (root, query, cb) -> cb.isNull(root.get("factura"));
    }
}