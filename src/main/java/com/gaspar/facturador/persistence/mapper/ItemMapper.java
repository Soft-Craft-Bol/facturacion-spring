package com.gaspar.facturador.persistence.mapper;

import com.gaspar.facturador.application.response.ItemResponse;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemResponse toResponse(ItemEntity entity) {
        ItemResponse response = new ItemResponse();
        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setDescripcion(entity.getDescripcion());
        response.setUnidadMedida(entity.getUnidadMedida());
        response.setPrecioUnitario(entity.getPrecioUnitario());
        response.setCodigoProductoSin(entity.getCodigoProductoSin());
        response.setImagen(entity.getImagen());

        if(entity.getCategoria() != null) {
            ItemResponse.CategoriaSimpleResponse categoria = new ItemResponse.CategoriaSimpleResponse();
            categoria.setId(entity.getCategoria().getId());
            categoria.setNombre(entity.getCategoria().getNombre());
            response.setCategoria(categoria);
        }

        return response;
    }
}
