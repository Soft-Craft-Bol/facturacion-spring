package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.IItemRepository;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {
    private IItemRepository itemRepository;

    public ItemController(IItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }
    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAllItems() {
        List<ItemEntity> items = (List<ItemEntity>) itemRepository.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Integer id) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ItemEntity> createItem(@RequestBody ItemEntity item) {
        ItemEntity savedItem = itemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
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
            itemRepository.save(updatedItem);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Integer id) {
        try {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
