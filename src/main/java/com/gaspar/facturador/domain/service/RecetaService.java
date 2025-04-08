package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.ItemRepository;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.crud.RecetaInsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.RecetasCrudRepository;
import com.gaspar.facturador.persistence.dto.CrearRecetaDTO;
import com.gaspar.facturador.persistence.dto.RecetaInsumoDTO;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.RecetaInsumoEntity;
import com.gaspar.facturador.persistence.entity.RecetasEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetasCrudRepository recetasCrudRepository;
    private final RecetaInsumoCrudRepository recetaInsumoCrudRepository;
    private final InsumoCrudRepository insumoCrudRepository;
    private final ItemCrudRepository itemCrudRepository;

    public void crearReceta(CrearRecetaDTO dto) {
        ItemEntity productoFinal = itemCrudRepository.findById(dto.getProductoFinalId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        RecetasEntity receta = new RecetasEntity();
        receta.setNombre(dto.getNombre());
        receta.setCantidadUnidades(dto.getCantidadUnidades());
        receta.setPeso(dto.getPeso());
        receta.setProducto(productoFinal);

        receta = recetasCrudRepository.save(receta);

        for (RecetaInsumoDTO insumoDTO : dto.getInsumos()) {
            InsumoEntity insumo = insumoCrudRepository.findById(insumoDTO.getInsumoId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

            RecetaInsumoEntity recetaInsumo = new RecetaInsumoEntity();
            recetaInsumo.setReceta(receta);
            recetaInsumo.setInsumo(insumo);
            recetaInsumo.setCantidad(insumoDTO.getCantidad());

            recetaInsumoCrudRepository.save(recetaInsumo);
        }
    }

    public List<RecetasEntity> listarRecetas() {
        return recetasCrudRepository.findAll();
    }
}
