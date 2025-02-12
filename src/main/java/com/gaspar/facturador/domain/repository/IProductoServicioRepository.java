package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ProductosDto;
import com.gaspar.facturador.persistence.entity.ProductoServicioEntity;

import java.util.Optional;


public interface IProductoServicioRepository {

    void save(ProductosDto productosDto);

    void deleteAll();

    Optional<ProductoServicioEntity> findByCodigoProducto(Long codigoProducto);
}
