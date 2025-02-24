package com.gaspar.facturador.application.rest.util;

import com.gaspar.facturador.application.response.EmpresaDTO;
import com.gaspar.facturador.application.response.ItemDTO;
import com.gaspar.facturador.application.response.SucursalDTO;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SucursalItemUtility {
    public SucursalItemUtility(){}
    public static List<SucursalDTO> groupItemsBySucursal(List<SucursalItemEntity> sucursalItems) {
        Map<Integer, SucursalDTO> sucursalMap = new HashMap<>();

        for (SucursalItemEntity sucursalItem : sucursalItems) {
            Integer sucursalId = sucursalItem.getSucursal().getId();
            SucursalDTO sucursalDTO = sucursalMap.getOrDefault(sucursalId, new SucursalDTO());

            if (sucursalDTO.getId() == null) {
                sucursalDTO.setId(sucursalItem.getSucursal().getId());
                sucursalDTO.setCodigo(sucursalItem.getSucursal().getCodigo());
                sucursalDTO.setNombre(sucursalItem.getSucursal().getNombre());
                sucursalDTO.setDepartamento(sucursalItem.getSucursal().getDepartamento());
                sucursalDTO.setMunicipio(sucursalItem.getSucursal().getMunicipio());
                sucursalDTO.setDireccion(sucursalItem.getSucursal().getDireccion());
                sucursalDTO.setTelefono(sucursalItem.getSucursal().getTelefono());
                sucursalDTO.setImage(sucursalItem.getSucursal().getImage());

                EmpresaDTO empresaDTO = new EmpresaDTO();
                empresaDTO.setId(sucursalItem.getSucursal().getEmpresa().getId());
                empresaDTO.setNit(sucursalItem.getSucursal().getEmpresa().getNit());
                empresaDTO.setRazonSocial(sucursalItem.getSucursal().getEmpresa().getRazonSocial());
                sucursalDTO.setEmpresa(empresaDTO);

                sucursalDTO.setItems(new ArrayList<>());
                sucursalMap.put(sucursalId, sucursalDTO);
            }

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(sucursalItem.getItem().getId());
            itemDTO.setCodigo(sucursalItem.getItem().getCodigo());
            itemDTO.setDescripcion(sucursalItem.getItem().getDescripcion());
            itemDTO.setUnidadMedida(sucursalItem.getItem().getUnidadMedida());
            itemDTO.setPrecioUnitario(sucursalItem.getItem().getPrecioUnitario());
            itemDTO.setCodigoProductoSin(sucursalItem.getItem().getCodigoProductoSin());
            itemDTO.setImagen(sucursalItem.getItem().getImagen());
            itemDTO.setCantidad(sucursalItem.getCantidad());

            sucursalDTO.getItems().add(itemDTO);
        }

        return new ArrayList<>(sucursalMap.values());
    }
}
