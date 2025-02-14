package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.DespachoEntity;
import org.springframework.data.repository.CrudRepository;

public interface IDespachoRepository extends CrudRepository<DespachoEntity,Long> {
}
