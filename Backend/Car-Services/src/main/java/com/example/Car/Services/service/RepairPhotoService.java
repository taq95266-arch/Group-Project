package com.example.Car.Services.service;

import com.example.Car.Services.Repository.RepairPhotoRepository;
import com.example.Car.Services.Repository.ServiceRequestRepository;
import com.example.Car.Services.entities.RepairPhoto;
import com.example.Car.Services.entities.ServiceRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@org.springframework.stereotype.Service
public class RepairPhotoService {

    private final RepairPhotoRepository repairPhotoRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    public RepairPhotoService(
            RepairPhotoRepository repairPhotoRepository,
            ServiceRequestRepository serviceRequestRepository
    ) {
        this.repairPhotoRepository = repairPhotoRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public RepairPhoto addPhoto(Long requestId, RepairPhoto repairPhoto) {

        ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service request not found"
                ));

        repairPhoto.setServiceRequest(serviceRequest);

        return repairPhotoRepository.save(repairPhoto);
    }

    public List<RepairPhoto> getAllPhotos() {
        return repairPhotoRepository.findAll();
    }

    public RepairPhoto getPhoto(Long photoId) {
        return repairPhotoRepository.findById(photoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Repair photo not found"
                ));
    }

    public void deletePhoto(Long photoId) {

        RepairPhoto repairPhoto = getPhoto(photoId);

        repairPhotoRepository.delete(repairPhoto);
    }
}
