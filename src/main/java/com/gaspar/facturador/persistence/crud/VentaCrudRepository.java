package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.application.response.ClienteFrecuenteDTO;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;

public interface VentaCrudRepository extends JpaRepository<VentasEntity, Long> {
    // Métodos base
    Page<VentasEntity> findByFechaBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    // Métodos con filtros combinados
    Page<VentasEntity> findByFechaBetweenAndPuntoVentaId(Date fechaInicio, Date fechaFin, Integer idPuntoVenta, Pageable pageable);

    Page<VentasEntity> findByFechaBetweenAndEstado(Date fechaInicio, Date fechaFin, String estado, Pageable pageable);

    Page<VentasEntity> findByFechaBetweenAndPuntoVentaIdAndEstado(
            Date fechaInicio, Date fechaFin, Integer idPuntoVenta, String estado, Pageable pageable);

    // Métodos para búsqueda de productos/clientes
    @Query("SELECT v FROM VentasEntity v JOIN v.detalles d JOIN d.producto p " +
            "WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "AND (p.descripcion LIKE %:busqueda% OR p.codigo LIKE %:busqueda%)")
    Page<VentasEntity> findByFechaAndProducto(
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("busqueda") String busqueda,
            Pageable pageable);

    @Query("SELECT v FROM VentasEntity v JOIN v.cliente c " +
            "WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "AND (c.nombreRazonSocial LIKE %:busqueda% OR c.codigoCliente LIKE %:busqueda%)")
    Page<VentasEntity> findByFechaAndCliente(
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("busqueda") String busqueda,
            Pageable pageable);

    // Método default para simplificar
    default Page<VentasEntity> findByFechaAndFiltros(
            LocalDate fecha,
            Integer idPuntoVenta,
            String estado,
            String busqueda,
            String tipoBusqueda,
            Pageable pageable) {

        Date fechaInicio = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fecha.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (busqueda != null && !busqueda.isEmpty()) {
            if ("producto".equalsIgnoreCase(tipoBusqueda)) {
                return findByFechaAndProducto(fechaInicio, fechaFin, busqueda, pageable);
            } else if ("cliente".equalsIgnoreCase(tipoBusqueda)) {
                return findByFechaAndCliente(fechaInicio, fechaFin, busqueda, pageable);
            }
        }

        if (idPuntoVenta != null && estado != null) {
            return findByFechaBetweenAndPuntoVentaIdAndEstado(fechaInicio, fechaFin, idPuntoVenta, estado, pageable);
        } else if (idPuntoVenta != null) {
            return findByFechaBetweenAndPuntoVentaId(fechaInicio, fechaFin, idPuntoVenta, pageable);
        } else if (estado != null) {
            return findByFechaBetweenAndEstado(fechaInicio, fechaFin, estado, pageable);
        }

        return findByFechaBetween(fechaInicio, fechaFin, pageable);
    }

    @Query(value = """
    SELECT 
        to_char(fecha, 'YYYY-MM-DD') as fecha, 
        SUM(monto) as total
    FROM ventas_entity
    GROUP BY to_char(fecha, 'YYYY-MM-DD')
    ORDER BY fecha
    """, nativeQuery = true)
    List<TotalVentasPorDiaDTO> findVentasAgrupadasPorDia();

    @Query("""
    SELECT new com.gaspar.facturador.application.response.ClienteFrecuenteDTO(
        c.id,
        c.nombreRazonSocial,
        COUNT(v.id)
    )
    FROM VentasEntity v
    JOIN v.cliente c
    GROUP BY c.id, c.nombreRazonSocial
    ORDER BY COUNT(v.id) DESC
    """)
    List<ClienteFrecuenteDTO> findTop10ClientesFrecuentes();

    @Query("SELECT v FROM VentasEntity v " +
            "WHERE (:idPuntoVenta IS NULL OR v.puntoVenta.id = :idPuntoVenta) " +
            "AND (:fechaInicio IS NULL OR v.fecha >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR v.fecha < :fechaFin) " +
            "AND (:estado IS NULL OR v.estado = :estado OR " +
            "     (v.factura IS NOT NULL AND v.factura.estado = :estado)) " +
            "AND (:productoNombre IS NULL OR EXISTS " +
            "     (SELECT 1 FROM v.detalles d WHERE " +
            "      LOWER(d.descripcionProducto) LIKE LOWER(CONCAT('%', :productoNombre, '%')))) " +
            "AND (:nombreCliente IS NULL OR LOWER(v.cliente.nombreRazonSocial) LIKE LOWER(CONCAT('%', :nombreCliente, '%')))")
    Page<VentasEntity> findByFiltros(
            @Param("idPuntoVenta") Long idPuntoVenta,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("estado") String estado,
            @Param("productoNombre") String productoNombre,
            @Param("nombreCliente") String nombreCliente,
            Pageable pageable);
}