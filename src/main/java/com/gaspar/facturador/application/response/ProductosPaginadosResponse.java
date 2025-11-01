package com.gaspar.facturador.application.response;

import com.gaspar.facturador.application.rest.dto.ProductoSucursalDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductosPaginadosResponse {
    private List<ProductoSucursalDto> productos;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
}