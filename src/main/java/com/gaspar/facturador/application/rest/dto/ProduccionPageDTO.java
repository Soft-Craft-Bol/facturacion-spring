package com.gaspar.facturador.application.rest.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ProduccionPageDTO {
    private Page<ProduccionResponseDTO> producciones;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
