package com.example.Car.Services.Interface;

import com.example.Car.Services.DTO.request.DecisionRequestDTO;
import com.example.Car.Services.DTO.request.OwnerRegistrationRequest;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.DTO.response.RegistrationDocumentResponse;
import com.example.Car.Services.entities.RegistrationDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface RegistrationDocumentInterface {


    MessageResponse registerGarageOwner(@Valid OwnerRegistrationRequest request);

    List<RegistrationDocumentResponse> getAllRegistrationDocuments();

    RegistrationDocumentResponse getRegistrationDocumentsById(Long Id);

    MessageResponse makeDecision(Long id, DecisionRequestDTO requestDTO);
}
