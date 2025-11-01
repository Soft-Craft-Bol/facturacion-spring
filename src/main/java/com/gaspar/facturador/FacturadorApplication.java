package com.gaspar.facturador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class FacturadorApplication {
	public static void main(String[] args) {
		SpringApplication.run(FacturadorApplication.class, args);
	}
}
