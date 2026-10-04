package com.example.Car.Services.Interface.Admin;

import com.example.Car.Services.DTO.request.DecisionRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.DTO.response.RegistrationDocumentResponse;

import java.util.List;

public interface AdminRegistrationDocumentInterface {


    List<RegistrationDocumentResponse> getAllRegistrationDocuments();

    RegistrationDocumentResponse getRegistrationDocumentsById(Long Id);

    MessageResponse makeDecision(Long id, DecisionRequestDTO requestDTO);
}
