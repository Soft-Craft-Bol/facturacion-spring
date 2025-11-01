package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.application.response.ClienteFrecuenteDTO;
import com.gaspar.facturador.application.rest.dto.ReporteProductoDTO;
import com.gaspar.facturador.persistence.dto.ReporteVentasDTO;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.persistence.TemporalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;

public interface VentaCrudRepository extends JpaRepository<VentasEntity, Long>, JpaSpecificationExecutor<VentasEntity> {
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
    FROM ventas
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

    List<VentasEntity> findByCajaId(Long cajaId);

    @Query("SELECT new com.gaspar.facturador.application.rest.dto.ReporteProductoDTO(" +
            "p.id, " +
            "vd.descripcionProducto, " +
            "SUM(vd.cantidad), " +
            "SUM(vd.cantidad * vd.precioUnitario - vd.montoDescuento)) " +
            "FROM VentasDetalleEntity vd " +
            "JOIN vd.producto p " +
            "JOIN vd.venta v " +
            "WHERE v.caja.id = :cajaId " +
            "AND v.estado = 'COMPLETADO' " +
            "GROUP BY p.id, vd.descripcionProducto " +
            "ORDER BY SUM(vd.cantidad) DESC")
    List<ReporteProductoDTO> findProductosVendidosPorCaja(@Param("cajaId") Long cajaId);

}