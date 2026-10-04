package com.example.Car.Services.DTO.response;

import java.time.Instant;

public record RepairPhotoResponse(
        Long photoId,
        Long requestId,
        String photoType,
        String imageUrl,
        Instant capturedAt
) {
}