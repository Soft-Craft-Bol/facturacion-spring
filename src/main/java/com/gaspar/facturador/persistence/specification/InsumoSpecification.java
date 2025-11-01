package com.gaspar.facturador.persistence.specification;

import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class InsumoSpecification {

    public static Specification<SucursalInsumoEntity> bySucursalId(Long sucursalId) {
        return (root, query, criteriaBuilder) -> {
            if (sucursalId == null) return null;
            return criteriaBuilder.equal(root.get("sucursal").get("id"), sucursalId);
        };
    }

    public static Specification<SucursalInsumoEntity> byInsumoActivo(Boolean activo) {
        return (root, query, criteriaBuilder) -> {
            if (activo == null) return null;
            return criteriaBuilder.equal(root.get("insumo").get("activo"), activo);
        };
    }

    public static Specification<SucursalInsumoEntity> byNombreContaining(String nombre) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(nombre)) return null;

            Expression<String> nombreInsumo = root.get("insumo").get("nombre");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(nombreInsumo),
                    "%" + nombre.toLowerCase() + "%"
            );
        };
    }

    public static Specification<SucursalInsumoEntity> byTipo(TipoInsumo tipo) {
        return (root, query, criteriaBuilder) -> {
            if (tipo == null) return null;
            return criteriaBuilder.equal(root.get("insumo").get("tipo"), tipo);
        };
    }

    public static Specification<SucursalInsumoEntity> byUnidadesContaining(String unidades) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(unidades)) return null;

            // Forzamos el cast a String para evitar problemas con bytea
            Expression<String> unidadesInsumo = criteriaBuilder.treat(
                    root.get("insumo").get("unidades"), String.class
            );

            return criteriaBuilder.like(
                    criteriaBuilder.lower(unidadesInsumo),
                    "%" + unidades.toLowerCase() + "%"
            );
        };
    }

    // Método de conveniencia para combinar todas las condiciones
    public static Specification<SucursalInsumoEntity> withFilters(
            Long sucursalId,
            Boolean activo,
            String nombre,
            TipoInsumo tipo,
            String unidades) {

        return Specification.where(bySucursalId(sucursalId))
                .and(byInsumoActivo(activo))
                .and(byNombreContaining(nombre))
                .and(byTipo(tipo))
                .and(byUnidadesContaining(unidades));
    }
}