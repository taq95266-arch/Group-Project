package com.example.Car.Services.DTO.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class ServiceCustomerRequestRequest {
    private String Request_id;
    private String Guest_Name;
    private  String Guest_phone;
    private String Car_make_model;
    private String Car_plate_model;
    private BigDecimal applied_price;
    private String Status;
    private LocalDateTime Created_at;
}
