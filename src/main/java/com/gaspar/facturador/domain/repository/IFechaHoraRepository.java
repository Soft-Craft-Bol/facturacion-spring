package com.gaspar.facturador.domain.repository;


import bo.gob.impuestos.siat.api.facturacion.sincronizacion.RespuestaFechaHora;

public interface IFechaHoraRepository {
    void save(RespuestaFechaHora respuestaFechaHora);
    void deleteAll();
    RespuestaFechaHora obtenerUltimaFechaHora();
}
