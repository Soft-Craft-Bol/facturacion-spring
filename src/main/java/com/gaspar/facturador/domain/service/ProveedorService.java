package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.ProveedorRequest;
import com.gaspar.facturador.application.response.ProveedorResponse;
import com.gaspar.facturador.persistence.crud.ProveedorRepository;
import com.gaspar.facturador.persistence.entity.ProveedorEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoProveedor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorService {
    private final ProveedorRepository proveedorRepository;

    @Transactional
    public ProveedorResponse crearProveedor(ProveedorRequest request) {
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setNombreRazonSocial(request.getNombreRazonSocial());
        proveedor.setTipoProveedor(request.getTipoProveedor());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setEmail(request.getEmail());

        proveedor = proveedorRepository.save(proveedor);
        return mapToResponse(proveedor);
    }

    public ProveedorResponse obtenerProveedorPorId(Long id) throws ChangeSetPersister.NotFoundException {
        ProveedorEntity proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return mapToResponse(proveedor);
    }

    public List<ProveedorResponse> listarTodosLosProveedores() {
        return proveedorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProveedorResponse actualizarProveedor(Long id, ProveedorRequest request) throws ChangeSetPersister.NotFoundException {
        ProveedorEntity proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        proveedor.setNombreRazonSocial(request.getNombreRazonSocial());
        proveedor.setTipoProveedor(request.getTipoProveedor());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setEmail(request.getEmail());

        proveedor = proveedorRepository.save(proveedor);
        return mapToResponse(proveedor);
    }

    @Transactional
    public void eliminarProveedor(Long id) throws ChangeSetPersister.NotFoundException {
        if (!proveedorRepository.existsById(id)) {
            throw new ChangeSetPersister.NotFoundException();
        }
        proveedorRepository.deleteById(id);
    }

    private ProveedorResponse mapToResponse(ProveedorEntity proveedor) {
        ProveedorResponse response = new ProveedorResponse();
        response.setId(proveedor.getId());
        response.setNombreRazonSocial(proveedor.getNombreRazonSocial());
        response.setTipoProveedor(proveedor.getTipoProveedor());
        response.setDireccion(proveedor.getDireccion());
        response.setTelefono(proveedor.getTelefono());
        response.setEmail(proveedor.getEmail());
        return response;
    }
}