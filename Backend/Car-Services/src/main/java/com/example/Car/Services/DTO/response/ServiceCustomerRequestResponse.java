package com.example.Car.Services.DTO.response;

import com.example.Car.Services.enums.ServiceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ServiceCustomerRequestResponse {

    private Long requestId;

    private Long garageOptionId;

    private String guestName;

    private String guestPhone;

    private String carMakeModel;

    private String carPlateNumber;

    private BigDecimal appliedPrice;

    private ServiceRequestStatus status;

    private LocalDateTime createdAt;
}