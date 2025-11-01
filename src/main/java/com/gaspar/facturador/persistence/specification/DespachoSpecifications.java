package com.gaspar.facturador.persistence.specification;

import com.gaspar.facturador.persistence.entity.DespachoEntity;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;

public class DespachoSpecifications {

    public static Specification<DespachoEntity> fechaBetween(Date inicio, Date fin) {
        return (root, query, cb) -> {
            if (inicio == null || fin == null) return null;
            return cb.between(root.get("fechaEnvio"), inicio, fin);
        };
    }

    public static Specification<DespachoEntity> transporteEquals(String transporte) {
        return (root, query, cb) -> {
            if (transporte == null || transporte.isEmpty()) return null;
            return cb.equal(cb.lower(root.get("transporte")), transporte.toLowerCase());
        };
    }

    public static Specification<DespachoEntity> numeroContactoEquals(Long numero) {
        return (root, query, cb) -> numero == null ? null : cb.equal(root.get("numeroContacto"), numero);
    }

    public static Specification<DespachoEntity> sucursalOrigenEquals(Long idSucursal) {
        return (root, query, cb) -> idSucursal == null ? null : cb.equal(root.get("sucursalOrigen").get("id"), idSucursal);
    }

    public static Specification<DespachoEntity> sucursalDestinoEquals(Long idSucursal) {
        return (root, query, cb) -> idSucursal == null ? null : cb.equal(root.get("sucursalDestino").get("id"), idSucursal);
    }

    public static Specification<DespachoEntity> contieneItem(Long itemId) {
        return (root, query, cb) -> {
            if (itemId == null) return null;
            query.distinct(true);
            return cb.equal(root.join("despachoItems").get("item").get("id"), itemId);
        };
    }
}
