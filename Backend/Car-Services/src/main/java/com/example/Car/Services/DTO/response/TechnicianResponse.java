package com.example.Car.Services.DTO.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TechnicianResponse(
        Long technicianId,
        Long userId,
        String fullName,
        String phone,
        String email,
        BigDecimal salary,
        String specialization,
        LocalDate hireDate,
        Boolean active
) {
}