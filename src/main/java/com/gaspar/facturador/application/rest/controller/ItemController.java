package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.ItemResponse;
import com.gaspar.facturador.domain.repository.IItemRepository;
import com.gaspar.facturador.persistence.crud.CategoriaCrudRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.entity.CategoriaEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import java. math. BigDecimal;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private IItemRepository itemRepository;
    @Autowired
    private CategoriaCrudRepository categoriaRepository;
    @Autowired
    private ItemMapper itemMapper;

    public ItemController(IItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }
    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAllItems() {
        List<ItemEntity> items = (List<ItemEntity>) itemRepository.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @GetMapping("/por-categoria/{categoriaId}")
    public ResponseEntity<List<ItemResponse>> getItemsByCategoria(@PathVariable Integer categoriaId) {
        List<ItemEntity> items = itemRepository.findByCategoriaId(categoriaId);
        List<ItemResponse> responses = items.stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Integer id) {
        return itemRepository.findById(id)
                .map(itemMapper::toResponse)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<ItemEntity> createItem(@RequestBody ItemEntity item) {
        item.setId(null);
        ItemEntity createdItem = itemRepository.save(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemEntity> updateItem(@PathVariable Integer id, @RequestBody ItemEntity itemDetails) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        if (item.isPresent()) {
            ItemEntity updatedItem = item.get();
            updatedItem.setCodigo(itemDetails.getCodigo());
            updatedItem.setDescripcion(itemDetails.getDescripcion());
            updatedItem.setUnidadMedida(itemDetails.getUnidadMedida());
            updatedItem.setPrecioUnitario(itemDetails.getPrecioUnitario());
            updatedItem.setCodigoProductoSin(itemDetails.getCodigoProductoSin());
            updatedItem.setImagen(itemDetails.getImagen());
            itemRepository.save(updatedItem);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Integer id) {
        try {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/limited")
    public ResponseEntity<List<ItemEntity>> getLimitedItems() {
        List<ItemEntity> items = itemRepository.findAll().stream().limit(5).collect(Collectors.toList());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //ya no se usa
    @PutMapping("/{id}/add/{cantidad}")
    public ResponseEntity<ItemEntity> addCantidadToItem(@PathVariable Integer id, @PathVariable BigDecimal cantidad) {
        Optional<ItemEntity> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            ItemEntity item = itemOptional.get();
            //item.setCantidad(item.getCantidad().add(cantidad));
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/categoria")
    public ResponseEntity<ItemEntity> assignCategoriaToItem(
            @PathVariable Integer id,
            @RequestParam Integer categoriaId) {

        Optional<ItemEntity> itemOpt = itemRepository.findById(id);
        Optional<CategoriaEntity> categoriaOpt = categoriaRepository.findById(categoriaId);

        if (itemOpt.isPresent() && categoriaOpt.isPresent()) {
            ItemEntity item = itemOpt.get();
            item.setCategoria(categoriaOpt.get());
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}