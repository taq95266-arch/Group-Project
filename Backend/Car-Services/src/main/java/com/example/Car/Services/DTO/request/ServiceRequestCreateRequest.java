package com.example.Car.Services.DTO.request;

import java.math.BigDecimal;

public record ServiceRequestCreateRequest(
        Long serviceOptionId,
        String guestName,
        String guestPhone,
        String carMakeModel,
        String carPlateNumber,
        BigDecimal appliedPrice,
        String status
) {
}