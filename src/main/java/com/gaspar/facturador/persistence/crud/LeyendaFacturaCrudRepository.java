package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.LeyendaFacturaEntity;
import com.gaspar.facturador.persistence.view.LeyendaView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface LeyendaFacturaCrudRepository extends CrudRepository<LeyendaFacturaEntity, Long> {

    @Query(
        value = "select" +
                "   RANDOM() as random, l.descripcion_leyenda as leyenda " +
                "from leyenda_factura l " +
                "where l.codigo_actividad = :codigoActividad " +
                "ORDER BY random LIMIT 1",
        nativeQuery = true
    )
    Optional<LeyendaView> obtenerLeyenda(@Param("codigoActividad") String codigoActividad);
}
