package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.application.response.ProductoMasVendidoDTO;
import com.gaspar.facturador.persistence.entity.VentasDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VentasDetalleRepository extends JpaRepository<VentasDetalleEntity, Long> {

    @Query("""
    SELECT new com.gaspar.facturador.application.response.ProductoMasVendidoDTO(
        p.id, 
        p.descripcion, 
        p.imagen, 
        p.precioUnitario, 
        SUM(d.cantidad)
    )
    FROM VentasDetalleEntity d
    JOIN d.producto p
    GROUP BY p.id, p.descripcion, p.imagen, p.precioUnitario
    ORDER BY SUM(d.cantidad) DESC
""")
    List<ProductoMasVendidoDTO> findTop10ProductosMasVendidos();

}
