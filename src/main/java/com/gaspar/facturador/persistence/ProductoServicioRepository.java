package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ProductosDto;
import com.gaspar.facturador.domain.repository.IProductoServicioRepository;
import com.gaspar.facturador.persistence.crud.ProductoServicioCrudRepository;
import com.gaspar.facturador.persistence.entity.ProductoServicioEntity;
import com.gaspar.facturador.persistence.mapper.ProductoServicioMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ProductoServicioRepository implements IProductoServicioRepository {

    private final ProductoServicioMapper productoServicioMapper;
    private final ProductoServicioCrudRepository productoServicioCrudRepository;

    public ProductoServicioRepository(ProductoServicioMapper productoServicioMapper, ProductoServicioCrudRepository productoServicioCrudRepository) {
        this.productoServicioMapper = productoServicioMapper;
        this.productoServicioCrudRepository = productoServicioCrudRepository;
    }

    @Override
    public void save(ProductosDto productosDto) {
        this.productoServicioCrudRepository.save(this.productoServicioMapper.toActividadEntity(productosDto));
    }

    @Override
    public void deleteAll() {
        this.productoServicioCrudRepository.deleteAll();
    }

    @Override
    public Optional<ProductoServicioEntity> findByCodigoProducto(Long codigoProducto) {
        return this.productoServicioCrudRepository.findByCodigoProducto(codigoProducto);
    }
}
