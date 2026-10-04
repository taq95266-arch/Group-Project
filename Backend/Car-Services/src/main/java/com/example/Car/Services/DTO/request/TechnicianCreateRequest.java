package com.example.Car.Services.DTO.request;

import java.math.BigDecimal;

public record TechnicianCreateRequest(
        String fullName,
        String phone,
        String email,
        String password,
        BigDecimal salary,
        String specialization
) {
}