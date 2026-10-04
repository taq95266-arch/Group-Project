package com.example.Car.Services.service;

import com.example.Car.Services.DTO.request.ServiceRequestCreateRequest;
import com.example.Car.Services.DTO.response.ServiceRequestResponse;
import com.example.Car.Services.Repository.ServiceOptionRepository;
import com.example.Car.Services.Repository.ServiceRequestRepository;
import com.example.Car.Services.entities.ServiceOption;
import com.example.Car.Services.entities.ServiceRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceOptionRepository serviceOptionRepository;

    public ServiceRequestService(
            ServiceRequestRepository serviceRequestRepository,
            ServiceOptionRepository serviceOptionRepository
    ) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceOptionRepository = serviceOptionRepository;
    }

    public ServiceRequestResponse createRequest(
            ServiceRequestCreateRequest request
    ) {

        ServiceOption serviceOption =
                serviceOptionRepository.findById(request.serviceOptionId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Service option not found"
                        ));

        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setServiceOption(serviceOption);
        serviceRequest.setGuestName(request.guestName());
        serviceRequest.setGuestPhone(request.guestPhone());
        serviceRequest.setCarMakeModel(request.carMakeModel());
        serviceRequest.setCarPlateNumber(request.carPlateNumber());
        serviceRequest.setAppliedPrice(request.appliedPrice());

        if (request.status() == null || request.status().isBlank()) {
            serviceRequest.setStatus("PENDING");
        } else {
            serviceRequest.setStatus(request.status());
        }

        return toResponse(
                serviceRequestRepository.save(serviceRequest)
        );
    }

    public List<ServiceRequestResponse> getAllRequests() {

        return serviceRequestRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServiceRequestResponse getRequest(Long requestId) {

        return toResponse(findRequest(requestId));
    }

    public ServiceRequestResponse updateStatus(
            Long requestId,
            String status
    ) {

        ServiceRequest serviceRequest = findRequest(requestId);

        serviceRequest.setStatus(status);

        return toResponse(
                serviceRequestRepository.save(serviceRequest)
        );
    }

    public void deleteRequest(Long requestId) {

        ServiceRequest serviceRequest = findRequest(requestId);

        serviceRequestRepository.delete(serviceRequest);
    }

    private ServiceRequest findRequest(Long requestId) {

        return serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service request not found"
                ));
    }

    private ServiceRequestResponse toResponse(
            ServiceRequest serviceRequest
    ) {

        return new ServiceRequestResponse(
                serviceRequest.getRequestId(),
                serviceRequest.getServiceOption().getServiceOptionId(),
                serviceRequest.getGuestName(),
                serviceRequest.getGuestPhone(),
                serviceRequest.getCarMakeModel(),
                serviceRequest.getCarPlateNumber(),
                serviceRequest.getAppliedPrice(),
                serviceRequest.getStatus(),
                serviceRequest.getCreatedAt()
        );
    }
}