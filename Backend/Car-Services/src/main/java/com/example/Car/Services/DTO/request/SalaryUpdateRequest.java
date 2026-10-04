package com.example.Car.Services.DTO.request;

import java.math.BigDecimal;

public record SalaryUpdateRequest(
        BigDecimal salary
) {
}