package com.gaspar.facturador.persistence.specification;

import com.gaspar.facturador.persistence.entity.AjusteInventarioEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AjusteInventarioSpecification {

    public static Specification<AjusteInventarioEntity> conSucursalId(Integer sucursalId) {
        return (root, query, criteriaBuilder) ->
                sucursalId == null ? null : criteriaBuilder.equal(root.get("sucursalItem").get("sucursal").get("id"), sucursalId);
    }

    public static Specification<AjusteInventarioEntity> conItemId(Integer itemId) {
        return (root, query, criteriaBuilder) ->
                itemId == null ? null : criteriaBuilder.equal(root.get("sucursalItem").get("item").get("id"), itemId);
    }

    public static Specification<AjusteInventarioEntity> conItemCodigo(String itemCodigo) {
        return (root, query, criteriaBuilder) ->
                itemCodigo == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("sucursalItem").get("item").get("codigo")),
                        "%" + itemCodigo.toLowerCase() + "%"
                );
    }

    public static Specification<AjusteInventarioEntity> conFechaDesde(LocalDateTime fechaDesde) {
        return (root, query, criteriaBuilder) ->
                fechaDesde == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), fechaDesde);
    }

    public static Specification<AjusteInventarioEntity> conFechaHasta(LocalDateTime fechaHasta) {
        return (root, query, criteriaBuilder) ->
                fechaHasta == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), fechaHasta);
    }

    public static Specification<AjusteInventarioEntity> conUsuario(String usuario) {
        return (root, query, criteriaBuilder) ->
                usuario == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("usuario")),
                        "%" + usuario.toLowerCase() + "%"
                );
    }

    public static Specification<AjusteInventarioEntity> construirSpecification(
            Integer sucursalId,
            Integer itemId,
            String itemCodigo,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            String usuario) {

        return Specification.where(conSucursalId(sucursalId))
                .and(conItemId(itemId))
                .and(conItemCodigo(itemCodigo))
                .and(conFechaDesde(fechaDesde))
                .and(conFechaHasta(fechaHasta))
                .and(conUsuario(usuario));
    }
}