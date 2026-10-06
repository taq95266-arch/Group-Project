package com.example.Car.Services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CarServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServicesApplication.class, args);
	}

}
