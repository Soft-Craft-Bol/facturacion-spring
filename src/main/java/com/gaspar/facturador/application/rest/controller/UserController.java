package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.UserDetailServiceImpl;
import com.gaspar.facturador.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUser() {
        List<UserEntity> users = userDetailService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
