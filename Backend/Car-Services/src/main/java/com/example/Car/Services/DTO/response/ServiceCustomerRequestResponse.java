package com.example.Car.Services.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ServiceCustomerRequestResponse {
    private String Request_id;
    private String Guest_Name;
    private  String Guest_phone;
    private String Car_make_model;
    private String Car_plate_model;
    private BigDecimal applied_price;
    private String Status;
    private LocalDateTime Created_at;
}
