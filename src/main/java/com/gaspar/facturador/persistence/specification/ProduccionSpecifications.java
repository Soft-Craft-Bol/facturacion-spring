package com.gaspar.facturador.persistence.specification;

import com.gaspar.facturador.persistence.entity.ProduccionEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ProduccionSpecifications {

    public static Specification<ProduccionEntity> withFilters(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Long recetaId,
            Long productoId,
            Long sucursalId) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fechaInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), fechaInicio));
            }

            if (fechaFin != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), fechaFin));
            }

            if (recetaId != null) {
                predicates.add(cb.equal(root.get("receta").get("id"), recetaId));
            }

            if (productoId != null) {
                predicates.add(cb.equal(root.get("producto").get("id"), productoId));
            }

            if (sucursalId != null) {
                predicates.add(cb.equal(root.get("sucursal").get("id"), sucursalId));
            }

            // Agregar ordenación descendente por fecha
            query.orderBy(cb.desc(root.get("fecha")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}