package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.UpdateUserRequest;
import com.gaspar.facturador.domain.service.UserDetailServiceImpl;
import com.gaspar.facturador.persistence.dto.UserDTO;
import com.gaspar.facturador.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<UserDTO> users = userDetailService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //Obtener usuarios por id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userDetailService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest updateRequest) {
        UserDTO updatedUser = userDetailService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/vendedores")
    public ResponseEntity<List<UserDTO>> getVendedorUsers() {
        List<UserDTO> users = userDetailService.getVendedorUsers();
        return ResponseEntity.ok(users);
    }
}
