package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.IItemRepository;
import com.gaspar.facturador.domain.service.RoleService;
import com.gaspar.facturador.persistence.SucursalRepository;
import com.gaspar.facturador.persistence.crud.ClienteCrudRepository;
import com.gaspar.facturador.persistence.crud.FacturaCrudRepository;
import com.gaspar.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.dto.StatDTO;
import com.gaspar.facturador.persistence.entity.RoleEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/stats")
public class StatController {
    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private FacturaCrudRepository facturaCrudRepository;

    @Autowired
    private IItemRepository itemRepository;

    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private ClienteCrudRepository clienteRepository;

    @Autowired
    private PuntoVentaCrudRepository puntoVentaCrudRepository;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<StatDTO> getStats() {
        StatDTO stats = new StatDTO();

        stats.setNumeroSucursales(sucursalRepository.count());
        stats.setInventario(itemRepository.count());

        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        long facturasEmitidasHoy = facturaCrudRepository.countByFechaEmisionBetween(startOfDay, endOfDay);
        stats.setFacturasEmitidasHoy(facturasEmitidasHoy);
        Double totalVentasHoy = facturaCrudRepository.sumSubtotalByFechaEmisionBetween(startOfDay, endOfDay);
        stats.setTotalVentasHoy(totalVentasHoy != null ? totalVentasHoy : 0.0);
        stats.setNumeroPuntosVenta(puntoVentaCrudRepository.count());
        stats.setNumeroUsuarios(usuarioRepository.count());
        stats.setClientesRegistrados(clienteRepository.count());
        roleService.getRoleEntity(RoleEnum.PANADERO).ifPresent(roleEntity ->
                stats.setTotalPanaderos(usuarioRepository.countByRoles(roleEntity))
        );
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}