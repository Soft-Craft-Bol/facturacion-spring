package com.gaspar.facturador.persistence.specification;

import com.gaspar.facturador.persistence.entity.AjusteInventarioInsumoEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Date;

public class AjusteInventarioInsumoSpecification {

    public static Specification<AjusteInventarioInsumoEntity> conSucursalId(Integer sucursalId) {
        return (root, query, criteriaBuilder) ->
                sucursalId == null ? null : criteriaBuilder.equal(root.get("sucursalInsumo").get("sucursal").get("id"), sucursalId);
    }

    public static Specification<AjusteInventarioInsumoEntity> conInsumoId(Long insumoId) {
        return (root, query, criteriaBuilder) ->
                insumoId == null ? null : criteriaBuilder.equal(root.get("sucursalInsumo").get("insumo").get("id"), insumoId);
    }

    public static Specification<AjusteInventarioInsumoEntity> conInsumoCodigo(String insumoCodigo) {
        return (root, query, criteriaBuilder) ->
                insumoCodigo == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("sucursalInsumo").get("insumo").get("codigo")),
                        "%" + insumoCodigo.toLowerCase() + "%"
                );
    }

    public static Specification<AjusteInventarioInsumoEntity> conFechaDesde(Date fechaDesde) {
        return (root, query, criteriaBuilder) ->
                fechaDesde == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("fechaAjuste"), fechaDesde);
    }

    public static Specification<AjusteInventarioInsumoEntity> conFechaHasta(Date fechaHasta) {
        return (root, query, criteriaBuilder) ->
                fechaHasta == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("fechaAjuste"), fechaHasta);
    }

    public static Specification<AjusteInventarioInsumoEntity> conUsuario(String usuarioResponsable) {
        return (root, query, criteriaBuilder) ->
                usuarioResponsable == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("usuarioResponsable")),
                        "%" + usuarioResponsable.toLowerCase() + "%"
                );
    }

    public static Specification<AjusteInventarioInsumoEntity> conTipoAjuste(String tipoAjuste) {
        return (root, query, criteriaBuilder) ->
                tipoAjuste == null ? null : criteriaBuilder.equal(root.get("tipoAjuste"), tipoAjuste);
    }

    public static Specification<AjusteInventarioInsumoEntity> construirSpecification(
            Integer sucursalId,
            Long insumoId,
            String insumoCodigo,
            Date fechaDesde,
            Date fechaHasta,
            String usuarioResponsable,
            String tipoAjuste) {

        return Specification.where(conSucursalId(sucursalId))
                .and(conInsumoId(insumoId))
                .and(conInsumoCodigo(insumoCodigo))
                .and(conFechaDesde(fechaDesde))
                .and(conFechaHasta(fechaHasta))
                .and(conUsuario(usuarioResponsable))
                .and(conTipoAjuste(tipoAjuste));
    }
}