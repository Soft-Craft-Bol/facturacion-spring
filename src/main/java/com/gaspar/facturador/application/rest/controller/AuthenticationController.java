package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.AuthCreateUserRequest;
import com.gaspar.facturador.application.rest.dto.AuthLoginRequest;
import com.gaspar.facturador.application.rest.dto.AuthResponse;
import com.gaspar.facturador.domain.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(
            @RequestPart("userRequest") @Valid AuthCreateUserRequest userRequest,
            @RequestPart(value = "photoFile", required = false) MultipartFile photoFile) {
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest, photoFile), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }
}