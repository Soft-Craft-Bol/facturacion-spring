package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.api.facturacion.codigos.MensajeServicio;
import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCuis;
import com.gaspar.facturador.application.response.CufdEventoDTO;
import com.gaspar.facturador.domain.service.CufdService;
import com.gaspar.facturador.domain.service.CuisService;
import com.gaspar.facturador.domain.service.VerificarNitService;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/codigos")
public class CodigosController {

    private final CuisService cuisService;
    private final CufdService cufdService;
    private final VerificarNitService verificarNitService;

    public CodigosController(CuisService cuisService, CufdService cufdService, VerificarNitService verificarNitService) {
        this.cuisService = cuisService;
        this.cufdService = cufdService;
        this.verificarNitService = verificarNitService;
    }

    @PostMapping("/obtener-cuis/{idPuntoVenta}")
    public ResponseEntity<Map<String, Object>> obtenerCuis(@PathVariable("idPuntoVenta") Integer idPuntoVenta) throws RemoteException {
        RespuestaCuis respuesta = this.cuisService.obtenerCuis(idPuntoVenta);

        Map<String, Object> response = new HashMap<>();
        response.put("codigo", respuesta.getCodigo());
        response.put("fechaVigencia", respuesta.getFechaVigencia());
        response.put("transaccion", respuesta.isTransaccion());

        if (respuesta.getMensajesList() != null) {
            List<String> mensajes = respuesta.getMensajesList().stream()
                    .map(MensajeServicio::getDescripcion)
                    .collect(Collectors.toList());
            response.put("mensajes", mensajes);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/obtener-cufd/{idPuntoVenta}")
    public ResponseEntity<Map<String, Object>> obtenerCufd(@PathVariable("idPuntoVenta") Integer idPuntoVenta) throws RemoteException {
        RespuestaCufd respuesta = this.cufdService.obtenerCufd(idPuntoVenta);

        Map<String, Object> response = new HashMap<>();
        response.put("codigo", respuesta.getCodigo());
        response.put("codigoControl", respuesta.getCodigoControl());
        response.put("direccion", respuesta.getDireccion()); // ← Aquí verías si viene la dirección
        response.put("fechaVigencia", respuesta.getFechaVigencia());
        response.put("transaccion", respuesta.isTransaccion());

        if (respuesta.getMensajesList() != null) {
            List<String> mensajes = respuesta.getMensajesList().stream()
                    .map(MensajeServicio::getDescripcion)
                    .collect(Collectors.toList());
            response.put("mensajes", mensajes);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verificar-nit/{idPuntoVenta}")
    public ResponseEntity<Map<String, Object>> verificarNit(
            @PathVariable("idPuntoVenta") Integer idPuntoVenta,
            @RequestParam("nit") Long nit) throws RemoteException {

        Map<String, Object> response = verificarNitService.verificarNit(nit, idPuntoVenta);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtener-cufds-anteriores/{idPuntoVenta}")
    public ResponseEntity<List<CufdEventoDTO>> obtenerCufdEvento(@PathVariable Integer idPuntoVenta) {
        return ResponseEntity.ok(cufdService.obtenerCufdsAnteriores(idPuntoVenta));
    }

}
