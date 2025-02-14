package com.gaspar.facturador.persistence;

import com.gaspar.facturador.domain.repository.IItemRepository;
import com.gaspar.facturador.persistence.crud.ItemCrudRepository;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ItemRepository implements IItemRepository {

    private final ItemCrudRepository itemCrudRepository;

    public ItemRepository(ItemCrudRepository itemCrudRepository) {
        this.itemCrudRepository = itemCrudRepository;
    }
    @Override
    public Optional<ItemEntity> findById(Integer id) {
        return itemCrudRepository.findById(id);
    }

    @Override
    public List<ItemEntity> findAll() {
        return (List<ItemEntity>) itemCrudRepository.findAll();
    }

    @Override
    public ItemEntity save(ItemEntity item) {
        return itemCrudRepository.save(item);
    }

    @Override
    public void deleteById(Integer id) {
        itemCrudRepository.deleteById(id);
    }
}
