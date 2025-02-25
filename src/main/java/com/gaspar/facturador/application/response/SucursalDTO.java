package com.gaspar.facturador.application.response;

import lombok.Data;

import java.util.List;

@Data
public class SucursalDTO {
    private Integer id;
    private Integer codigo;
    private String nombre;
    private String departamento;
    private String municipio;
    private String direccion;
    private String telefono;
    private EmpresaDTO empresa;
    private String image;
    private List<ItemWithQuantityDTO> items;

}