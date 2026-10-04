package com.example.Car.Services.service;

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

    public ServiceRequest createRequest(ServiceRequest request, Long serviceOptionId) {

        ServiceOption serviceOption = serviceOptionRepository.findById(serviceOptionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service option not found"
                ));

        request.setServiceOption(serviceOption);

        return serviceRequestRepository.save(request);
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public ServiceRequest getRequest(Long requestId) {
        return serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service request not found"
                ));
    }

    public ServiceRequest updateStatus(Long requestId, String status) {

        ServiceRequest request = getRequest(requestId);

        request.setStatus(status);

        return serviceRequestRepository.save(request);
    }

    public void deleteRequest(Long requestId) {

        ServiceRequest request = getRequest(requestId);

        serviceRequestRepository.delete(request);
    }
}
