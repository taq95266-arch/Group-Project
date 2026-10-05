package com.example.Car.Services.Interface.GarageOwner;

import com.example.Car.Services.DTO.request.NewGarageRequestDTO;
import com.example.Car.Services.DTO.response.GarageResponseDTO;
import com.example.Car.Services.DTO.response.MessageResponse;

import java.util.List;

public interface GarageServiceInterface {
    List<GarageResponseDTO> getOwnerGarages(Long ownerId);
    MessageResponse deactivateGarage(Long garageId, Long ownerId);
    MessageResponse requestNewGarage(Long ownerId, NewGarageRequestDTO request);
}