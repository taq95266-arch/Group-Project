package com.example.Car.Services.service;

import com.example.Car.Services.DTO.request.SalaryUpdateRequest;
import com.example.Car.Services.DTO.request.TechnicianCreateRequest;
import com.example.Car.Services.DTO.request.TechnicianNotificationRequest;
import com.example.Car.Services.DTO.response.TechnicianResponse;

import com.example.Car.Services.Repository.TechnicianRepository;
import com.example.Car.Services.Repository.UserRepository;

import com.example.Car.Services.entities.Technician;
import com.example.Car.Services.entities.User;

import com.example.Car.Services.enums.Role;

import com.example.Car.Services.expection.EmailAlreadyExistsException;
import com.example.Car.Services.expection.PhoneAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class GarageOwnerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Transactional
    public TechnicianResponse createTechnician(
            String ownerEmail,
            TechnicianCreateRequest request
    ) {

        User garageOwner =
                userRepository.findByEmail(ownerEmail)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Garage owner not found"
                                )
                        );

        if (garageOwner.getRole() != Role.GARAGE_OWNER) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "User is not a garage owner"
            );
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists"
            );
        }

        if (userRepository.existsByPhone(request.phone())) {
            throw new PhoneAlreadyExistsException(
                    "Phone already exists"
            );
        }

        User technicianUser =
                new User();

        technicianUser.setFullName(
                request.fullName()
        );

        technicianUser.setPhone(
                request.phone()
        );

        technicianUser.setEmail(
                request.email()
        );

        technicianUser.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        technicianUser.setRole(
                Role.TECHNICIAN
        );

        technicianUser.setActive(true);
        technicianUser.setEmailVerified(false);

        String verificationToken =
                UUID.randomUUID().toString();

        technicianUser.setVerificationToken(
                verificationToken
        );

        technicianUser.setVerificationTokenExpiry(
                Instant.now().plusSeconds(86400)
        );

        userRepository.save(
                technicianUser
        );

        Technician technician =
                new Technician();

        technician.setUser(
                technicianUser
        );

        technician.setGarageOwner(
                garageOwner
        );

        technician.setSalary(
                request.salary()
        );

        technician.setSpecialization(
                request.specialization()
        );

        technician.setHireDate(
                LocalDate.now()
        );

        Technician savedTechnician =
                technicianRepository.save(
                        technician
                );

        emailService.sendVerificationEmail(
                technicianUser.getEmail(),
                verificationToken
        );

        return toResponse(
                savedTechnician
        );
    }

    public List<TechnicianResponse> getAllTechnicians(
            String ownerEmail
    ) {

        return technicianRepository
                .findByGarageOwner_Email(
                        ownerEmail
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TechnicianResponse getTechnician(
            String ownerEmail,
            Long technicianId
    ) {

        Technician technician =
                findTechnician(
                        ownerEmail,
                        technicianId
                );

        return toResponse(
                technician
        );
    }

    @Transactional
    public TechnicianResponse updateSalary(
            String ownerEmail,
            Long technicianId,
            SalaryUpdateRequest request
    ) {

        if (request.salary() == null
                || request.salary().signum() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Salary must be greater than zero"
            );
        }

        Technician technician =
                findTechnician(
                        ownerEmail,
                        technicianId
                );

        technician.setSalary(
                request.salary()
        );

        Technician savedTechnician =
                technicianRepository.save(
                        technician
                );

        emailService.sendSalaryUpdateEmail(
                technician.getUser().getEmail(),
                request.salary()
        );

        return toResponse(
                savedTechnician
        );
    }

    @Transactional
    public TechnicianResponse deactivateTechnician(
            String ownerEmail,
            Long technicianId
    ) {

        Technician technician =
                findTechnician(
                        ownerEmail,
                        technicianId
                );

        User technicianUser =
                technician.getUser();

        technicianUser.setActive(false);

        userRepository.save(
                technicianUser
        );

        emailService.sendTechnicianAccountStatusEmail(
                technicianUser.getEmail(),
                false
        );

        return toResponse(
                technician
        );
    }

    @Transactional
    public TechnicianResponse activateTechnician(
            String ownerEmail,
            Long technicianId
    ) {

        Technician technician =
                findTechnician(
                        ownerEmail,
                        technicianId
                );

        User technicianUser =
                technician.getUser();

        technicianUser.setActive(true);

        userRepository.save(
                technicianUser
        );

        emailService.sendTechnicianAccountStatusEmail(
                technicianUser.getEmail(),
                true
        );

        return toResponse(
                technician
        );
    }

    public void sendNotification(
            String ownerEmail,
            Long technicianId,
            TechnicianNotificationRequest request
    ) {

        if (request.subject() == null
                || request.subject().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subject is required"
            );
        }

        if (request.message() == null
                || request.message().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message is required"
            );
        }

        Technician technician =
                findTechnician(
                        ownerEmail,
                        technicianId
                );

        emailService.sendTechnicianNotificationEmail(
                technician.getUser().getEmail(),
                request.subject(),
                request.message()
        );
    }

    private Technician findTechnician(
            String ownerEmail,
            Long technicianId
    ) {

        return technicianRepository
                .findByTechnicianIdAndGarageOwner_Email(
                        technicianId,
                        ownerEmail
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Technician not found"
                        )
                );
    }

    private TechnicianResponse toResponse(
            Technician technician
    ) {

        return new TechnicianResponse(
                technician.getTechnicianId(),
                technician.getUser().getId(),
                technician.getUser().getFullName(),
                technician.getUser().getPhone(),
                technician.getUser().getEmail(),
                technician.getSalary(),
                technician.getSpecialization(),
                technician.getHireDate(),
                technician.getUser().getActive()
        );
    }
}