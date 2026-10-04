package com.example.Car.Services.DTO.response;

import java.math.BigDecimal;
import java.time.Instant;

public record ServiceRequestResponse(
        Long requestId,
        Long serviceOptionId,
        String guestName,
        String guestPhone,
        String carMakeModel,
        String carPlateNumber,
        BigDecimal appliedPrice,
        String status,
        Instant createdAt
) {
}