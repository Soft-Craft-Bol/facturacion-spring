package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.RespuestaFechaHora;

public interface IFechaHoraRepository {
    void save(RespuestaFechaHora respuestaFechaHora);
    void deleteAll();
    RespuestaFechaHora obtenerUltimaFechaHora();
}
