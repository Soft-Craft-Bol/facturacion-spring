package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository  extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
    List<UserEntity> findAll();
    void deleteById(long id);

}