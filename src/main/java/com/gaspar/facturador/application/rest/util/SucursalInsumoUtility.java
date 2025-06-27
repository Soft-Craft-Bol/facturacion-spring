package com.gaspar.facturador.application.rest.util;

import com.gaspar.facturador.application.response.EmpresaDTO;
import com.gaspar.facturador.application.response.InsumoWithQuantityDTO;
import com.gaspar.facturador.application.response.SucursalDTO;
import com.gaspar.facturador.application.response.SucursalDTOIns;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SucursalInsumoUtility {
    public SucursalInsumoUtility() {}

    public static List<SucursalDTOIns> groupInsumosBySucursal(List<SucursalInsumoEntity> sucursalInsumos){
        Map<Integer,SucursalDTOIns> sucursalMap = new HashMap<>();
        for (SucursalInsumoEntity sucursalInsumo: sucursalInsumos){
            Integer sucursalId = sucursalInsumo.getSucursal().getId();
            SucursalDTOIns sucursalDTO = sucursalMap.getOrDefault(sucursalId, new SucursalDTOIns());

            if(sucursalDTO.getId() == null){
                sucursalDTO.setId(sucursalInsumo.getSucursal().getId());
                sucursalDTO.setCodigo(sucursalInsumo.getSucursal().getCodigo());
                sucursalDTO.setNombre(sucursalInsumo.getSucursal().getNombre());
                sucursalDTO.setDepartamento(sucursalInsumo.getSucursal().getDepartamento());
                sucursalDTO.setMunicipio(sucursalInsumo.getSucursal().getMunicipio());
                sucursalDTO.setDireccion(sucursalInsumo.getSucursal().getDireccion());
                sucursalDTO.setTelefono(sucursalInsumo.getSucursal().getTelefono());
                sucursalDTO.setImage(sucursalInsumo.getSucursal().getImage());

                EmpresaDTO empresaDTO = new EmpresaDTO();
                empresaDTO.setId(sucursalInsumo.getSucursal().getEmpresa().getId());
                empresaDTO.setNit(sucursalInsumo.getSucursal().getEmpresa().getNit());
                empresaDTO.setRazonSocial(sucursalInsumo.getSucursal().getEmpresa().getRazonSocial());
                sucursalDTO.setEmpresa(empresaDTO);

                sucursalDTO.setInsumos(new ArrayList<>());
                sucursalMap.put(sucursalId, sucursalDTO);

            }
            InsumoWithQuantityDTO insumoDTO = new InsumoWithQuantityDTO();
            insumoDTO.setId(sucursalInsumo.getInsumo().getId());
            insumoDTO.setNombre(sucursalInsumo.getInsumo().getNombre());
            //insumoDTO.setProveedor(sucursalInsumo.getInsumo().getProveedor());
            //insumoDTO.setMarca(sucursalInsumo.getInsumo().getMarca());
            //insumoDTO.setPrecio(sucursalInsumo.getInsumo().getPrecio());
            insumoDTO.setUnidades(sucursalInsumo.getInsumo().getUnidades());
            //insumoDTO.setDescripcion(sucursalInsumo.getInsumo().getDescripcion());
            insumoDTO.setImagen(sucursalInsumo.getInsumo().getImagen());
            insumoDTO.setCantidad(sucursalInsumo.getCantidad());

            sucursalDTO.getInsumos().add(insumoDTO);

        }
        return new ArrayList<>(sucursalMap.values());
    }
}
