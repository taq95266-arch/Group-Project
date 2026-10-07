package com.example.Car.Services.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCustomerRequestRequest {

    private Long garageOptionId;

    private String guestName;

    private String guestPhone;

    private String carMakeModel;

    private String carPlateNumber;
}