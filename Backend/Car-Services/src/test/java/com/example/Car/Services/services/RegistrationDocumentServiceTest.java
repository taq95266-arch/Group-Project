package com.example.Car.Services.services;


import com.example.Car.Services.DTO.request.OwnerRegistrationRequest;
import com.example.Car.Services.Repository.RegistrationDocumentRepository;
import com.example.Car.Services.Repository.UserRepository;
import com.example.Car.Services.entities.RegistrationDocument;
import com.example.Car.Services.expection.BadRequestException;
import com.example.Car.Services.service.EmailService;
import com.example.Car.Services.service.FileStorageService;
import com.example.Car.Services.service.RegistrationDocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Testing - Registration Document Service")
public class RegistrationDocumentServiceTest {


      @Mock
      private RegistrationDocumentRepository documentRepository;

      @Mock
      private  UserRepository userRepository;

      @Mock
      private  FileStorageService fileStorageService;

      @Mock
      private PasswordEncoder passwordEncoder;

     @Mock
     private EmailService emailService;

    @InjectMocks
    private RegistrationDocumentService registrationService;

    private OwnerRegistrationRequest validRequest;


    @BeforeEach
    void setUp(){
        validRequest = new OwnerRegistrationRequest();
        validRequest.setEmail("garage.owner@example.com");
        validRequest.setFullName("Ahmed Al-Busaidi");
        validRequest.setPhone("98544444");
        validRequest.setPassword("123456");
        validRequest.setCommercialRegisterNumber("CR-102030");
        validRequest.setGovernorate("Muscat");
        validRequest.setState("Seeb");
        validRequest.setLatitude(23.6141);
        validRequest.setLongitude(58.5453);
        validRequest.setMapAddress("Seeb Industrial Area");
    }


    @Test
    @DisplayName("Should register garage owner successfully when data is valid")
    void registerGarageOwner_Success() {
        when(documentRepository.existsByCommercialRegisterNumber(validRequest.getCommercialRegisterNumber()))
                .thenReturn(false);

        RegistrationDocument savedDoc = new RegistrationDocument();
        savedDoc.setDocId(100L);
        savedDoc.setCommercialRegisterNumber(validRequest.getCommercialRegisterNumber());
        when(documentRepository.save(any(RegistrationDocument.class))).thenReturn(savedDoc);

        assertDoesNotThrow(() -> registrationService.registerGarageOwner(validRequest));

        verify(documentRepository, times(1)).existsByCommercialRegisterNumber(validRequest.getCommercialRegisterNumber());
        verify(documentRepository, times(1)).save(any(RegistrationDocument.class));
        verify(emailService, times(1)).sendWelcomeEmail(validRequest.getEmail(), validRequest.getFullName());

    }



    @Test
    @DisplayName("Should throw BadRequestException when CR number already exists")
    void registerGarageOwner_ShouldThrowException_WhenCRNumberExists() {
        when(documentRepository.existsByCommercialRegisterNumber(validRequest.getCommercialRegisterNumber()))
                .thenReturn(true);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> registrationService.registerGarageOwner(validRequest)
        );

        assertEquals("Commercial register number is already registered!", exception.getMessage());

        verify(documentRepository, never()).save(any());
        verify(emailService, never()).sendWelcomeEmail(anyString(), anyString());
    }


}
