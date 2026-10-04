package com.example.Car.Services.service.GarageOwner;

import com.example.Car.Services.DTO.request.TechnicianRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.DTO.response.TechnicianResponseDTO;
import com.example.Car.Services.Interface.GarageOwner.TechnicianServiceInterface;
import com.example.Car.Services.Repository.RegistrationDocumentRepository;
import com.example.Car.Services.Repository.TechnicianRepository;
import com.example.Car.Services.Repository.UserRepository;
import com.example.Car.Services.entities.RegistrationDocument;
import com.example.Car.Services.entities.Technician;
import com.example.Car.Services.entities.User;
import com.example.Car.Services.expection.BadRequestException;
import com.example.Car.Services.service.common.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechnicianService implements TechnicianServiceInterface {

    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final RegistrationDocumentRepository documentRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public MessageResponse addTechnician(TechnicianRequestDTO request, Long garageId) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        RegistrationDocument garage = documentRepository.findById(garageId)
                .orElseThrow(() -> new BadRequestException("Garage not found for this owner!"));

        String verificationToken = UUID.randomUUID().toString();

        User technicianUser = new User();
        technicianUser.setFullName(request.getFullName());
        technicianUser.setEmail(request.getEmail());
        technicianUser.setPhone(request.getPhone());
        technicianUser.setActive(false);
        technicianUser.setVerificationToken(verificationToken);
        technicianUser.setVerificationTokenExpiry(Instant.now().plusSeconds(86400));

        userRepository.save(technicianUser);

        Technician technician = new Technician();
        technician.setUser(technicianUser);
        technician.setGarage(garage);
        technician.setSpecialization(request.getSpecialization());
        technician.setIsAvailable(true);

        technicianRepository.save(technician);

        try {
            emailService.sendApprovalEmail(technicianUser.getEmail(), technicianUser.getFullName(), verificationToken);
        } catch (Exception ex) {
            log.error("Failed to send invitation email to technician {}: {}", technicianUser.getEmail(), ex.getMessage());
        }

        return new MessageResponse("Technician added successfully and invitation email sent.");
    }




    @Transactional(readOnly = true)
    @Override
    public List<TechnicianResponseDTO> getTechniciansByGarage(Long garageId) {

        RegistrationDocument garage = documentRepository.findById(garageId)
                .orElseThrow(() -> new BadRequestException("Garage not found with ID: " + garageId));

        return technicianRepository.findByGarageDocId(garage.getDocId()).stream()
                .map(tech -> new TechnicianResponseDTO(
                        tech.getId(),
                        tech.getUser().getFullName(),
                        tech.getUser().getEmail(),
                        tech.getUser().getPhone(),
                        tech.getSpecialization(),
                        tech.getIsAvailable(),
                        tech.getUser().getActive()
                ))
                .collect(Collectors.toList());
    }
}