package com.gaspar.facturador.application.response;

public class ClienteFrecuenteDTO {
    private Integer id;
    private String nombreRazonSocial;
    private Long totalCompras;

    public ClienteFrecuenteDTO(Integer id, String nombreRazonSocial, Long totalCompras) {
        this.id = id;
        this.nombreRazonSocial = nombreRazonSocial;
        this.totalCompras = totalCompras;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public Long getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(Long totalCompras) {
        this.totalCompras = totalCompras;
    }
}
