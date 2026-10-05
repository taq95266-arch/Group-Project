package com.example.Car.Services.Controller;

import com.example.Car.Services.DTO.request.SalaryUpdateRequest;
import com.example.Car.Services.DTO.request.TechnicianCreateRequest;
import com.example.Car.Services.DTO.request.TechnicianNotificationRequest;
import com.example.Car.Services.DTO.response.TechnicianResponse;

import com.example.Car.Services.service.GarageOwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garage-owner")
@PreAuthorize("hasRole('GARAGE_OWNER')")
public class GarageOwnerController {

    @Autowired
    private GarageOwnerService garageOwnerService;

    @PostMapping("/technicians")
    @ResponseStatus(HttpStatus.CREATED)
    public TechnicianResponse createTechnician(
            Authentication authentication,
            @RequestBody TechnicianCreateRequest request
    ) {

        String ownerEmail =
                authentication.getName();

        return garageOwnerService.createTechnician(
                ownerEmail,
                request
        );
    }

    @GetMapping("/technicians")
    public List<TechnicianResponse> getAllTechnicians(
            Authentication authentication
    ) {

        String ownerEmail =
                authentication.getName();

        return garageOwnerService.getAllTechnicians(
                ownerEmail
        );
    }

    @GetMapping("/technicians/{technicianId}")
    public TechnicianResponse getTechnician(
            Authentication authentication,
            @PathVariable Long technicianId
    ) {

        String ownerEmail =
                authentication.getName();

        return garageOwnerService.getTechnician(
                ownerEmail,
                technicianId
        );
    }

    @PutMapping("/technicians/{technicianId}/salary")
    public TechnicianResponse updateSalary(
            Authentication authentication,
            @PathVariable Long technicianId,
            @RequestBody SalaryUpdateRequest request
    ) {

        String ownerEmail =
                authentication.getName();

        return garageOwnerService.updateSalary(
                ownerEmail,
                technicianId,
                request
        );
    }

    @PutMapping("/technicians/{technicianId}/deactivate")
    public TechnicianResponse deactivateTechnician(
            Authentication authentication,
            @PathVariable Long technicianId
    ) {

        String ownerEmail =
                authentication.getName();

        return garageOwnerService.deactivateTechnician(
                ownerEmail,
                technicianId
        );
    }

    @PutMapping("/technicians/{technicianId}/activate")
    public TechnicianResponse activateTechnician(
            Authentication authentication,
            @PathVariable Long technicianId
    ) {

        String ownerEmail =
                authentication.getName();

        return garageOwnerService.activateTechnician(
                ownerEmail,
                technicianId
        );
    }

    @PostMapping("/technicians/{technicianId}/notify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendNotification(
            Authentication authentication,
            @PathVariable Long technicianId,
            @RequestBody TechnicianNotificationRequest request
    ) {

        String ownerEmail =
                authentication.getName();

        garageOwnerService.sendNotification(
                ownerEmail,
                technicianId,
                request
        );
    }
}