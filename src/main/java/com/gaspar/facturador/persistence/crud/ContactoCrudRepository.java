package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ContactoEntity;
import com.gaspar.facturador.persistence.entity.enums.EstadoContacto;
import com.gaspar.facturador.persistence.entity.enums.TipoAsunto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoCrudRepository extends JpaRepository<ContactoEntity, Long> {
    Page<ContactoEntity> findAll(Pageable pageable);

    Page<ContactoEntity> findByAtendido(EstadoContacto atendido, Pageable pageable);

    Page<ContactoEntity> findByAsunto(TipoAsunto asunto, Pageable pageable);

    Page<ContactoEntity> findByAtendidoAndAsunto(EstadoContacto atendido, TipoAsunto asunto, Pageable pageable);
}
