package com.example.Car.Services.DTO.request;

import com.example.Car.Services.enums.GarageStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GrageRequest {

    private Long ownerId;
    private String garageName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phone;
    private GarageStatus status;
}