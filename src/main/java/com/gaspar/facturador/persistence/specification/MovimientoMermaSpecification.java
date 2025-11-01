package com.gaspar.facturador.persistence.specification;

import com.gaspar.facturador.persistence.entity.MovimientoMermaEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoMerma;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class MovimientoMermaSpecification {

    public static Specification<MovimientoMermaEntity> sucursalIdEquals(Integer sucursalId) {
        return (root, query, cb) -> sucursalId != null ?
                cb.equal(root.get("sucursal").get("id"), sucursalId) : null;
    }

    public static Specification<MovimientoMermaEntity> tipoEquals(TipoMerma tipo) {
        return (root, query, cb) -> tipo != null ? cb.equal(root.get("tipo"), tipo) : null;
    }

    public static Specification<MovimientoMermaEntity> motivoLike(String motivo) {
        return (root, query, cb) -> motivo != null && !motivo.isBlank() ?
                cb.like(cb.lower(root.get("motivo")), "%" + motivo.toLowerCase() + "%") : null;
    }

    public static Specification<MovimientoMermaEntity> fechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return (root, query, cb) -> {
            if (inicio != null && fin != null) {
                return cb.between(root.get("fecha"), inicio, fin);
            } else if (inicio != null) {
                return cb.greaterThanOrEqualTo(root.get("fecha"), inicio);
            } else if (fin != null) {
                return cb.lessThanOrEqualTo(root.get("fecha"), fin);
            }
            return null;
        };
    }
}
