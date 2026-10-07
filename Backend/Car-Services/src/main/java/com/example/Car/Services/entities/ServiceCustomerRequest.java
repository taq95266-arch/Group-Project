package com.example.Car.Services.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter

public class ServiceCustomerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Request_id;
    private String Guest_Name;
    private  String Guest_phone;
    private String Car_make_model;
    private String Car_plate_model;
    private BigDecimal applied_price;
    private String Status;
    private LocalDateTime Created_at;

}
