package com.example.Car.Services.DTO;

import java.math.BigDecimal;

public record ServiceOptionRequest(
        String type,
        String size,
        String brand,
        BigDecimal price
)
{
}