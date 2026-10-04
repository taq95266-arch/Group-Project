package com.example.Car.Services.service;

import com.example.Car.Services.DTO.request.RepairPhotoCreateRequest;
import com.example.Car.Services.DTO.response.RepairPhotoResponse;
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

    public RepairPhotoResponse addPhoto(
            Long requestId,
            RepairPhotoCreateRequest request
    ) {

        ServiceRequest serviceRequest =
                serviceRequestRepository.findById(requestId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Service request not found"
                        ));

        RepairPhoto repairPhoto = new RepairPhoto();

        repairPhoto.setServiceRequest(serviceRequest);
        repairPhoto.setPhotoType(request.photoType());
        repairPhoto.setImageUrl(request.imageUrl());

        return toResponse(
                repairPhotoRepository.save(repairPhoto)
        );
    }

    public List<RepairPhotoResponse> getAllPhotos(Long requestId) {

        if (!serviceRequestRepository.existsById(requestId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Service request not found"
            );
        }

        return repairPhotoRepository.findByServiceRequest_RequestId(requestId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RepairPhotoResponse getPhoto(
            Long requestId,
            Long photoId
    ) {

        return toResponse(
                findPhoto(requestId, photoId)
        );
    }

    public void deletePhoto(
            Long requestId,
            Long photoId
    ) {

        RepairPhoto repairPhoto =
                findPhoto(requestId, photoId);

        repairPhotoRepository.delete(repairPhoto);
    }

    private RepairPhoto findPhoto(
            Long requestId,
            Long photoId
    ) {

        RepairPhoto repairPhoto =
                repairPhotoRepository.findById(photoId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Repair photo not found"
                        ));

        if (!repairPhoto.getServiceRequest()
                .getRequestId()
                .equals(requestId)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Repair photo not found for this service request"
            );
        }

        return repairPhoto;
    }

    private RepairPhotoResponse toResponse(
            RepairPhoto repairPhoto
    ) {

        return new RepairPhotoResponse(
                repairPhoto.getPhotoId(),
                repairPhoto.getServiceRequest().getRequestId(),
                repairPhoto.getPhotoType(),
                repairPhoto.getImageUrl(),
                repairPhoto.getCapturedAt()
        );
    }
}