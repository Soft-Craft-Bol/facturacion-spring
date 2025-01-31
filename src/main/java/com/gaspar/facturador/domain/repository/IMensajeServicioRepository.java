package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.RespuestaListaParametricas;

public interface IMensajeServicioRepository {
    void save(RespuestaListaParametricas respuestaListaParametricas);
    void deleteAll();
    RespuestaListaParametricas obtenerUltimosMensajesServicios();
}