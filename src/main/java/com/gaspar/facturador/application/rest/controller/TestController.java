package com.gaspar.facturador.application.rest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")

@PreAuthorize("denyAll()")//Deniega el acceso a todos los usuarios --// tODO ESTO YA esta configurado por el archivo SecurityConfig.java
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("permitAll()")//Permite el acceso a todos los usuarios
    public String test(){
        return "Test";
    }

    @GetMapping("/test1")
    @PreAuthorize("hasAuthority('READ')")//Permite el acceso a los usuarios con el rol READ
    public String test1(){
        return "Test1-security";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('CREATE')")//Permite el acceso a los usuarios con el rol CREATE
    public String test2(){
        return "Test1-security2";
    }
}

