package com.example.Car.Services.DTO.request;

public record RepairPhotoCreateRequest(
        String photoType,
        String imageUrl
) {
}