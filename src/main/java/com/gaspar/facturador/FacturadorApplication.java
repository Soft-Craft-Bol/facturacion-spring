package com.gaspar.facturador;

import com.gaspar.facturador.persistence.crud.UserRepository;
import com.gaspar.facturador.persistence.entity.PermissionEntity;
import com.gaspar.facturador.persistence.entity.RoleEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;
import com.gaspar.facturador.persistence.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class FacturadorApplication {
	public static void main(String[] args) {
		SpringApplication.run(FacturadorApplication.class, args);
	}
}
