package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.UserDetailServiceImpl;
import com.gaspar.facturador.persistence.dto.UserDTO;
import com.gaspar.facturador.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userDetailService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userDetailService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //Obtener usuarios por id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userDetailService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/vendedores")
    public ResponseEntity<List<UserDTO>> getVendedorUsers() {
        List<UserDTO> users = userDetailService.getVendedorUsers();
        return ResponseEntity.ok(users);
    }
}
