    package com.gaspar.facturador.application.response;

    import bo.gob.impuestos.siat.api.facturacion.operaciones.RespuestaListaEventos;
    import lombok.Data;


    @Data
    public class EventoSignificativoRegistroResponse {
        private Long idEvento;
        private RespuestaListaEventos respuestaSiat;

        public EventoSignificativoRegistroResponse(Long id, RespuestaListaEventos respuesta) {
            this.idEvento = id;
            this.respuestaSiat = respuesta;
        }
    }