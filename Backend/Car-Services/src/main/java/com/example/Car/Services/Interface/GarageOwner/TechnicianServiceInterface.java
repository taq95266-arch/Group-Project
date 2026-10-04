package com.example.Car.Services.Interface.GarageOwner;

import com.example.Car.Services.DTO.request.TechnicianRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.DTO.response.TechnicianResponseDTO;

import java.util.List;

public interface TechnicianServiceInterface {
    MessageResponse addTechnician(TechnicianRequestDTO request, Long ownerUserId);

    List<TechnicianResponseDTO> getTechniciansByGarage(Long ownerUserId);
}
